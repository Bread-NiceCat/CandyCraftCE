package cn.breadnicecat.candycraftce.utils

import cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID
import cn.breadnicecat.candycraftce.CandyCraftCE.log
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import org.apache.logging.log4j.util.StackLocatorUtil


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

    private val walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
    fun sign() {
        StackLocatorUtil.getCallerClass(1)
        log.info("${walker.callerClass.simpleName} loaded")
    }

    inline fun ifClient(block: () -> Unit) {
        if (FabricLoader.getInstance().environmentType == EnvType.CLIENT) {
            block()
        }
    }

    inline fun ifServer(block: () -> Unit) {
        if (FabricLoader.getInstance().environmentType != EnvType.CLIENT) {
            block()
        }
    }

    fun String.modLoc(modId: String = MOD_ID) = ResourceLocation(MOD_ID, this)
    fun String.mcLoc() = ResourceLocation(this)
    fun <V> ResourceLocation.get(register: Registry<V>): V? = register.get(this)

    fun <V : Any> Registry<V>.createKey(id: ResourceLocation) = ResourceKey.create(this.key(), id)!!
    fun <V : Any> Registry<V>.register(id: ResourceLocation, value: V): V = Registry.register(this, id, value)
    fun <V : Any> Registry<V>.register(id: ResourceKey<V>, value: V): V = Registry.register(this, id, value)


    fun <K, V> Map<K, V>.safeForEach(onErrorDesc: (K) -> String, action: (Map.Entry<K, V>) -> Unit) {
        safeForEach(
            onError = { entry, tr ->
                when (tr) {
                    is Exception -> throw RuntimeException(onErrorDesc(entry.key), tr)
                    is Error -> throw Error(onErrorDesc(entry.key), tr)
                }
            },
            action = action
        )
    }

    inline fun <K, V> Map<K, V>.safeForEach(
        onError: (Map.Entry<K, V>, Throwable) -> Unit,
        action: (Map.Entry<K, V>) -> Unit,
    ) {
        this.forEach {
            try {
                action(it)
            } catch (e: Throwable) {
                onError(it, e)
            }
        }
    }

    val Int.tick get() = TimeUnit.Tick(this)
    val Int.second get() = TimeUnit.Second(this.toFloat())
    val Float.second get() = TimeUnit.Second(this)
    val Long.ms get() = TimeUnit.Millis(this)
    val Int.ms get() = TimeUnit.Millis(this.toLong())

    abstract class TimeUnit {
        abstract val tick: Int
        abstract val second: Float
        open val millis: Long by lazy { (this.second * 1000).toLong() }

        class Tick(override val tick: Int) : TimeUnit() {
            override val second by lazy { this.tick / 20f }
        }

        class Second(override val second: Float) : TimeUnit() {
            override val tick by lazy { (this.second * 20).toInt() }
        }

        class Millis(override val millis: Long) : TimeUnit() {
            override val tick by lazy { (this.millis / 50).toInt() }
            override val second by lazy { millis / 1000f }

        }


    }
}