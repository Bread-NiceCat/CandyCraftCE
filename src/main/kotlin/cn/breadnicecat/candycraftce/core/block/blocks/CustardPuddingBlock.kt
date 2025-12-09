package cn.breadnicecat.candycraftce.core.block.blocks

import cn.breadnicecat.candycraftce.core.block.CBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.FluidTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.lighting.LightEngine

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class CustardPuddingBlock(properties: Properties) : Block(properties) {
    companion object {
        val PUDDING_ALT = CBlocks.pudding

        /**
         * VanillaCopy
         * @see net.minecraft.world.level.block.GrassBlock.canBeGrass
         * */
        fun canBeCustard(state: BlockState, levelReader: LevelReader, pos: BlockPos): Boolean {
            val upPos = pos.above()
            val upState = levelReader.getBlockState(upPos)
//		if (blockState.is(Blocks.SNOW) && blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
//			return true;
//		}
            if (upState.fluidState.amount == 8) {
                return false
            }
            val light = LightEngine.getLightBlockInto(
                levelReader,
                state,
                pos,
                upState,
                upPos,
                Direction.UP,
                upState.getLightBlock(levelReader, upPos)
            )
            return light < levelReader.maxLightLevel
        }
    }

    fun canPropagate(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val above = pos.above()
        return canBeCustard(state, level, pos)
                && !level.getFluidState(above).`is`(FluidTags.WATER)
    }
    
    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        //还原为布丁
        if (!canBeCustard(state, level, pos)) {
            level.setBlockAndUpdate(pos, PUDDING_ALT.defaultBlockState())
            return
        }
        //扩散
        if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
            val blockState = defaultBlockState()
            repeat(4) {
                val blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1)
                if (level.getBlockState(blockPos).`is`(PUDDING_ALT.block)
                    && canPropagate(blockState, level, blockPos)
                ) {
                    level.setBlockAndUpdate(blockPos, blockState)
                }
            }
        }
    }
}