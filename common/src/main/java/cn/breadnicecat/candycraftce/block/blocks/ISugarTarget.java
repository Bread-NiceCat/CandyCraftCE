package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.mixin.plants.MixinItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created in 2024/2/13 10:05
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * @see MixinItem
 */
public interface ISugarTarget {
	/**
	 * @see BoneMealItem#growCrop(ItemStack, Level, BlockPos)
	 */
	static boolean grow(ItemStack item, Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() instanceof ISugarTarget target && target.isValidSugarTarget(level, pos, state, level.isClientSide)) {
			if (level.isClientSide) return true;
			if (target.isSugarSuccess((ServerLevel) level, level.random, pos, state)) {
				target.performSugar((ServerLevel) level, level.random, pos, state);
				item.shrink(1);
				return true;
			}
		}
		return false;
	}
	
	boolean isValidSugarTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient);
	
	boolean isSugarSuccess(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state);
	
	void performSugar(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state);
	
}
