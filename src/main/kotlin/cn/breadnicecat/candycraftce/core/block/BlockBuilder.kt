package cn.breadnicecat.candycraftce.core.block

import cn.breadnicecat.candycraftce.CandyCraftCE
import cn.breadnicecat.candycraftce.core.items.ItemBuilder
import cn.breadnicecat.candycraftce.core.items.ItemBuilder.ItemEntry
import cn.breadnicecat.candycraftce.core.items.ItemFactory
import cn.breadnicecat.candycraftce.core.items.PropertiesFactory
import cn.breadnicecat.candycraftce.utils.Arguments
import cn.breadnicecat.candycraftce.utils.ModUtils.modLoc
import cn.breadnicecat.candycraftce.utils.ModUtils.register
import cn.breadnicecat.candycraftce.utils.OperationRecordable
import cn.breadnicecat.candycraftce.utils.V
import cn.breadnicecat.candycraftce.utils.V.Companion.v
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.MapColor
import java.util.*
import java.util.function.Consumer

typealias BlockFactory<B> = Arguments.(Properties) -> B
typealias BehaviourFactory = (Properties) -> Unit

class BlockBuilder<B : Block>(
    val id: String,
    val factory: BlockFactory<B>,
    private var propBuilder: BehaviourFactory = {},
) : OperationRecordable<BlockBuilder<B>>() {

    private val lateUsage = LinkedList<Consumer<BlockEntry<B>>>()
    private val arguments = Arguments.Builder()
    private var blockItem: ItemEntry<out BlockItem>? = null
    private var blockItemMod: (BlockEntry<B>.(ItemBuilder<out BlockItem>) -> Unit) = {}
    private var propCopy: BlockBehaviour? = null
    fun modifyBlockItem(action: BlockEntry<B>.(ItemBuilder<out BlockItem>) -> Unit = {}): BlockBuilder<B> {
        record("modifyBlockItem", overridable = false) {
            val old = blockItemMod
            blockItemMod = { old(it); action(it); }
        }
        return this
    }

    fun <I : BlockItem> blockItem(
        factory: (Block) -> ItemFactory<I>,
        properties: PropertiesFactory = {},
        action: BlockEntry<B>.(ItemBuilder<I>) -> Unit = {},
    ): BlockBuilder<B> {
        record("blockItem") {
            lateUsage {
                val fn = factory(it.block)
                val builder = ItemBuilder(id, fn, properties)
                action(it, builder)
                blockItemMod(it, builder)
                blockItem = builder.save()
            }
        }
        return this
    }

    fun simpleBlockItem(
        properties: PropertiesFactory = {},
        action: BlockEntry<B>.(ItemBuilder<BlockItem>) -> Unit = {},
    ) = blockItem(
        factory = { block -> { BlockItem(block, it) } },
        properties = properties,
        action = action
    )


    /*==============================
                Core Zone
     ==============================*/
    fun copyProperties(block: BlockBehaviour): BlockBuilder<B> {
        record("copyProperties") {
            propCopy = block
        }
        return this
    }

    fun modifyProperties(action: BehaviourFactory): BlockBuilder<B> {
        record("modifyProperties", overridable = false) {
            val prop = propBuilder
            propBuilder = { prop(it); action(it) }
        }
        return this
    }

    fun mapColor(mapColor: MapColor): BlockBuilder<B> {
        modifyProperties { it.mapColor(mapColor) }
        return this
    }

    /**
     * 在注册后调用
     * */
    fun lateUsage(action: (BlockEntry<B>) -> Unit) {
        check(!frozen)
        lateUsage.add(action)
    }

    fun argument(key: String, value: String?): BlockBuilder<B> {
        if (value == null) arguments.remove(key)
        else arguments[key] = value
        return this
    }

    fun arguments(vararg args: Pair<String, String?>): BlockBuilder<B> {
        args.forEach { (key, value) ->
            argument(key, value)
        }
        return this
    }

    fun save(): BlockEntry<B> {
        val location = id.modLoc()
        CandyCraftCE.clog.info("Registering Block/{}", location)

        executeRecords()
        val argument = arguments.build()


        val properties = propCopy?.let { Properties.copy(it) } ?: Properties.of()
        properties.apply(propBuilder)

        val block = register(location) { factory(argument, properties) }

        val entry = BlockEntry(location, block, blockItem, this.v())
        lateUsage.forEach { it.accept(entry) }
        return entry
    }

    @Suppress("UNCHECKED_CAST")
    fun <NB : B> sub(
        id: String,
        factory: BlockFactory<NB>,
        properties: BehaviourFactory = this.propBuilder,
    ): BlockBuilder<NB> {
        val new = BlockBuilder(id, factory, properties)
        new.copyRecord(this as OperationRecordable<BlockBuilder<NB>>)
        return new
    }

    fun copy(
        id: String,
        properties: BehaviourFactory = this.propBuilder,
    ): BlockBuilder<B> {
        val new = BlockBuilder(id, factory, properties)
        new.copyRecord(this)
        return new
    }

    override val receiver: BlockBuilder<B>
        get() = this

    companion object {
        @Suppress("UNCHECKED_CAST")
        private fun <B : Block> register(
            id: ResourceLocation,
            factory: () -> B,
        ): B {
            return BuiltInRegistries.BLOCK.register(id, factory()) as B
        }
    }

    class BlockEntry<B : Block>(
        val id: ResourceLocation,
        val block: B,
        val item: ItemEntry<out BlockItem>?,
        private val builder: V<BlockBuilder<B>>,
    ) {
        operator fun component1() = id
        operator fun component2() = block
        fun copy(
            id: String,
            properties: BehaviourFactory = builder.get().propBuilder,
        ) = builder.get().copy(id, properties)

        fun <NB : B> sub(
            id: String,
            factory: BlockFactory<NB>,
            properties: BehaviourFactory = builder.get().propBuilder,
        ): BlockBuilder<NB> = builder.get().sub(id, factory, properties)

        fun defaultBlockState(): BlockState = block.defaultBlockState()
    }
}
