package cn.breadnicecat.candycraftce.core.block.blocks.jelly

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * Created by NiceCat on 2026/2/27.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * @param deltaYMovement       Y加速度
 * @param fallDamageMultiplier 摔落减伤,[0-1],0=无伤,1=全伤
 */
open class TrampoJellyBlock(
    properties: Properties,
    val deltaYMovement: Double?,
    val fallDamageMultiplier: Float?,
) :
    JellyBlock(properties) {

    override fun fallOn(level: Level, state: BlockState, pos: BlockPos, entity: Entity, fallDistance: Float) {
        if (fallDamageMultiplier != null) {
            entity.causeFallDamage(fallDistance, fallDamageMultiplier, entity.damageSources().fall())
        } else {
            super.fallOn(level, state, pos, entity, fallDistance)
        }
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (deltaYMovement != null) {
            val movement = entity.deltaMovement
            if (deltaYMovement != 0.0 && movement.y() <= 0 && !entity.isShiftKeyDown) {
                entity.deltaMovement = movement.add(0.0, deltaYMovement, 0.0)
            }
        }
        super.entityInside(state, level, pos, entity)
    }
}