package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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
public class CandySaplingBlock extends SaplingBlock {
	protected CandySaplingBlock(AbstractTreeGrower abstractTreeGrower, Properties properties) {
		super(abstractTreeGrower, properties);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return super.isValidBonemealTarget(level, pos, state, isClient);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return super.isBonemealSuccess(level, random, pos, state);
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		super.performBonemeal(level, random, pos, state);
	}
}
