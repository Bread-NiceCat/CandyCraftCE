package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

import static cn.breadnicecat.candycraftce.block.CBlocks.PUDDING;

/**
 * Created in 2024/1/28 15:59
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * @see net.minecraft.world.level.block.SpreadingSnowyDirtBlock
 */
public class CustardPudding extends Block {
	public static final Block DIRT_LIKE = PUDDING.get();

	public CustardPudding(Properties properties) {
		super(properties);
	}

	private static boolean canBeGrass(BlockState state, LevelReader levelReader, BlockPos pos) {
		BlockPos blockPos = pos.above();
		BlockState blockState = levelReader.getBlockState(blockPos);
//		if (blockState.is(Blocks.SNOW) && blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
//			return true;
//		}
		if (blockState.getFluidState().getAmount() == 8) {
			return false;
		}
		int i = LightEngine.getLightBlockInto(levelReader, state, pos, blockState, blockPos, Direction.UP, blockState.getLightBlock(levelReader, blockPos));
		return i < levelReader.getMaxLightLevel();
	}

//	private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
//		BlockPos blockPos = pos.above();
//		return canBeGrass(state, level, pos) && !level.getFluidState(blockPos).is(FluidTags.WATER);
//	}

	@Override
	@SuppressWarnings("deprecation")
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		//恢复
		if (!canBeGrass(state, level, pos)) {
			level.setBlockAndUpdate(pos, DIRT_LIKE.defaultBlockState());
//			return;
		}
//		奶皮怎么传播？
//		if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
//			BlockState blockState = this.defaultBlockState();
//			for (int i = 0; i < 4; ++i) {
//				BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
//				if (!level.getBlockState(blockPos).is(DIRT_LIKE) || !canPropagate(blockState, level, blockPos)) {
//					continue;
//				}
////				level.setBlockAndUpdate(blockPos, (BlockState) blockState.setValue(SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
//				level.setBlockAndUpdate(blockPos, blockState);
//			}
//		}
	}
}
