package cn.breadnicecat.candycraftce.core.item

import cn.breadnicecat.candycraftce.utils.Arguments
import cn.breadnicecat.candycraftce.utils.CLogUtils.logRegister
import cn.breadnicecat.candycraftce.utils.CUtils.immediate
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.ImmediateScope
import cn.breadnicecat.candycraftce.utils.OperationRecordable
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
    internal val arguments = Arguments.Builder()
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

    fun argument(key: String, value: Any): ItemBuilder<I> {
        arguments[key] = value
        return this
    }

    fun arguments(args: Arguments): ItemBuilder<I> {
        arguments.putAll(args)
        return this
    }

    /**
     * 在注册后调用
     * */
    fun lateUsage(action: (ItemEntry<I>) -> Unit) {
        check(!frozen) { "frozen builder" }
        lateUsage.add(action)
    }

    fun save(): ItemEntry<I> {
        val location = id.modLoc()
        logRegister("Item", location)

        executeRecords()
        val item = register(location) {
            factory(arguments.build(), Properties().apply(propBuilder))
        }
        val entry = ItemEntry(location, item, this.immediate())
        lateUsage.forEach { it.accept(entry) }
        return entry
    }

    @Suppress("UNCHECKED_CAST")
    fun <NI : I> sub(
        id: String,
        factory: ItemFactory<NI>,
        properties: PropertiesFactory = this.propBuilder,
    ): ItemBuilder<NI> {
        val new = ItemBuilder(id, factory, properties)
        new.arguments.putAll(arguments)
        new.copyFrom(this as OperationRecordable<ItemBuilder<NI>>)
        return new
    }


    fun copy(
        id: String,
        properties: PropertiesFactory = this.propBuilder,
    ): ItemBuilder<I> {
        val new = ItemBuilder(id, factory, properties)
        new.arguments.putAll(arguments)
        new.copyFrom(this)
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
        private val builder: ImmediateScope.Immediate<ItemBuilder<I>>,
    ) : ItemLike by item {
        operator fun component1() = id
        operator fun component2() = item

        fun copy(
            id: String,
            properties: PropertiesFactory = builder.get().propBuilder,
        ) = builder.get().copy(id, properties)

        fun <NI : I> sub(
            id: String,
            factory: ItemFactory<NI>,
            properties: PropertiesFactory = builder.get().propBuilder,
        ): ItemBuilder<NI> = builder.get().sub(id, factory, properties)

        fun getDefaultInstance(): ItemStack = item.defaultInstance
    }
}
