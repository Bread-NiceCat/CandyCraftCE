package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * Created in 2024/8/1 上午10:59
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CaramelLeavesBlock extends LeavesBlock {
	public static final IntegerProperty EXTRA_DISTANCE = IntegerProperty.create("distance_extra", 0, 3);
	
	public CaramelLeavesBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(EXTRA_DISTANCE, 3));
	}
	
	@Override
	protected boolean decaying(BlockState state) {
		return super.decaying(state) && state.getValue(EXTRA_DISTANCE) == 3;//d=10
	}
	
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		level.setBlock(pos, updateDistance(state, level, pos), 3);
	}
	
	private static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int i = 7 + 3;
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (Direction direction : Direction.values()) {
			mutableBlockPos.setWithOffset(pos, direction);
			i = Math.min(i, getDistanceAt(level.getBlockState(mutableBlockPos)) + 1);
			if (i == 1) break;
		}
		int e = 0;
		if (i > 7) {
			e = i - 7;
			i = 7;
		}
		return state.setValue(DISTANCE, i).setValue(EXTRA_DISTANCE, e);
	}
	
	public static int getDistanceAt(BlockState state) {
		if (state.is(BlockTags.LOGS)) {
			return 0;
		}
		if (state.hasProperty(DISTANCE)) {
			int distance = state.getValue(DISTANCE);
			if (state.hasProperty(EXTRA_DISTANCE)) {
				distance += state.getValue(EXTRA_DISTANCE);
			}
			return distance;
		}
		return 7 + 3;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(EXTRA_DISTANCE);
	}
}