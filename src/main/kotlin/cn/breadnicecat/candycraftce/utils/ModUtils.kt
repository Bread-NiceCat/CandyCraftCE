package cn.breadnicecat.candycraftce.utils

import cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID
import cn.breadnicecat.candycraftce.CandyCraftCE.clog
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Direction.Axis
import net.minecraft.core.Direction.AxisDirection
import net.minecraft.core.Registry
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.level.Level
import org.apache.logging.log4j.util.StackLocatorUtil
import java.util.stream.Stream
import kotlin.math.max
import kotlin.math.min


object ModUtils {
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
        clog.info("${walker.callerClass.simpleName} loaded")
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

    inline fun Level.ifClient(block: (ClientLevel) -> Unit): Level {
        if (this is ClientLevel) {
            block(this)
        }
        return this
    }

    inline fun Level.ifServer(block: (ServerLevel) -> Unit): Level {
        if (this is ServerLevel) {
            block(this)
        }
        return this
    }

    fun String.modLoc(modId: String = MOD_ID) = ResourceLocation(MOD_ID, this)
    fun String.mcLoc() = ResourceLocation(this)
    fun <V> ResourceLocation.get(register: Registry<V>): V? = register.get(this)

    fun <V : Any> Registry<V>.createKey(id: ResourceLocation) = ResourceKey.create(this.key(), id)!!
    fun <V : Any> Registry<V>.register(id: ResourceLocation, value: V): V = Registry.register(this, id, value)
    fun <V : Any> Registry<V>.register(id: ResourceKey<V>, value: V): V = Registry.register(this, id, value)

    fun BlockPos.MutableBlockPos.set(axis: Axis, value: Int) {
        when (axis) {
            Axis.X -> this.x = value
            Axis.Y -> this.y = value
            Axis.Z -> this.z = value
        }
    }

    fun Axis.direction(positive: Boolean): Direction {
        return Direction.get(
            if (positive) AxisDirection.POSITIVE else AxisDirection.NEGATIVE,
            this
        )
    }

    fun getNeighbourPos(pos: BlockPos): Stream<Pair<Direction, BlockPos>> {
        return Stream.iterate(
            0,
            { it < Direction.entries.size },
            { it + 1 })
            .map {
                val direction = Direction.entries[it]
                direction to pos.relative(direction)
            }
    }

    fun particleBlock(particle: ParticleOptions, level: ClientLevel, pos: BlockPos, step: Double) =
        particleBlock(
            particle,
            level,
            pos.x.toDouble(),
            pos.y.toDouble(),
            pos.z.toDouble(),
            pos.x.toDouble(),
            pos.y.toDouble(),
            pos.z.toDouble(),
            step
        )

    fun particleBlock(particle: ParticleOptions, level: ClientLevel, from: BlockPos, to: BlockPos, step: Double) =
        particleBlock(
            particle,
            level,
            from.x.toDouble(),
            from.y.toDouble(),
            from.z.toDouble(),
            to.x.toDouble(),
            to.y.toDouble(),
            to.z.toDouble(),
            step
        )

    /**
     * 在 [(x,y,z),(x2,y2,z2)](两端都包括) 生成粒子块，就像领地插件圈地时的粒子效果
     */
    @Suppress("DuplicatedCode")
    fun particleBlock(
        particle: ParticleOptions,
        level: ClientLevel,
        x: Double,
        y: Double,
        z: Double,
        x2: Double,
        y2: Double,
        z2: Double,
        step: Double,
    ) {
        val x = min(x, x2)
        val y = min(y, y2)
        val z = min(z, z2)
        val x2 = max(x, x2) + 1
        val y2 = max(y, y2) + 1
        val z2 = max(z, z2) + 1
        var u = x
        while (u < x2 + step) {
            level.addParticle(particle, u, y, z, 0.0, 0.0, 0.0)
            level.addParticle(particle, u, y2, z, 0.0, 0.0, 0.0)
            level.addParticle(particle, u, y, z2, 0.0, 0.0, 0.0)
            level.addParticle(particle, u, y2, z2, 0.0, 0.0, 0.0)
            u += step
        }
        var v = y
        while (v < y2 + step) {
            level.addParticle(particle, x, v, z, 0.0, 0.0, 0.0)
            level.addParticle(particle, x2, v, z, 0.0, 0.0, 0.0)
            level.addParticle(particle, x, v, z2, 0.0, 0.0, 0.0)
            level.addParticle(particle, x2, v, z2, 0.0, 0.0, 0.0)
            v += step
        }
        var w = z
        while (w < z2 + step) {
            level.addParticle(particle, x, y, w, 0.0, 0.0, 0.0)
            level.addParticle(particle, x2, y, w, 0.0, 0.0, 0.0)
            level.addParticle(particle, x, y2, w, 0.0, 0.0, 0.0)
            level.addParticle(particle, x2, y2, w, 0.0, 0.0, 0.0)
            w += step
        }
    }

    operator fun BlockPos.component1() = this.x
    operator fun BlockPos.component2() = this.y
    operator fun BlockPos.component3() = this.z
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

    fun CompoundTag.use(key: String, block: (CompoundTag) -> Unit) {
        val tag = this.getCompound(key)
        block(tag)
        this.put(key, tag)
    }

    val Int.tick get() = TimeUnit.Tick(this)
    val Int.second get() = TimeUnit.Second(this.toFloat())
    val Float.second get() = TimeUnit.Second(this)
    val Long.ms get() = TimeUnit.Millis(this)
    val Int.ms get() = TimeUnit.Millis(this.toLong())
}