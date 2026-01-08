package cn.breadnicecat.candycraftce.core.items

import cn.breadnicecat.candycraftce.CandyCraftCE
import cn.breadnicecat.candycraftce.utils.Arguments
import cn.breadnicecat.candycraftce.utils.ModUtils.modLoc
import cn.breadnicecat.candycraftce.utils.OperationRecordable
import cn.breadnicecat.candycraftce.utils.V
import cn.breadnicecat.candycraftce.utils.V.Companion.v
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import java.util.*
import java.util.function.Consumer


typealias ItemFactory<I> = Arguments.(Properties) -> I
typealias PropertiesFactory = (Properties) -> Unit

class ItemBuilder<I : Item>(
    val id: String,
    val factory: ItemFactory<I>,
    private var propBuilder: PropertiesFactory = {},
) : OperationRecordable<ItemBuilder<I>>() {

    private val lateUsage = LinkedList<Consumer<ItemEntry<I>>>()
    private val arguments = Arguments.Builder()
    fun food(
        nut: Int,
        satMod: Float = 0f,
        mobEffect: List<Pair<Float, MobEffectInstance>> = listOf(),
        fastEat: Boolean = false,
        alwaysEat: Boolean = false,
    ): ItemBuilder<I> {
        record("food") {
            modifyProperties {
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
    fun modifyProperties(action: PropertiesFactory): ItemBuilder<I> {
        record("modifyProperties", overridable = false) {
            val prop = propBuilder
            propBuilder = { prop(it); action(it) }
        }
        return this
    }

    fun argument(key: String, value: Any?): ItemBuilder<I> {
        record("argument", overridable = false) {
            if (value == null) arguments.remove(key)
            else arguments[key] = value
        }
        return this
    }

    fun arguments(vararg args: Pair<String, Any?>): ItemBuilder<I> {
        args.forEach { (key, value) ->
            argument(key, value)
        }
        return this
    }

    /**
     * 在注册后调用
     * */
    fun lateUsage(action: (ItemEntry<I>) -> Unit) {
        check(!frozen)
        lateUsage.add(action)
    }

    fun save(): ItemEntry<I> {
        val location = id.modLoc()
        CandyCraftCE.clog.info("Registering Item/{}", location)

        executeRecords()
        val item = register(location) {
            factory(arguments.build(), Properties().apply(propBuilder))
        }
        val entry = ItemEntry(location, item, this.v())
        lateUsage.forEach { it.accept(entry) }
        return entry
    }

    fun copy(
        id: String,
        factory: ItemFactory<I> = this.factory,
        properties: PropertiesFactory = this.propBuilder,
    ): ItemBuilder<I> {
        val new = ItemBuilder(id, factory, properties)
        new.arguments.putAll(arguments)
        new.copyRecord(this)
        return new
    }

    override val receiver: ItemBuilder<I>
        get() = this

    companion object {
        @Suppress("UNCHECKED_CAST")
        private fun <I : Item> register(
            id: ResourceLocation,
            factory: () -> I,
        ): I {
            return Items.registerItem(id, factory()) as I
        }

    }

    class ItemEntry<I : Item>(
        val id: ResourceLocation,
        val item: I,
        private val builder: V<ItemBuilder<I>>,
    ) : ItemLike {
        operator fun component1() = id
        operator fun component2() = item

        override fun asItem(): Item = item
        fun copy(
            id: String,
            factory: ItemFactory<I> = builder.get().factory,
            properties: PropertiesFactory = builder.get().propBuilder,
        ) = builder.get().copy(id, factory, properties)

        fun getDefaultInstance(): ItemStack = item.defaultInstance
    }
}
