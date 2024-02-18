package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.CBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.min;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/20 14:29
 */
public class CandyCropBlock extends CandyPlantBlock implements ISugarTarget {

	public static final int MAX_AGE = 7;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
	protected final VoxelShape[] shapes;
	private static VoxelShape[] SHAPE_L4 = new VoxelShape[]{
			box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),//0
			box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),//1
			box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),//2
			box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),//3
	};


	public int getMaxAge() {
		return MAX_AGE;
	}

	public CandyCropBlock(Properties properties, VoxelShape[] shapes) {
		super(properties);
		this.shapes = shapes;
		registerDefaultState(stateDefinition.any().setValue(AGE, 0));
	}

	public static CandyCropBlock createL4(Properties properties) {
		return new CandyCropBlock(properties, SHAPE_L4);
	}

	public int getAge(BlockState b) {
		return b.getValue(AGE);
	}


	/**
	 * @return [0, {@code shapes.length}]
	 */
	public int getStage(BlockState b) {
		return switch (getAge(b)) {
			case 0, 1, 2 -> 0;
			case 3, 4 -> 1;
			case 5, 6 -> 2;
			default -> 3;
		};
	}


	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
		return shapes[getStage(pState)];
	}

	public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
		return pLevel.getBlockState(pPos.below()).is(CBlocks.PUDDING_FARMLAND.get())
				&& ((pLevel.getRawBrightness(pPos, 0) >= 8 || pLevel.canSeeSky(pPos)) && super.canSurvive(pState, pLevel, pPos));
	}


	//[VanillaCopy]net.minecraft.world.level.block.CropBlock
	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, @NotNull ServerLevel level, BlockPos pos, RandomSource random) {
		int age;
		if (level.getRawBrightness(pos, 0) >= 9 && (age = this.getAge(state)) < this.getMaxAge() && random.nextInt((int) (25.0f / (getGrowthSpeed(this, level, pos))) + 1) == 0) {
			level.setBlock(pos, state.setValue(AGE, age + 1), 2);
		}
	}

	//[VanillaCopy]net.minecraft.world.level.block.CropBlock
	protected float getGrowthSpeed(Block block, BlockGetter level, @NotNull BlockPos pos) {
		boolean bl2;
		float f = 1.0f;
		BlockPos blockPos = pos.below();
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				float g = 0.0f;
				BlockState blockState = level.getBlockState(blockPos.offset(i, 0, j));
				if (blockState.is(CBlocks.PUDDING_FARMLAND.get())) {
					g = 1.0f;
					if (blockState.getValue(PuddingFarmBlock.MOISTURE) > 0) {
						g = 3.0f;
					}
				}
				if (i != 0 || j != 0) {
					g /= 4.0f;
				}
				f += g;
			}
		}
		BlockPos blockPos2 = pos.north();
		BlockPos blockPos3 = pos.south();
		BlockPos blockPos4 = pos.west();
		BlockPos blockPos5 = pos.east();
		boolean bl = level.getBlockState(blockPos4).is(block) || level.getBlockState(blockPos5).is(block);
		boolean bl3 = bl2 = level.getBlockState(blockPos2).is(block) || level.getBlockState(blockPos3).is(block);
		if (bl && bl2) {
			f /= 2.0f;
		} else {
			boolean bl32;
			boolean bl4 = bl32 = level.getBlockState(blockPos4.north()).is(block) || level.getBlockState(blockPos5.north()).is(block) || level.getBlockState(blockPos5.south()).is(block) || level.getBlockState(blockPos4.south()).is(block);
			if (bl32) {
				f /= 2.0f;
			}
		}
		return f;
	}

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState pState) {
		return getAge(pState) < getMaxAge();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
		pBuilder.add(AGE);
	}

	@Override
	public boolean isValidSugarTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return getAge(state) < getMaxAge();
	}

	@Override
	public boolean isSugarSuccess(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		return rand.nextFloat() < 0.80;
	}

	@Override
	public void performSugar(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		int age = min(getAge(state) + Mth.nextInt(level.random, 2, 5), getMaxAge());
		level.setBlock(pos, state.setValue(AGE, age), 2);
	}
}
