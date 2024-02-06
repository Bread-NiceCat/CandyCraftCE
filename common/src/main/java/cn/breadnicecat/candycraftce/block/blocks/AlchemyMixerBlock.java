package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/2/5
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class AlchemyMixerBlock extends FallingBlock {
	public static final BooleanProperty HANGING = BooleanProperty.create("hanging");
	public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 4);

	public AlchemyMixerBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(LEVEL, 0));
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return super.use(state, level, pos, player, hand, hit);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(HANGING, context.getClickedFace() == Direction.DOWN);
	}

	@Override
	public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		BlockState updated = super.updateShape(state, direction, neighborState, level, pos, neighborPos);
		return (state.getValue(HANGING) && isFree(level.getBlockState(pos.above())))
				? updated.setValue(HANGING, false)
				: updated;
	}

	@Override
	public void tick(@NotNull BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.getValue(HANGING)) super.tick(state, level, pos, random);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HANGING);
		builder.add(LEVEL);
	}
}
