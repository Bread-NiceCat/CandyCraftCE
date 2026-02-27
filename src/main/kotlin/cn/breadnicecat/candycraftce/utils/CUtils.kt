package cn.breadnicecat.candycraftce.utils

import cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID
import cn.breadnicecat.candycraftce.utils.TimeUnit.Companion.tick
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
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.level.GameRules
import net.minecraft.world.level.Level
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.util.stream.Stream
import kotlin.math.max
import kotlin.math.min


object CUtils {
    val Int.rgb get() = Color(this)
    val String.rgb
        get() = run {
            require(startsWith("#") || startsWith("0x")) { "Invalid color format" }
            Color(Integer.decode(this))
        }

    /**
     * @param amplifier 药水等级
     * @param ambient 是否显示粒子
     * */
    fun MobEffect.instance(
        duration: TimeUnit = 0.tick,
        amplifier: Int = 0,
        ambient: Boolean = false,
        visible: Boolean = true,
        showIcon: Boolean = visible,
    ): MobEffectInstance {
        return MobEffectInstance(this, duration.tick, amplifier, ambient, visible, showIcon)
    }

    val walker: StackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)

    private val logCache = mutableMapOf<String, Logger>()
    val mainLog = modLogger("Core")
    val registerLog = modLogger("Registry")
    val debugLog = modLogger("Debug")
    val clog: Logger
        get() = modLogger(walker.callerClass.let {
            if (it.kotlin.isCompanion) it.enclosingClass else it
        }.simpleName)

    fun modLogger(tag: String): Logger {
        return logCache.computeIfAbsent(tag) { LoggerFactory.getLogger("CandyCraftCE|${it}") }
    }

    private val signed = mutableSetOf<Class<*>>()
    fun sign() {
        val clazz = walker.callerClass
        require(signed.add(clazz)) { "Class ${clazz.simpleName} is already registered" }
        mainLog.info("${clazz.simpleName} loaded")
    }

    fun logRegister(type: String, id: ResourceLocation) {
        registerLog.info("Registering $type/$id")
    }

    fun isLoaded(modId: String): Boolean = FabricLoader.getInstance().isModLoaded(modId)
    inline fun ifLoaded(modId: String, block: () -> Unit) {
        if (isLoaded(modId)) block()
    }

    inline fun <R> ifDev(block: () -> R): R? {
        return if (FabricLoader.getInstance().isDevelopmentEnvironment) block() else null
    }

    inline fun <R> ifClient(block: () -> R): R? {
        return if (FabricLoader.getInstance().environmentType == EnvType.CLIENT) block() else null
    }

    inline fun <R> ifServer(block: () -> R): R? {
        return if (FabricLoader.getInstance().environmentType != EnvType.CLIENT) block() else null
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

    fun Int.provider(): ConstantInt = ConstantInt.of(this)
    fun Int.generator(): ConstantValue = ConstantValue.exactly(this.toFloat())
    fun IntRange.provider(): UniformInt = UniformInt.of(this.first, this.last)
    fun IntRange.generator(): UniformGenerator = UniformGenerator.between(this.first.toFloat(), this.last.toFloat())
    fun <R> ResourceLocation.toKey(registry: ResourceKey<Registry<R>>): ResourceKey<R> {
        return ResourceKey.create(registry, this)
    }

    fun String.modLoc(modId: String = MOD_ID) = ResourceLocation(MOD_ID, this)
    fun String.mcLoc() = ResourceLocation(this)
    fun <V> ResourceLocation.get(register: Registry<V>): V? = register.get(this)

    fun <V : Any> Registry<V>.createKey(id: ResourceLocation) = ResourceKey.create(this.key(), id)!!
    fun <V : Any> Registry<in V>.register(id: ResourceLocation, value: V): V = Registry.register(this, id, value)
    fun <R : Any, V : R> Registry<R>.register(id: ResourceKey<R>, value: V): V = Registry.register(this, id, value)

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
    fun GameRules.Key<GameRules.BooleanValue>.get(level: Level): Boolean {
        return level.gameRules.getBoolean(this)
    }

    fun GameRules.Key<GameRules.IntegerValue>.get(level: Level): Int {
        return level.gameRules.getInt(this)
    }

    fun <T : GameRules.Value<T>> GameRules.Key<T>.get(level: Level): T {
        return level.gameRules.getRule(this)
    }

    //读取并自动写入复合nbt里面的数据
    fun CompoundTag.use(key: String, block: (CompoundTag) -> Unit) {
        if (key in this) {
            block(getCompound(key))
        } else {
            put(key, CompoundTag().also(block))
        }
    }

    //笛卡尔叉乘
    fun <T> List<T>.descartes(): Stream<Pair<T, T>> {
        return stream().flatMap { first ->
            stream().filter { second -> second != first }
                .map { second ->
                    first to second
                }
        }
    }


    inline fun <T, R> cistrans(
        model: Pair<T, T>,
        actual: Pair<T, T>,
        cis: () -> R,
        trans: () -> R,
        default: () -> R,
        equals: (T, T) -> Boolean = { a, b -> a == b },
    ): R {
        val (modelA, modelB) = model
        val (actualA, actualB) = actual
        return if (equals(modelA, actualA) && equals(modelB, actualB)) {
            cis()
        } else if (equals(modelA, actualB) && equals(modelB, actualA)) {
            trans()
        } else {
            default()
        }
    }
}
