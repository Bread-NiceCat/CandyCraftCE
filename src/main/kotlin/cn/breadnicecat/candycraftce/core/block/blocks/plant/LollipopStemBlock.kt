package cn.breadnicecat.candycraftce.core.block.blocks.plant

import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.core.item.CItems
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

/**
 * Created in 2024/2/18 13:13
 * Project: candycraftce
 *
 * @author [Bread_NiceCat](https://github.com/BreadNiceCat)
 *
 *
 */
class LollipopStemBlock(properties: Properties) : CandyCropBlock(properties, ::stage, shapes::get) {
    companion object {
        private val shapes = arrayOf<VoxelShape>(
            box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Shapes.block()
        )

        private fun stage(age: Int): Int {
            return when (age) {
                0, 1, 2, 3 -> 0
                4, 5, 6 -> 1
                else -> 2
            }
        }
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (getAge(state) < MAX_AGE) {
            super.randomTick(state, level, pos, random)
        } else {
            val above: BlockPos = pos.above()
            if (level.getBlockState(above).isAir) {
                if (random.nextFloat() < (25f / getGrowthSpeed(this, level, pos))) {
                    level.setBlockAndUpdate(above, CBlocks.lollipop_fruit.block.defaultBlockState())
                }
            }
        }
    }

    override fun getCloneItemStack(level: BlockGetter, pos: BlockPos, state: BlockState): ItemStack? {
        return if (state.getValue(AGE) == MAX_AGE) {
            asItem().defaultInstance
        } else {
            CItems.lollipop_seeds.item.defaultInstance
        }
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return if (context.itemInHand.`is`(CBlocks.lollipop_stem.item!!.item)) {
            defaultBlockState().setValue(AGE, MAX_AGE)
        } else {
            super.getStateForPlacement(context)
        }
    }

    override fun isRandomlyTicking(state: BlockState): Boolean = true

    //stem
    override fun asItem() = CBlocks.lollipop_stem.item!!.item
}
