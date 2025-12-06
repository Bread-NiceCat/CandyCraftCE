package cn.breadnicecat.candycraftce.core.block.blocks.plant

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.state.BlockState

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * <p>
 * @see [net.minecraft.world.item.BoneMealItem.growCrop]
 */
interface ISugarTarget {
    companion object {
        /**
         * @see [cn.breadnicecat.candycraftce.mixin.MixinItem]
         * */
        @JvmStatic
        fun grow(item: ItemStack, level: Level, pos: BlockPos): Boolean {
            val state = level.getBlockState(pos)
            val target = state.block
            if (target is ISugarTarget && target.isValidSugarTarget(level, pos, state, level.isClientSide)) {
                if (level !is ServerLevel) return true
                if (target.isSugarSuccess(level, level.random, pos, state)) {
                    target.performSugar(level, level.random, pos, state)
                    item.shrink(1)
                    return true
                }
            }
            return false
        }
    }

    fun isValidSugarTarget(level: LevelReader, pos: BlockPos, state: BlockState, isClient: Boolean): Boolean
    fun isSugarSuccess(level: ServerLevel, rand: RandomSource, pos: BlockPos, state: BlockState): Boolean
    fun performSugar(level: ServerLevel, rand: RandomSource, pos: BlockPos, state: BlockState)

}