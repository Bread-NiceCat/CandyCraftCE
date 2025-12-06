package cn.breadnicecat.candycraftce.core.block.blocks.plant

import cn.breadnicecat.candycraftce.core.tag.CTags
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * <p>
 *
 */
open class CandyPlantBlock(properties: Properties) : Block(properties) {
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos,
    ): BlockState? {
        return if (state.canSurvive(level, pos))
            super.updateShape(state, direction, neighborState, level, pos, neighborPos)
        else
            Blocks.AIR.defaultBlockState()
    }


    @Deprecated("Deprecated in Java")
    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean =
        level.getBlockState(pos.below()).`is`(CTags.CBlockTags.candy_plant_suitable)
}