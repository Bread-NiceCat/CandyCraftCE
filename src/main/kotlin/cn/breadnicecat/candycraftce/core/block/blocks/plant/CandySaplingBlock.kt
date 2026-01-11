package cn.breadnicecat.candycraftce.core.block.blocks.plant

import cn.breadnicecat.candycraftce.core.tag.CTags
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.SaplingBlock
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import net.minecraft.world.level.block.state.BlockState

/**
 * Created by NiceCat on 2026/1/9.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class CandySaplingBlock(treeGrower: AbstractTreeGrower, properties: Properties) :
    SaplingBlock(treeGrower, properties), ISugarTarget {
    override fun mayPlaceOn(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return level.getBlockState(pos).`is`(CTags.CBlockTags.candy_plant_suitable)
    }

    //去骨粉
    override fun isValidBonemealTarget(
        level: LevelReader,
        pos: BlockPos,
        state: BlockState,
        isClient: Boolean,
    ): Boolean = false

    override fun isBonemealSuccess(
        level: Level,
        random: RandomSource,
        pos: BlockPos,
        state: BlockState,
    ): Boolean = false

    override fun performBonemeal(
        level: ServerLevel,
        random: RandomSource,
        pos: BlockPos,
        state: BlockState,
    ) {
    }

    override fun isValidSugarTarget(
        level: LevelReader,
        pos: BlockPos,
        state: BlockState,
        isClient: Boolean,
    ): Boolean = true

    override fun isSugarSuccess(
        level: ServerLevel,
        rand: RandomSource,
        pos: BlockPos,
        state: BlockState,
    ): Boolean = level.random.nextFloat() < 0.45

    override fun performSugar(
        level: ServerLevel,
        rand: RandomSource,
        pos: BlockPos,
        state: BlockState,
    ) = this.advanceTree(level, pos, state, rand)
}