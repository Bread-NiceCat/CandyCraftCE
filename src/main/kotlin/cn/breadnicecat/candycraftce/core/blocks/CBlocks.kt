package cn.breadnicecat.candycraftce.core.blocks


import cn.breadnicecat.candycraftce.core.items.CItems
import cn.breadnicecat.candycraftce.core.items.ItemFactory
import cn.breadnicecat.candycraftce.core.items.PropertiesFactory
import cn.breadnicecat.candycraftce.data.DataUtils.modelBlockSimple
import cn.breadnicecat.candycraftce.data.DataUtils.modelCubeAll
import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.Utils.modLoc
import cn.breadnicecat.candycraftce.utils.Utils.register
import cn.breadnicecat.candycraftce.utils.Utils.safeForEach
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
private typealias BlockFactory<B> = (BlockBehaviour.Properties) -> B
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
        }
        .modelCubeAll()

    val sugar_block = simple.copy("sugar_block")
        .translate("Sugar Block", "糖块")
        .save()

    class BlockBuilder<B : Block>(
        val id: String,
        val factory: BlockFactory<B>,
        internal var propBuilder: BehaviourFactory = {},
    ) {


        private val ops = LinkedHashMap<String, BlockBuilderOp<B>>()
        private val lateUsage = LinkedList<Consumer<Entry<B>>>()
        private var blockItem: CItems.Entry<out BlockItem>? = null
        fun <I : BlockItem> blockItem(
            factory: (Block) -> ItemFactory<I>,
            properties: PropertiesFactory = {},
            action: Entry<B>.(CItems.ItemBuilder<I>) -> Unit = {},
        ): BlockBuilder<B> {
            record("blockItem") {
                lateUsage {
                    val builder = CItems.ItemBuilder(id, factory(it.block), properties)
                    action(it, builder)
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

        fun properties(action: BehaviourFactory): BlockBuilder<B> {
            record("properties", overridable = false) {
                val prop = propBuilder
                propBuilder = { prop(it); action(it) }
            }
            return this
        }

        /**
         * 在注册后调用
         * */
        fun lateUsage(action: (Entry<B>) -> Unit) {
            lateUsage.add(action)
        }

        private var saving = false
        private var saved = false

        /**
         * 记录每次操作，用于在保存前执行
         * [overridable]允许重写,默认为true
         * [private]如果为true则不会在[copy]时复制,默认为false
         * */
        @Suppress("DuplicatedCode")
        internal fun record(
            key: String,
            overridable: Boolean = true,
            private: Boolean = false,
            op: BlockBuilderOp<B>,
        ) {
            check(!saved)
            if (saving) {
                op()
                return
            }
            var key = key
            if (private) {
                key = "_private$key"
            }
            if (!overridable) {
                key += "@${id}.${key}@${op.hashCode()}"
                var vkey = key
                var cnt = 0
                //抗碰撞
                while (vkey in ops) {
                    vkey = key + cnt++
                }
                key = vkey
            }
            ops[key] = op
        }

        fun loadOps(model: BlockBuilder<B>) {
            check(!saved)
            check("_uncopiable" !in model.ops) { "Unable to copy ops as the builder has been marked it as uncopiable" }
            val op = model.ops.filter { (key, _) -> !key.startsWith("_private") }
            ops.putAll(op)
        }

        fun uncopiable() {
            record("_uncopiable") { error("uncopiable") }
        }

        fun save(): Entry<B> {
            saving = true
            ops.safeForEach(desc = { "Operation: $it" }) { (_, op) -> op() }
            saved = true
            val block = register(id.modLoc()) { factory(of().apply(propBuilder)) }
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
                new.loadOps(this)
            }
            return new
        }

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
            withOps: Boolean = true,
        ) = builder.get().copy(id, factory, properties, withOps)
    }

}