package cn.breadnicecat.candycraftce.core.block.blocks

import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.FallingBlock
import net.minecraft.world.level.block.state.BlockState

class SugarBlock(properties: Properties) : FallingBlock(properties) {
    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        //不要粒子效果
    }
}