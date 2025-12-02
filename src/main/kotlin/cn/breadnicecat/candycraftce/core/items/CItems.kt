package cn.breadnicecat.candycraftce.core.items

import cn.breadnicecat.candycraftce.data.DataUtils.modelFlat
import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.Utils.instance
import cn.breadnicecat.candycraftce.utils.Utils.modLoc
import cn.breadnicecat.candycraftce.utils.Utils.safeForEach
import cn.breadnicecat.candycraftce.utils.Utils.second
import cn.breadnicecat.candycraftce.utils.V
import cn.breadnicecat.candycraftce.utils.V.Companion.v
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects.CONFUSION
import net.minecraft.world.effect.MobEffects.NIGHT_VISION
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.Items
import java.util.*
import java.util.function.Consumer

private typealias ItemBuilderOp<I> = CItems.ItemBuilder<I>.() -> Unit
typealias ItemFactory<I> = (Properties) -> I
typealias PropertiesFactory = (Properties) -> Unit

object CItems {
    /*注册规范:
       .translate 翻译
       .model 模型
       .food 食用性
       .save 保存
    */
    private val simple = ItemBuilder("_modelSimple", { Item(it) })
        .modelFlat()

    val licorice = simple.copy("licorice")
        .translate("Licorice", "盐甘草糖")
        .food(3, 0.3f)
        .save()
    val honeycomb = simple.copy("honeycomb")
        .translate("Honeycomb", "蜜蜡")
        .food(6, 0.1f)
        .save()
    val honeycomb_shard = simple.copy("honeycomb_shard")
        .translate("Honeycomb Shard", "蜜蜡碎片")
        .food(6, 0.1f)
        .save()
    val pez = simple.copy("pez")
        .translate("PEZ", "皮礼士糖")
        .food(10, 0.5f, listOf(1f to NIGHT_VISION.instance(60.second)))
        .save()
    val marshmallow_stick = simple.copy("marshmallow_stick")
        .translate("Marshmallow Stick", "棉花软木木棍")
        .food(1, 1f)
        .save()
    val sugar_crystal = simple.copy("sugar_crystal")
        .translate("Sugar Crystal", "冰糖")
        .food(4, 2f)
        .save()

    val chocolate_brick = simple.copy("chocolate_brick")
        .translate("Chocolate Brick", "巧克力砖")
        .food(2, 1f)
        .save()
    val caramel_brick = chocolate_brick.copy("caramel_brick")
        .translate("Caramel Brick", "焦糖砖")
        .save()
    val white_chocolate_brick = chocolate_brick.copy("white_chocolate_brick")
        .translate("White Chocolate Brick", "白巧克力砖")
        .save()
    val cotton_candy = chocolate_brick.copy("cotton_candy")
        .translate("Cotton Candy", "棉花糖")
        .save()

    val gummy = simple.copy("gummy")
        .translate("Gummy", "软糖")
        .food(2, 1f, listOf(0.8f to CONFUSION.instance(10.second)))
        .save()
    val hot_gummy = simple.copy("hot_gummy")
        .translate("Hot Gummy", "熟软糖")
        .food(2, 1f)
        .save()
    val chocolate_coin = simple.copy("chocolate_coin")
        .translate("Chocolate Coin", "巧克力币")
        .food(2, 1f)
        .save()

    val nougat_powder = simple.copy("nougat_powder")
        .translate("Nougat Powder", "牛轧糖粉")
        .food(2, 1f, fastEat = true)
        .save()
    val pez_dust = simple.copy("pez_dust")
        .translate("PEZ Dust", "皮礼士糖粉")
        .food(2, 1f, listOf(1f to NIGHT_VISION.instance((60 / 9f).second)), fastEat = true)
        .save()
    val waffle = simple.copy("waffle")
        .translate("Waffle", "华夫饼")
        .food(2, 1f)
        .save()
    val waffle_nugget = simple.copy("waffle_nugget")
        .translate("Waffle Nugget", "华夫饼碎屑")
        .food(2, 1f)
        .save()
    val candied_cherry = simple.copy("candied_cherry")
        .translate("Candied Cherry", "蜜饯樱桃")
        .food(2, 1f)
        .save()
    val candy_cane = simple.copy("candy_cane")
        .translate("Candy Cane", "拐杖糖")
        .food(2, 1f)
        .save()
    val chewing_gum = simple.copy("chewing_gum")
        .translate("Chewing Gum", "口香糖")
        .food(2, 1f)
        .save()
    val lollipop = simple.copy("lollipop")
        .translate("Lollipop", "棒棒糖")
        .food(2, 1f)
        .save()

    //叶子
    val chocolate_leaf = simple.copy("chocolate_leaf")
        .translate("Chocolate Leaf", "巧克力叶子")
        .food(2, 0.5f)
        .save()
    val white_chocolate_leaf = chocolate_leaf.copy("white_chocolate_leaf")
        .translate("White Chocolate Leaf", "白巧克力叶子")
        .save()
    val caramel_leaf = chocolate_leaf.copy("caramel_leaf")
        .translate("Caramel Leaf", "焦糖叶子")
        .save()
    val candied_cherry_leaf = chocolate_leaf.copy("candied_cherry_leaf")
        .translate("Candied Cherry Leaf", "蜜饯樱桃叶子")
        .save()
    val magical_leaf = chocolate_leaf.copy("magical_leaf")
        .translate("Magical Leaf", "魔法叶子")
        .save()

    val cranfish = simple.copy("cranfish")
        .translate("Cranfish", "蔓越莓鱼")
        .food(2, 1f)
        .save()
    val cranfish_cooked = simple.copy("cranfish_cooked")
        .translate("Cranfish Cooked", "烤蔓越莓鱼")
        .food(2, 1f)
        .save()
    val cranfish_scale = simple.copy("cranfish_scale")
        .translate("Cranfish Scale", "蔓越莓鱼鳞")
        .food(2, 1f)
        .save()


    class ItemBuilder<I : Item>(
        val id: String,
        val factory: ItemFactory<I>,
        internal var propBuilder: PropertiesFactory = {},
    ) {


        private val ops = LinkedHashMap<String, ItemBuilderOp<I>>()
        private val lateUsage = LinkedList<Consumer<Entry<I>>>()
        fun food(
            nut: Int,
            satMod: Float,
            mobEffect: List<Pair<Float, MobEffectInstance>> = listOf(),
            fastEat: Boolean = false,
            alwaysEat: Boolean = false,
        ): ItemBuilder<I> {
            record("food") {
                properties {
                    it.food(
                        FoodProperties.Builder()
                            .nutrition(nut)
                            .saturationMod(satMod)
                            .apply {
                                if (fastEat) fast()
                                if (alwaysEat) alwaysEat()
                                mobEffect.forEach { (probability, effect) ->
                                    effect(effect, probability)
                                }
                            }
                            .build()
                    )
                }
            }
            return this
        }

        /*==============================
                   Core Zone
        ==============================*/
        fun properties(action: PropertiesFactory): ItemBuilder<I> {
            record("properties", overridable = false) {
                val prop = propBuilder
                propBuilder = { prop(it); action(it) }
            }
            return this
        }

        /**
         * 在注册后调用
         * */
        fun lateUsage(action: (Entry<I>) -> Unit) {
            check(!saved)
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
            op: ItemBuilderOp<I>,
        ) {
            check(!saved)
            //在保存时忽略op中里面的record操作
            //用于模拟高级别record覆盖低级别record
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

        fun loadOps(model: ItemBuilder<I>) {
            check(!saved)
            check("_uncopiable" !in model.ops)
            val op = model.ops.filter { (key, _) -> !key.startsWith("_private") }
            ops.putAll(op)
        }

        fun uncopiable() {
            record("_uncopiable") { error("Unable to copy ops as the builder has been marked it as uncopiable") }
        }

        fun save(): Entry<I> {
            saving = true
            ops.safeForEach(desc = { "Operation: $it" }) { (_, op) -> op() }
            saved = true
            val item = register(id.modLoc()) { factory(Properties().apply(propBuilder)) }
            val entry = Entry(id.modLoc(), item, this.v())
            lateUsage.forEach { it.accept(entry) }
            return entry
        }

        fun copy(
            id: String,
            factory: ItemFactory<I> = this.factory,
            properties: PropertiesFactory = this.propBuilder,
            withOps: Boolean = true,
        ): ItemBuilder<I> {
            val new = ItemBuilder(id, factory, properties)
            if (withOps) {
                new.loadOps(this)
            }
            return new
        }

        companion object {
            @Suppress("UNCHECKED_CAST")
            private fun <I : Item> register(
                id: ResourceLocation,
                factory: () -> I,
            ): I {
                return Items.registerItem(id, factory()) as I
            }

        }
    }

    class Entry<I : Item>(
        val id: ResourceLocation,
        val item: I,
        private val builder: V<ItemBuilder<I>>,
    ) {
        operator fun component1() = id
        operator fun component2() = item
        fun copy(
            id: String,
            factory: ItemFactory<I> = builder.get().factory,
            properties: PropertiesFactory = builder.get().propBuilder,
            withOps: Boolean = true,
        ) = builder.get().copy(id, factory, properties, withOps)
    }

}