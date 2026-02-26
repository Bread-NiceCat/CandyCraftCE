package cn.breadnicecat.candycraftce.core.block.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

/**
 * Created by NiceCat on 2026/2/25.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class SugarSpikesBlock(properties: Properties) : Block(properties) {
    companion object {
        val shape: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0)
    }

    @Deprecated("Deprecated in Java")
    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return canSupportCenter(level, pos.below(), Direction.UP)
    }

    @Deprecated("Deprecated in Java")
    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext,
    ): VoxelShape = shape

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        entity.makeStuckInBlock(state, Vec3(0.6, 0.4, 0.6))
        entity.hurt(level.damageSources().generic(), 2f)
//        entity.hurt(CDamageTypes.stepOnSpikes(level.registryAccess()), 2f)
        super.entityInside(state, level, pos, entity)
    }

    override fun fallOn(level: Level, state: BlockState, pos: BlockPos, entity: Entity, fallDistance: Float) {
//        entity.causeFallDamage(fallDistance, 2.5f, CDamageTypes.stepOnSpikes(level.registryAccess()))
        entity.causeFallDamage(fallDistance, 2.5f, level.damageSources().generic())
    }
}