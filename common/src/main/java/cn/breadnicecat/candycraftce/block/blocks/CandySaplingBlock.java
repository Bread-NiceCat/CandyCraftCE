package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created in 2024/2/6
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CandySaplingBlock extends SaplingBlock implements ISugarTarget {
	protected CandySaplingBlock(AbstractTreeGrower abstractTreeGrower, Properties properties) {
		super(abstractTreeGrower, properties);
	}
	
	
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(CBlockTags.BT_CANDY_PLANT_SUITABLE);
	}
	
	@Override
	public boolean isValidSugarTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}
	
	@Override
	public boolean isSugarSuccess(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		return rand.nextFloat() < 0.45;
	}
	
	@Override
	public void performSugar(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		advanceTree(level, pos, state, rand);
	}
	
	
	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return false;
	}
	
	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return false;
	}
	
	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
	}
}
