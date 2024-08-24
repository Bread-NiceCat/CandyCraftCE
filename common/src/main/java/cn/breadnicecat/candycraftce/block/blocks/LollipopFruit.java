package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.CBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/2/18 13:20
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class LollipopFruit extends CandyPlantBlock {
	public LollipopFruit(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState below = level.getBlockState(pos.below());
		return below.getBlock() instanceof LollipopStemBlock block
				&& block.getAge(below) >= block.getMaxAge();
	}
	
	@Override
	public @NotNull ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return CBlocks.LOLLIPOP_STEM.get().getCloneItemStack(level, pos, state);
	}
}
