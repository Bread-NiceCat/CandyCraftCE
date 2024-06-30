//package cn.breadnicecat.candycraftce.block.fluids;
//
//import cn.breadnicecat.candycraftce.block.CBlocks;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.LevelReader;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.StateDefinition;
//import net.minecraft.world.level.material.FlowingFluid;
//import net.minecraft.world.level.material.Fluid;
//import net.minecraft.world.level.material.FluidState;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.Optional;
//
//import static cn.breadnicecat.candycraftce.item.CItems.CARAMEL_BUCKET;
//import static net.minecraft.world.level.material.FluidState.AMOUNT_FULL;
//
///**
// * Created in 2024/4/4 0:48
// * Project: candycraftce
// *
// * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
// * <p>
// */
//public abstract class CaramelFluid extends FlowingFluid {
//	public CaramelFluid() {
//		registerDefaultState(stateDefinition.any().setValue(LEVEL, AMOUNT_FULL));
//	}
//
//	@Override
//	public @NotNull Fluid getFlowing() {
//		return this;
//	}
//
//	@Override
//	public @NotNull Fluid getSource() {
//		return this;
//	}
//
//	@Override
//	protected boolean canConvertToSource(Level level) {
//		return false;
//	}
//
//	@Override
//	protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
//		Block.dropResources(state, level, pos, state.hasBlockEntity() ? level.getBlockEntity(pos) : null);
//	}
//
//	@Override
//	protected int getSlopeFindDistance(LevelReader level) {
//		return 0;
//	}
//
//	@Override
//	protected int getDropOff(LevelReader level) {
//		return AMOUNT_FULL;
//	}
//
//	@Override
//	public @NotNull Item getBucket() {
//		return CARAMEL_BUCKET.get();
//	}
//
//	@Override
//	protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
//		return false;
//	}
//
//	@Override
//	public int getTickDelay(LevelReader level) {
//		return 0;
//	}
//
//	@Override
//	public void tick(Level level, BlockPos pos, FluidState state) {
//		//no spread
//	}
//
//	@Override
//	protected void randomTick(Level level, BlockPos pos, FluidState state, RandomSource random) {
//		//冷却
//		if (random.nextFloat() > 0.1f) {
//			level.setBlockAndUpdate(pos, CBlocks.CARAMEL_BLOCK.defaultBlockState());
//		}
//		super.randomTick(level, pos, state, random);
//	}
//
//	@Override
//	protected boolean isRandomlyTicking() {
//		return true;
//	}
//
//	@Override
//	protected float getExplosionResistance() {
//		return CBlocks.CARAMEL_BLOCK.get().getExplosionResistance() * 0.8f;
//	}
//
//	@Override
//	protected @NotNull BlockState createLegacyBlock(FluidState state) {
//		return CBlocks.CARAMEL_LIQUID.defaultBlockState();
//	}
//
//	@Override
//	public boolean isSource(FluidState state) {
//		return true;
//	}
//
//	@Override
//	public int getAmount(FluidState state) {
//		return 8;
//	}
//
//	@Override
//	protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
//		builder.add(LEVEL);
//		super.createFluidStateDefinition(builder);
//	}
//
//	@Override
//	public @NotNull Optional<SoundEvent> getPickupSound() {
//		return Optional.of(SoundEvents.BUCKET_FILL);
//	}
//
//}
