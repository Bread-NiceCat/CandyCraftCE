package cn.breadnicecat.candycraftce.core.block.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import kotlin.math.min

/**
 * Created by NiceCat on 2025/12/18.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class CaramelLeavesBlock(properties: Properties) : LeavesBlock(properties) {
    companion object {
        const val EXTRA_MAX = 3
        val extra_distance: IntegerProperty = IntegerProperty.create("distance_extra", 0, EXTRA_MAX)
    }

    init {
        registerDefaultState(defaultBlockState().setValue(extra_distance, EXTRA_MAX))
    }

    override fun decaying(state: BlockState): Boolean {
        return super.decaying(state) && state.getValue(extra_distance) == EXTRA_MAX//d=10
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        level.setBlock(pos, updateDistance(state, level, pos), 3)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        super.createBlockStateDefinition(builder)
        builder.add(extra_distance)
    }

    fun updateDistance(state: BlockState, level: LevelAccessor, pos: BlockPos): BlockState {
        var distance = 7 + EXTRA_MAX
        val npos = BlockPos.MutableBlockPos()
        for (direction in Direction.entries) {
            npos.setWithOffset(pos, direction)
            distance = min(distance, getDistanceAt(level.getBlockState(npos)) + 1)
            if (distance == 1) break
        }
        var distanceEx = 0
        if (distance > 7) {
            distanceEx = distance - 7
            distance = 7
        }
        return state
            .setValue(DISTANCE, distance)
            .setValue(extra_distance, distanceEx)
    }

    fun getDistanceAt(state: BlockState): Int {
//        if (state.`is`(CTags.marshmallow_logs.second)) {
        if (state.`is`(BlockTags.LOGS)) {
            return 0
        }
        if (state.hasProperty(DISTANCE)) {
            var distance = state.getValue(DISTANCE)
            if (state.hasProperty(extra_distance)) {
                distance += state.getValue(extra_distance)
            }
            return distance
        }
        return 7 + EXTRA_MAX
    }
}