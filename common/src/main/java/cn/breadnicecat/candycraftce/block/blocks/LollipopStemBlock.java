package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.block.CBlocks.LOLLIPOP_FRUIT;

/**
 * Created in 2024/2/18 13:13
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class LollipopStemBlock extends CandyCropBlock {

	private static final VoxelShape[] SHAPE = {
			box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
			box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
			Shapes.block()
	};

	public LollipopStemBlock(Properties properties) {
		super(properties, SHAPE);
	}

	@Override
	public int getStage(BlockState b) {
		return switch (getAge(b)) {
			case 0, 1, 2, 3 -> 0;
			case 4, 5, 6 -> 1;
			default -> 2;
		};
	}

	@SuppressWarnings("all")
	@Override
	public void randomTick(BlockState state, @NotNull ServerLevel level, BlockPos pos, RandomSource random) {
		if (getAge(state) < getMaxAge()) {
			super.randomTick(state, level, pos, random);
		} else {
			BlockPos above = pos.above();
			if (level.getBlockState(above).isAir()) {
				if (random.nextFloat() < (25f / getGrowthSpeed(this, level, pos))) {
					level.setBlockAndUpdate(above, LOLLIPOP_FRUIT.get().defaultBlockState());
				}
			}
		}
	}

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState pState) {
		return true;
	}
}
