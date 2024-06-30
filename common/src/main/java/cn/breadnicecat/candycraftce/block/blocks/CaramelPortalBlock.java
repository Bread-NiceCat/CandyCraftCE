package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import cn.breadnicecat.candycraftce.level.CDims;
import cn.breadnicecat.candycraftce.misc.CGameRules;
import cn.breadnicecat.candycraftce.misc.muitlblocks.VectorPortalShape;
import cn.breadnicecat.candycraftce.particle.CParticles;
import cn.breadnicecat.candycraftce.utils.Axes;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

import static cn.breadnicecat.candycraftce.block.CBlocks.CARAMEL_PORTAL;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.hate;
import static net.minecraft.world.level.block.Blocks.LAVA;

/**
 * Created in 2023/12/31 9:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CaramelPortalBlock extends Block {
	public static final BooleanProperty X = BooleanProperty.create("x");
	public static final BooleanProperty Y = BooleanProperty.create("y");
	public static final BooleanProperty Z = BooleanProperty.create("z");
	
	public static final VectorPortalShape.PortalConfig CONFIG = new VectorPortalShape.PortalConfig(2, 21, 3, 21,
			true, true,
			b -> b.isAir() || /*b.is(CARAMEL_LIQUID.get()) ||*/ b.is(LAVA) || b.is(CARAMEL_PORTAL.get()),
			b -> b.is(CBlockTags.BT_CARAMEL_PORTAL_FRAME));
	
	public static final BiFunction<Axes, BlockState, BlockState> PLACER = (axes, old) -> {
		if (old.is(CARAMEL_PORTAL.get())) {
			return old.setValue(X, axes.hasX() | old.getValue(X))
					.setValue(Y, axes.hasY() | old.getValue(Y))
					.setValue(Z, axes.hasZ() | old.getValue(Z));
		} else return CARAMEL_PORTAL.defaultBlockState()
				.setValue(X, axes.hasX())
				.setValue(Y, axes.hasY())
				.setValue(Z, axes.hasZ());
	};
	
	
	private static final VoxelShape DEFAULT = Shapes.empty();
	private static final VoxelShape X_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
	private static final VoxelShape Y_AABB = Block.box(0.0, 6.0, 0.0, 16.0, 10.0, 16.0);
	private static final VoxelShape Z_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
	private static final VoxelShape XY_AABB = Shapes.or(X_AABB, Y_AABB);
	private static final VoxelShape XZ_AABB = Shapes.or(X_AABB, Z_AABB);
	private static final VoxelShape YZ_AABB = Shapes.or(Y_AABB, Z_AABB);
	private static final VoxelShape XYZ_AABB = Shapes.or(X_AABB, Y_AABB, Z_AABB);
	
	/**
	 * <pre>
	 * index shape
	 * 0     default
	 * 1     x=1
	 * 2     y=2
	 * 3     x+y=3
	 * 4     z=4
	 * 5     x+z=5
	 * 6     y+z=6
	 * 7     x+y+z=7
	 * </pre>
	 */
	private static final VoxelShape[] AABBs = new VoxelShape[]{DEFAULT, X_AABB, Y_AABB, XY_AABB, Z_AABB, XZ_AABB, YZ_AABB, XYZ_AABB};
	
	public CaramelPortalBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(X, true).setValue(Y, false).setValue(Z, false));
	}
	
	private int getShapeIndex(BlockState state) {
		int flag = 0;
		if (state.getValue(X)) flag += 1;
		if (state.getValue(Y)) flag += 2;
		if (state.getValue(Z)) flag += 4;
		return flag;
	}
	
	@SuppressWarnings("deprecation")
	public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return AABBs[getShapeIndex(state)];
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (!neighborState.is(this)) {
			//两个方块之间未连接
			Direction.Axis[] axes = VectorPortalShape.axis2pipe2(direction.getAxis());
			for (Direction.Axis axe : axes) {
				if (state.getValue(axis2Property(axe))) return Blocks.AIR.defaultBlockState();
			}
		}
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

//	@SuppressWarnings("deprecation")
//	@Override
//	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
//		if (getShapeIndex(state) == 0) level.removeBlock(pos, false);
//	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (level.isClientSide()) return;
//		if (entity instanceof ItemEntity itemEntity) {
//			ItemStack stack = itemEntity.getItem();
//			itemEntity.setItem(ItemStack.EMPTY);
		//交易
//			Vec3 delta = itemEntity.getDeltaMovement();
//			for (CaramelPortalRecipe r : RecipeHelper.getAllRecipesFor(CCManagerRecipe.caramel_portal_type.get())) {
//				if (r.matches(stack, level)) {
//					ItemStack result = r.assemble(stack);
//					level.addFreshEntity(new ItemEntity(level, pPos.getX(), pPos.getY(), pPos.getZ(), result, -delta.x(), delta.y(), -delta.z()));
//					return;
//				}
//			}
//		}else
		if (entity.isAlive() && !entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
			//传送
			ResourceKey<Level> destination = getDestination(level);
			if (destination != null) {
				MinecraftServer server = level.getServer();
				if (server != null) {
					ServerLevel cl = server.getLevel(destination);
					if (cl != null) {
						if (entity instanceof LivingEntity livE) {
							//cn.breadnicecat.candycraftce.mixin.MixinEntity#findDimensionEntryPoint
							if (livE.changeDimension(cl) instanceof LivingEntity newLivE) {
								newLivE.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, (int) (16 * TickUtils.SEC2TICK), 5));
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(100) == 0) {
			level.playLocalSound((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
		}
		for (int i = 0; i < 4; ++i) {
			double d = (double) pos.getX() + random.nextDouble();
			double e = (double) pos.getY() + random.nextDouble();
			double f = (double) pos.getZ() + random.nextDouble();
			double g = ((double) random.nextFloat() - 0.5) * 0.5;
			double h = ((double) random.nextFloat() - 0.5) * 0.5;
			double j = ((double) random.nextFloat() - 0.5) * 0.5;
			int k = random.nextInt(2) * 2 - 1;
			if (level.getBlockState(pos.west()).is(this) || level.getBlockState(pos.east()).is(this)) {
				f = (double) pos.getZ() + 0.5 + 0.25 * (double) k;
				j = random.nextFloat() * 2.0f * (float) k;
			} else {
				d = (double) pos.getX() + 0.5 + 0.25 * (double) k;
				g = random.nextFloat() * 2.0f * (float) k;
			}
			level.addParticle(CParticles.CARAMEL_PORTAL_PARTICLE_TYPE.get(), d, e, f, g, h, j);
		}
	}
	
	/**
	 * @return null, 如果无法传送
	 */
	protected @Nullable ResourceKey<Level> getDestination(Level level) {
		return level.getGameRules().getBoolean(CGameRules.CARAMEL_PORTAL_WORKS) ?
				hate(level.dimension(), CDims.CANDYLAND, Level.OVERWORLD, null)
				: null;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(X, Y, Z);
	}
	
	private BooleanProperty axis2Property(Direction.Axis axis) {
		return switch (axis) {
			case X -> X;
			case Y -> Y;
			case Z -> Z;
		};
	}
	
}
