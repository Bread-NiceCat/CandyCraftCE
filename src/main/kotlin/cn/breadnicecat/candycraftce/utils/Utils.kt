package cn.breadnicecat.candycraftce.utils

import cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance


object Utils {
    fun MobEffect.instance(
        duration: TimeUnit = 0.tick,
        amplifier: Int = 0,
        ambient: Boolean = false,
        visible: Boolean = true,
        showIcon: Boolean = visible,
    ): MobEffectInstance {
        return MobEffectInstance(this, duration.tick, amplifier, ambient, visible, showIcon)
    }

    fun String.modLoc(modId: String = MOD_ID) = ResourceLocation(MOD_ID, this)
    fun <V> ResourceLocation.get(register: Registry<V>): V? = register.get(this)

    fun <V : Any> Registry<V>.createKey(id: ResourceLocation) = ResourceKey.create(this.key(), id)!!
    fun <V : Any> Registry<V>.register(id: ResourceLocation, value: V): V = Registry.register(this, id, value)
    fun <V : Any> Registry<V>.register(id: ResourceKey<V>, value: V): V = Registry.register(this, id, value)

    val Int.tick get() = TimeUnit.Tick(this)
    val Int.second get() = TimeUnit.Second(this.toFloat())
    val Float.second get() = TimeUnit.Second(this)

    abstract class TimeUnit {
        abstract val tick: Int
        abstract val second: Float

        class Tick(override val tick: Int) : TimeUnit() {
            override val second: Float get() = this.tick / 20f
        }

        class Second(override val second: Float) : TimeUnit() {
            override val tick: Int get() = (this.second * 20).toInt()
        }

    }
}