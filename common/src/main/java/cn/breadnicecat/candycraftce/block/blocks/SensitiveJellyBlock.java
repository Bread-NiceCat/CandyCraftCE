package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/2/14 10:54
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SensitiveJellyBlock extends JellyBlock {
	
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final JellyType SENSITIVE = new JellyType(0.0, 1F);
	
	public SensitiveJellyBlock(Properties properties, JellyType type) {
		super(properties, type);
		registerDefaultState(stateDefinition.any().setValue(POWERED, false));
	}
	
	public SensitiveJellyBlock(Properties properties) {
		this(properties, SENSITIVE);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		level.scheduleTick(pos, this, 1);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState state, ServerLevel level, final BlockPos pos, RandomSource random) {
		BlockPos detect = pos;
		BlockPos above = pos.above();
		BlockState state1 = level.getBlockState(above);
		if (state1.is(CBlockTags.BT_JELLY.b()) && !state1.is(this)) {
			detect = above;
		}
		boolean powered = !level.getEntitiesOfClass(LivingEntity.class, new AABB(
				detect.getX(),
				detect.getY() + 1,
				detect.getZ(),
				detect.getX() + 1,
				detect.getY() + 1.5,
				detect.getZ() + 1
		)).isEmpty();
		
		if (powered != state.getValue(POWERED)) level.setBlockAndUpdate(pos, state.setValue(POWERED, powered));
		level.scheduleTick(pos, this, 1);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
		super.createBlockStateDefinition(builder);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isSignalSource(@NotNull BlockState state) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getSignal(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
		return state.getValue(POWERED) ? Redstone.SIGNAL_MAX : Redstone.SIGNAL_NONE;
	}
	
	
}
