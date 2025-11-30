package cn.breadnicecat.candycraftce.core.items

import cn.breadnicecat.candycraftce.data.DataUtils.modelFlat
import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.Utils.modLoc
import cn.breadnicecat.candycraftce.utils.V
import cn.breadnicecat.candycraftce.utils.V.Companion.v
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.Items
import java.util.*
import java.util.function.Consumer

typealias ItemFactory<I> = (Properties) -> I
typealias ItemBuilderOp = CItems.ItemBuilder<*>.() -> Unit

object CItems {
    private val simple: ItemFactory<Item> = { Item(it) }

    val licorice = ItemBuilder("licorice", simple)
        .translate("Licorice", "盐甘草糖")
        .modelFlat()
        .food(3, 0.3f)
        .save()
    val honeycomb = ItemBuilder("honeycomb", simple)
        .translate("Honeycomb", "蜜蜡")
        .modelFlat()
        .food(6, 0.1f)
        .save()
    val honeycomb_shard = ItemBuilder("honeycomb_shard", simple)
        .translate("Honeycomb Shard", "蜜蜡碎片")
        .modelFlat()
        .food(6, 0.1f)
        .save()
    val pez = ItemBuilder("pez", simple)
        .translate("PEZ", "皮礼士糖")
        .modelFlat()
        .food(10, 0.5f)
        .save()
    val marshmallow_stick = ItemBuilder("marshmallow_stick", simple)
        .translate("Marshmallow Stick", "棉花软木木棍")
        .modelFlat()
        .food(1, 1f)
        .save()
    val sugar_crystal = ItemBuilder("sugar_crystal", simple)
        .translate("Sugar Crystal", "冰糖")
        .modelFlat()
        .food(4, 2f)
        .save()
    val caramel_brick = ItemBuilder("caramel_brick", simple)
        .translate("Caramel Brick", "焦糖砖")
        .modelFlat()
        .food(2, 1f)
        .save()
    val chocolate_brick = ItemBuilder("chocolate_brick", simple)
        .translate("Chocolate Brick", "巧克力砖")
        .modelFlat()
        .food(2, 1f)
        .save()
    val white_chocolate_brick = ItemBuilder("white_chocolate_brick", simple)
        .translate("White Chocolate Brick", "白巧克力砖")
        .modelFlat()
        .food(2, 1f)
        .save()

    class ItemBuilder<I : Item>(
        val id: String,
        val factory: ItemFactory<I>,
        val properties: Properties = Properties(),
    ) {
        constructor(
            id: String,
            other: ItemBuilder<I>,
            factory: ItemFactory<I> = other.factory,
            properties: Properties = other.properties,
            withOps: Boolean = true,
        ) : this(id, factory, properties) {
            if (withOps) {
                other.ops.forEach { op -> op(this) }
                this.ops.addAll(other.ops)
            }
        }

        private val ops = LinkedList<ItemBuilderOp>()
        private val lateUsage = LinkedList<Consumer<Entry<I>>>()
        fun properties(action: Properties.() -> Unit): ItemBuilder<I> {
            record { properties(action) }
            action(properties)
            return this
        }

        fun food(
            nut: Int,
            satMod: Float,
            vararg mobEffect: Pair<Float, MobEffectInstance>,
        ): ItemBuilder<I> {
            record { food(nut, satMod, *mobEffect) }
            properties.food(
                FoodProperties.Builder()
                    .nutrition(nut)
                    .saturationMod(satMod)
                    .apply {
                        mobEffect.forEach { (probability, effect) ->
                            effect(effect, probability)
                        }
                    }
                    .build()
            )
            return this
        }

        /**
         * 在注册后调用
         * */
        fun lateUsage(action: (Entry<I>) -> Unit) {
            lateUsage.add(action)
        }

        internal fun record(op: ItemBuilderOp) {
            ops.add(op)
        }

        fun setOpUncopyable() {
            ops.add { error("Unable to copy   via the builder has been marked as uncopyable") }
        }

        fun save(): Entry<I> {
            val item = register(id.modLoc()) { factory(properties) }
            val entry = Entry(id.modLoc(), item, this.v())
            lateUsage.forEach { it.accept(entry) }
            return entry
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
            properties: Properties = builder.get().properties,
            withOps: Boolean = true,
        ) = ItemBuilder(id, builder.get(), factory, properties, withOps)
    }

}