package cn.breadnicecat.candycraftce.multiblock.caramel_portal

import cn.breadnicecat.candycraftce.utils.AxisSet
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import java.util.stream.Stream

/**
 * Created by NiceCat on 2025/12/9.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
sealed interface CaramelPortal {
    fun getPortals(): Stream<BlockPos>
    fun getRequiredFrames(): Stream<BlockPos>
    fun getOptionalFrames(): Stream<BlockPos>
    fun getPortalsAxis(): Stream<Pair<BlockPos, AxisSet>>
    fun getAllFrames(): Stream<BlockPos> = Stream.concat(getRequiredFrames(), getOptionalFrames())
    fun getUnits(): Stream<Flat>
    fun validate(level: Level, config: PortalConfig): Boolean {
        for (pos in getRequiredFrames()) {
            if (!config.isFrame(level.getBlockState(pos))) return false
        }
        for (pos in getPortals()) {
            if (!config.isEmpty(level.getBlockState(pos))) return false
        }
        return true
    }

    fun build(level: Level, placer: PortalPlacer) {
        getPortalsAxis().forEach { (pos, ax) ->
            level.setBlock(pos, placer.place(ax, level.getBlockState(pos)), 3)
        }
    }

    override fun toString(): String
    override fun hashCode(): Int

    class Flat internal constructor(
        val base: BlockPos,
        val axis: Direction.Axis,
        val pipe2: List<Direction.Axis>,
        val width: Int,
        val height: Int,
        private val portals: Set<BlockPos>,
        private val requiredFrames: Set<BlockPos>,
        private val optionalFrames: Set<BlockPos>,
    ) : CaramelPortal {
        override fun getPortals(): Stream<BlockPos> = portals.stream()
        override fun getRequiredFrames(): Stream<BlockPos> = requiredFrames.stream()
        override fun getOptionalFrames(): Stream<BlockPos> = optionalFrames.stream()
        override fun getPortalsAxis(): Stream<Pair<BlockPos, AxisSet>> = portals.stream()
            .map { it to AxisSet(axis) }

        override fun getUnits(): Stream<Flat> = Stream.of(this)

        val end: BlockPos = base.relative(pipe2[0], width - 1).relative(pipe2[1], height - 1)

        override fun toString(): String = """
          {
            "type": "unit",
            "base": {
                "x": ${base.x},
                "y": ${base.y},
                "z": ${base.z}
            },
            "axis": "$axis",
            "width": $width,
            "height": $height
          }
        """.trimIndent()

        override fun hashCode(): Int {
            var result = width * height
            result = 31 * result + base.hashCode()
            result = 31 * result + axis.hashCode()
            return result
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return true
        }
    }

    class Compound internal constructor(val parts: Set<CaramelPortal>) : CaramelPortal {
        val requiredFrames: Set<BlockPos> by lazy {
            val required = LinkedHashSet<BlockPos>()
            parts.forEach {
                it.getRequiredFrames().forEach(required::add)
            }
            required
        }
        val optionalFrames: Set<BlockPos> by lazy {
            val optional = LinkedHashSet<BlockPos>()
            parts.forEach {
                it.getOptionalFrames().forEach(optional::add)
            }
            optional
        }
        val portals: Map<BlockPos, AxisSet> by lazy {
            val portals = LinkedHashMap<BlockPos, AxisSet.MutableAxisSet>()
            parts.forEach {
                if (it is Flat) {
                    it.getPortals().forEach { pos ->
                        if (pos in portals) {
                            portals[pos]!!.with(it.axis)
                        } else {
                            portals[pos] = AxisSet.MutableAxisSet(it.axis)
                        }
                    }
                } else if (it is Compound) {
                    it.portals.forEach { (pos, axs) ->
                        if (pos in portals) {
                            portals[pos]!!.withOr(axs)
                        } else {
                            portals[pos] = axs.mutable()
                        }
                    }
                }
            }
            portals
        }

        override fun getPortals(): Stream<BlockPos> = portals.keys.stream()
        override fun getRequiredFrames(): Stream<BlockPos> = requiredFrames.stream()
        override fun getOptionalFrames(): Stream<BlockPos> = optionalFrames.stream()
        override fun getPortalsAxis(): Stream<Pair<BlockPos, AxisSet>> =
            portals.entries.stream().map { it.toPair() }

        override fun getUnits(): Stream<Flat> {
            return parts.stream().flatMap { it.getUnits() }
        }

        override fun toString(): String = """
          {
            "type": "compound",
            "parts": [${parts.joinToString(", ")}]
          }
        """.trimIndent()

        override fun hashCode(): Int = parts.hashCode()
        override fun equals(other: Any?): Boolean {
            return this === other
        }

    }
}