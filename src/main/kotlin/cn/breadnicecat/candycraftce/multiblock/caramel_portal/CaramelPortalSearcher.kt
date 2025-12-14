package cn.breadnicecat.candycraftce.multiblock.caramel_portal

import cn.breadnicecat.candycraftce.utils.AxisSet
import cn.breadnicecat.candycraftce.utils.ModUtils
import cn.breadnicecat.candycraftce.utils.ModUtils.direction
import cn.breadnicecat.candycraftce.utils.ModUtils.set
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction.Axis
import net.minecraft.core.Direction.Axis.Y
import net.minecraft.world.level.BlockGetter
import java.util.stream.Collectors
import kotlin.math.max

/**
 * Created by NiceCat on 2025/12/10.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class CaramelPortalSearcher(
    val config: PortalConfig,
) {

    fun find(level: BlockGetter, pos: BlockPos): CaramelPortal? {
        if (!config.isEmpty(level.getBlockState(pos))) return null
        val units = HashSet<CaramelPortal>()
        for (axis in Axis.VALUES) {
            if (axis == Y && !config.enableHorizontal) continue

            val unit = findAxis(level, pos, axis)
            if (unit != null) {
                if (config.enableCompound) {
                    units.add(unit)
                } else {
                    break
                }
            }
        }
        return when {
            units.isEmpty() -> null
            units.size == 1 -> units.first()
            else -> CaramelPortal.Compound(units)
        }
    }

    fun findOnFrame(getter: BlockGetter, pos: BlockPos): CaramelPortal? {
        if (!config.isFrame(getter.getBlockState(pos))) return null
        val parts = ModUtils.getNeighbourPos(pos)
            .map { (_, nPos) ->
                find(getter, nPos)
            }
            .filter { it != null }
            .map { it!! }
            .collect(Collectors.toSet())
        return when {
            parts.isEmpty() -> null
            parts.size == 1 -> parts.first()
            else -> CaramelPortal.Compound(parts)
        }
    }

    fun findAxis(
        level: BlockGetter,
        pos: BlockPos,
        axis: Axis,
    ): CaramelPortal.Flat? {
        val pipe2 = AxisSet(axis).not().toMutableList()
        val changeable = axis == Y
        //对应第二个一定是高度
        if (!changeable && pipe2[1] != Y) {
            pipe2.reverse()
        }
        val limMax = max(config.maxHeight, config.maxWidth)
        val bound1 = findBound(level, pos, pipe2[0], limMax) ?: return null
        val bound2 = findBound(level, pos, pipe2[1], limMax) ?: return null

        //范围验证
        val width = bound1.second
        val height = bound2.second
        if (width !in config.limWidth || height !in config.limHeight) {
            //不可变或者 可变但是不在范围内
            if (changeable) {
                if (height !in config.limWidth || width !in config.limHeight) {
                    //仍然不在范围内
                    return null
                }
            } else {
                //不可变
                return null
            }
        }


        val delta1 = bound1.second - bound1.first - 1
        val delta2 = bound2.second - bound2.first - 1
        val base = pos
            .relative(pipe2[0], -delta1)
            .relative(pipe2[1], -delta2)
        val blocks = collectBlocks(level, base, pipe2[0], width, pipe2[1], height) ?: return null
        return CaramelPortal.Flat(base, axis, pipe2, width, height, blocks[0], blocks[1], blocks[2])
    }

    /**
     * ```
     * | = = = = = = = | : 总宽度 = 7 (最后一个i-1)
     * |       = = = = | : 正边界宽度 = 4
     * 8 7 6 0 1 2 3 4 5 : i(<limit+2),7号位是base
     * | = = = = = = = |
     *       ↑pos
     *```
     * @return `[正边界宽度，总宽度]` 如果未找到则返回 null
     */

    private fun findBound(
        level: BlockGetter,
        pos: BlockPos,
        pipe: Axis,
        limit: Int,
    ): Pair<Int, Int>? {
        if (!config.isEmpty(level.getBlockState(pos))) return null

        val mPos = pos.mutable()

        var direction = pipe.direction(true)
        var boundFlag = -1
        for (i in 0 until limit + 2) {
            val state = level.getBlockState(mPos)
            when {
                config.isEmpty(state) -> {}
                config.isFrame(state) -> {
                    if (boundFlag == -1) {
                        //第一个边界
                        boundFlag = i - 1
                        direction = direction.opposite
                        mPos.set(pos)
                    } else {
                        return boundFlag to i - 1
                    }
                }

                else -> break
            }


            mPos.move(direction)
        }
        return null
    }

    /**
     * 收集并验证方块
     * @param len1 `pipe1`方向上传送门内的长度
     * @param len2 `pipe2`方向上传送门内的长度
     * @return `[portals,requiredFrames,optionalFrames]`, 如果在验证时有方块未通过,则返回`null`
     * */
    private fun collectBlocks(
        level: BlockGetter,
        base: BlockPos,
        pipe1: Axis,
        len1: Int,
        pipe2: Axis,
        len2: Int,
    ): Array<Set<BlockPos>>? {
        val arr = Array(3) { LinkedHashSet<BlockPos>() }
        val min1 = base.get(pipe1) - 1
        val min2 = base.get(pipe2) - 1
        val max1 = base.get(pipe1) + len1
        val max2 = base.get(pipe2) + len2
        val pos = base.mutable()
        for (w in min1..max1) {
            pos.set(pipe1, w)
            for (h in min2..max2) {
                pos.set(pipe2, h)
                var flag = 0
                if (w == min1 || w == max1) flag++
                if (h == min2 || h == max2) flag++
                //0->portal
                //1->frame required
                //2->frame optional
                val state = level.getBlockState(pos)
                val cond = if (flag == 0) config.isEmpty else config.isFrame
                if (cond(state)) {
                    arr[flag].add(pos.immutable())
                } else if (flag != 2) {
                    //没有通过测试，且不是可选的框架
                    return null
                }
            }
        }
        @Suppress("UNCHECKED_CAST")
        return arr as Array<Set<BlockPos>>
    }
}