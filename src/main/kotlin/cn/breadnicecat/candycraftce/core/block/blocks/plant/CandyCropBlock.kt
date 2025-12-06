package cn.breadnicecat.candycraftce.core.block.blocks.plant

import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.core.block.blocks.PuddingFarmBlock
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import kotlin.math.min
import kotlin.random.Random

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * <p>
 *
 */
class CandyCropBlock(
    properties: Properties,
    val stages: (Int) -> Int,
    val shapes: (Int) -> VoxelShape,
) : CandyPlantBlock(properties), ISugarTarget {
    companion object {
        const val MAX_AGE: Int = 7
        val AGE: IntegerProperty = BlockStateProperties.AGE_7
        val SHAPE_L4 = arrayOf<VoxelShape>(
            box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),  //0
            box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),  //1
            box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),  //2
            box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),  //3
        )

        fun stagesL4(age: Int): Int = when (age) {
            0, 1, 2 -> 0
            3, 4 -> 1
            5, 6 -> 2
            else -> 3
        }

        fun shapesL4(stage: Int): VoxelShape = SHAPE_L4[stage]

        fun createL4(properties: Properties): CandyCropBlock {
            return CandyCropBlock(properties, ::stagesL4, ::shapesL4)
        }

    }

    fun getAge(b: BlockState): Int {
        return b.getValue(AGE)
    }

    fun getStage(b: BlockState): Int {
        return stages(getAge(b))
    }

    @Deprecated("Deprecated in Java")
    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext,
    ): VoxelShape = shapes(getStage(state))

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return level.getBlockState(pos.below()).`is`(CBlocks.sugar_block.block)
                && (level.canSeeSky(pos) || level.getRawBrightness(pos, 0) >= 8)
                && super.canSurvive(state, level, pos)
    }

    /**
     * VanillaCopy
     * @see net.minecraft.world.level.block.CropBlock.randomTick
     */
    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        val age: Int = getAge(state)
        if (level.getRawBrightness(pos, 0) >= 9
            && age < MAX_AGE
            && Random.nextInt((25f / getGrowthSpeed(this, level, pos)).toInt() + 1) == 0
        ) {
            level.setBlock(pos, state.setValue(AGE, age + 1), 2)
        }
    }

    /** VanillaCopy
     *  change `farm` to `pudding_farm`
     *  @see net.minecraft.world.level.block.CropBlock.getGrowthSpeed
     */
    fun getGrowthSpeed(block: Block, level: BlockGetter, pos: BlockPos): Float {
        var speed = 1.0f
        val blockPos = pos.below()
        for (x in -1..1) {
            for (z in -1..1) {
                var boost = 0.0f
                val blockState = level.getBlockState(blockPos.offset(x, 0, z))
                if (blockState.`is`(CBlocks.pudding_farm.block)) {
                    boost = 1.0f
                    if (blockState.getValue(PuddingFarmBlock.MOISTURE) > 0) {
                        boost = 3.0f
                    }
                }
                if (x != 0 || z != 0) {
                    boost /= 4.0f
                }
                speed += boost
            }
        }
        val north = pos.north()
        val south = pos.south()
        val west = pos.west()
        val east = pos.east()

        //东或西 南或北 同时种植相同的作物
        if ((level.getBlockState(west).`is`(block)
                    || level.getBlockState(east).`is`(block))
            && (level.getBlockState(north).`is`(block)
                    || level.getBlockState(south).`is`(block))
        ) {
            speed /= 2.0f
        } else {
            //判定斜角
            if (level.getBlockState(west.north()).`is`(block)
                || level.getBlockState(east.north()).`is`(block)
                || level.getBlockState(east.south()).`is`(block)
                || level.getBlockState(west.south()).`is`(block)
            ) {
                speed /= 2.0f
            }
        }
        return speed
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(AGE)
    }

    override fun isRandomlyTicking(state: BlockState): Boolean = getAge(state) < MAX_AGE

    override fun isValidSugarTarget(
        level: LevelReader,
        pos: BlockPos,
        state: BlockState,
        isClient: Boolean,
    ): Boolean = getAge(state) < MAX_AGE

    override fun isSugarSuccess(
        level: ServerLevel,
        rand: RandomSource,
        pos: BlockPos,
        state: BlockState,
    ): Boolean = rand.nextFloat() < 0.8f

    override fun performSugar(
        level: ServerLevel,
        rand: RandomSource,
        pos: BlockPos,
        state: BlockState,
    ) {
        val age = min(getAge(state) + Random.nextInt(2, 5), MAX_AGE)
        level.setBlock(pos, state.setValue(AGE, age), 2)
    }
}