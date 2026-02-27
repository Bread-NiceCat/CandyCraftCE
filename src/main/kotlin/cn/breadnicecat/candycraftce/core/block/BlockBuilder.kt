package cn.breadnicecat.candycraftce.core.block

import cn.breadnicecat.candycraftce.core.item.ItemBuilder
import cn.breadnicecat.candycraftce.core.item.ItemBuilder.ItemEntry
import cn.breadnicecat.candycraftce.core.item.ItemFactory
import cn.breadnicecat.candycraftce.core.item.PropertiesFactory
import cn.breadnicecat.candycraftce.utils.*
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.CUtils.register
import cn.breadnicecat.candycraftce.utils.Immediate.Companion.immediate
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.ItemLike
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
    internal val arguments = Arguments.Builder()
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

    fun noBlockItem(): BlockBuilder<B> {
        removeRecord("blockItem")
        return this
    }

    fun <I : BlockItem> blockItem(
        factory: (Block) -> ItemFactory<I>,
        properties: PropertiesFactory = {},
        action: BlockEntry<B>.(ItemBuilder<I>) -> Unit = {},
    ): BlockBuilder<B> {
        record("blockItem", queue = QueueType.LAST) {
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
    fun copyProperties(block: BlockEntry<*>): BlockBuilder<B> {
        return this.copyProperties(block.block)
    }

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


    fun argument(key: String, value: Any?): BlockBuilder<B> {
        if (value == null) {
            arguments.remove(key)
        } else {
            arguments[key] = value
        }
        return this
    }

    fun arguments(args: Arguments): BlockBuilder<B> {
        arguments.putAll(args)
        return this
    }

    fun save(): BlockEntry<B> {
        val location = id.modLoc()
        CUtils.logRegister("Block", location)
        executeRecords()
        val properties = propCopy?.let { Properties.copy(it) } ?: Properties.of()
        properties.apply(propBuilder)
        val block = register(location) { factory(arguments, properties) }
        val entry = BlockEntry(location, block, blockItem, this.immediate())

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
        new.arguments.putAll(arguments)
        new.copyFrom(this as OperationRecordable<BlockBuilder<NB>>)
        return new
    }

    fun copy(
        id: String,
        properties: BehaviourFactory = this.propBuilder,
    ): BlockBuilder<B> {
        val new = BlockBuilder(id, factory, properties)
        new.arguments.putAll(arguments)
        new.copyFrom(this)
        return new
    }

    override val receiver: BlockBuilder<B>
        get() = this

    companion object {
        private fun <B : Block> register(
            id: ResourceLocation,
            factory: () -> B,
        ): B {
            return BuiltInRegistries.BLOCK.register(id, factory())
        }
    }

    class BlockEntry<B : Block>(
        val id: ResourceLocation,
        val block: B,
        private val item: ItemEntry<out BlockItem>?,
        private val builder: Immediate<BlockBuilder<B>>,
    ) : ItemLike by block {
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
