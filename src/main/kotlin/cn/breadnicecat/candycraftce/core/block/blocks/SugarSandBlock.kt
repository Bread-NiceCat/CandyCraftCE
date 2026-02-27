package cn.breadnicecat.candycraftce.core.block.blocks

import cn.breadnicecat.candycraftce.core.block.CBlocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.ConcretePowderBlock
import net.minecraft.world.level.block.state.BlockState
import java.awt.Color

class SugarSandBlock(properties: Properties) : ConcretePowderBlock(CBlocks.sugar_block.block, properties) {
    override fun getDustColor(state: BlockState, level: BlockGetter, pos: BlockPos): Int {
        return Color.white.rgb
    }
}