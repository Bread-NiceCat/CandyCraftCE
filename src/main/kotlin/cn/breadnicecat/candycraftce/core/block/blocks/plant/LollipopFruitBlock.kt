package cn.breadnicecat.candycraftce.core.block.blocks.plant

import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.state.BlockState

/**
 * Created in 2024/2/18 13:20
 * Project: candycraftce
 *
 * @author [Bread_NiceCat](https://github.com/BreadNiceCat)
 *
 *
 */
class LollipopFruitBlock(properties: Properties) : CandyPlantBlock(properties) {
    @Deprecated("Deprecated in Java")
    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val below = level.getBlockState(pos.below())
        val block = below.block
        return block is LollipopStemBlock
                && block.getAge(below) >= CandyCropBlock.MAX_AGE
    }
}
