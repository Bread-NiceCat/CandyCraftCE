package cn.breadnicecat.candycraftce.core.blocks


import cn.breadnicecat.candycraftce.core.items.CItems
import cn.breadnicecat.candycraftce.core.items.ItemFactory
import cn.breadnicecat.candycraftce.core.items.PropertiesFactory
import cn.breadnicecat.candycraftce.core.tabs.CItemTabs.CANDYCRAFT
import cn.breadnicecat.candycraftce.core.tabs.CItemTabs.tab
import cn.breadnicecat.candycraftce.data.DataUtils.modelBlockSimple
import cn.breadnicecat.candycraftce.data.DataUtils.modelCubeAll
import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.Arguments
import cn.breadnicecat.candycraftce.utils.OperationRecordable
import cn.breadnicecat.candycraftce.utils.Utils.modLoc
import cn.breadnicecat.candycraftce.utils.Utils.register
import cn.breadnicecat.candycraftce.utils.V
import cn.breadnicecat.candycraftce.utils.V.Companion.v
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour.Properties.of
import java.util.*
import java.util.function.Consumer

private typealias BlockBuilderOp<I> = CBlocks.BlockBuilder<I>.() -> Unit
private typealias BlockFactory<B> = Arguments.(BlockBehaviour.Properties) -> B
private typealias BehaviourFactory = (BlockBehaviour.Properties) -> Unit

object CBlocks {
    /*注册规范:
    .translate 翻译
    .model 模型
    .save 保存
    */
    private val simple = BlockBuilder("_modelSimple", { Block(it) })
        .simpleBlockItem {
            it.modelBlockSimple(this)
                .tab(CANDYCRAFT)
        }
        .modelCubeAll()

    val sugar_block = simple.copy("sugar_block")
        .translate("Sugar Block", "糖块")
        .save()

    class BlockBuilder<B : Block>(
        val id: String,
        val factory: BlockFactory<B>,
        internal var propBuilder: BehaviourFactory = {},
    ) : OperationRecordable<BlockBuilder<B>>() {

        private val lateUsage = LinkedList<Consumer<Entry<B>>>()
        private val arguments = Arguments.Builder()
        private var blockItem: CItems.Entry<out BlockItem>? = null
        private var blockItemMod: (Entry<B>.(CItems.ItemBuilder<out BlockItem>) -> Unit) = {}
        fun modifyBlockItem(action: Entry<B>.(CItems.ItemBuilder<out BlockItem>) -> Unit = {}) {
            record("modifyBlockItem", overridable = false) {
                val old = blockItemMod
                blockItemMod = { old(it); action(it); }
            }
        }

        fun <I : BlockItem> blockItem(
            factory: (Block) -> ItemFactory<I>,
            properties: PropertiesFactory = {},
            action: Entry<B>.(CItems.ItemBuilder<I>) -> Unit = {},
        ): BlockBuilder<B> {
            record("blockItem") {
                lateUsage {
                    val builder = CItems.ItemBuilder(id, factory(it.block), properties)
                    action(it, builder)
                    blockItemMod(it, builder)
                    blockItem = builder.save()
                }
            }
            return this
        }

        fun simpleBlockItem(
            properties: PropertiesFactory = {},
            action: Entry<B>.(CItems.ItemBuilder<BlockItem>) -> Unit = {},
        ) = blockItem(
            factory = { block -> { BlockItem(block, it) } },
            properties = properties,
            action = action
        )


        /*==============================
                    Core Zone
         ==============================*/

        fun modifyProperties(action: BehaviourFactory): BlockBuilder<B> {
            record("modifyProperties", overridable = false) {
                val prop = propBuilder
                propBuilder = { prop(it); action(it) }
            }
            return this
        }

        /**
         * 在注册后调用
         * */
        fun lateUsage(action: (Entry<B>) -> Unit) {
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

        fun save(): Entry<B> {
            executeRecords()
            val block = register(id.modLoc()) {
                factory(arguments.build(), of().apply(propBuilder))
            }
            val entry = Entry(id.modLoc(), block, blockItem, this.v())
            lateUsage.forEach { it.accept(entry) }
            return entry
        }

        fun copy(
            id: String,
            factory: BlockFactory<B> = this.factory,
            properties: BehaviourFactory = this.propBuilder,
            withOps: Boolean = true,
        ): BlockBuilder<B> {
            val new = BlockBuilder(id, factory, properties)
            if (withOps) {
                new.copyRecord(this)
            }
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
    }

    class Entry<B : Block>(
        val id: ResourceLocation,
        val block: B,
        val item: CItems.Entry<out BlockItem>?,
        private val builder: V<BlockBuilder<B>>,
    ) {
        operator fun component1() = id
        operator fun component2() = block
        fun copy(
            id: String,
            factory: BlockFactory<B> = builder.get().factory,
            properties: BehaviourFactory = builder.get().propBuilder,
            withRecord: Boolean = true,
        ) = builder.get().copy(id, factory, properties, withRecord)
    }

}