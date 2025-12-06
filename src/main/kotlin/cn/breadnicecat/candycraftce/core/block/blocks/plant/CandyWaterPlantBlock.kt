package cn.breadnicecat.candycraftce.core.block.blocks.plant

import net.minecraft.core.BlockPos
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.LiquidBlockContainer
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * <p>
 *
 */
class CandyWaterPlantBlock(properties: Properties) : CandyPlantBlock(properties), LiquidBlockContainer {
    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return state.fluidState.`is`(Fluids.WATER)
                && super.canSurvive(state, level, pos)
    }


    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val fluidState = context.level.getFluidState(context.clickedPos)
        return if (fluidState.`is`(Fluids.WATER)) super.getStateForPlacement(context) else null
    }

    @Deprecated("Deprecated in Java")
    override fun getFluidState(state: BlockState): FluidState =
        Fluids.WATER.getSource(false)

    override fun canPlaceLiquid(
        level: BlockGetter,
        pos: BlockPos,
        state: BlockState,
        fluid: Fluid,
    ): Boolean = false

    override fun placeLiquid(
        level: LevelAccessor,
        pos: BlockPos,
        state: BlockState,
        fluidState: FluidState,
    ): Boolean = false

}