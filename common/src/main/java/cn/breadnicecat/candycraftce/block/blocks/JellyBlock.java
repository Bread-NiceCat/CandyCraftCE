package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.min;

/**
 * Created in 2024/2/14 10:04
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class JellyBlock extends Block {
	private final VoxelShape SHAPE = Shapes.create(0d, 0d, 0d, 1d, 0.995d, 1d);
	private final JellyType type;

	public JellyBlock(Properties properties, JellyType type) {
		super(properties);
		this.type = type;

	}

	//  ORIGIN
	//    public static final Block trampojelly = new BlockJelly(2.0D).setHardness(3.0F).setResistance(2000.0F);
	//    public static final Block redTrampojelly = new BlockJelly(4.0D).setHardness(3.0F).setResistance(2000.0F);
	//    public static final Block yellowTrampojelly = new BlockJelly(1.0D).setHardness(3.0F).setResistance(2000.0F);
	//    public static final Block jellyShockAbsorber = new BlockJelly(-1.0D).setHardness(3.0F).setResistance(2000.0F);
	//    public static final Block purpleJellyJump = new BlockJelly(2.1D).setLightLevel(0.8F).setHardness(3.0F).setResistance(2000.0F);
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, @NotNull Entity entity, float fallDistance) {
		entity.causeFallDamage(fallDistance, getFallDamageMultiplier(pos, level, 3), entity.damageSources().fall());
	}

	private static float getFallDamageMultiplier(@NotNull BlockPos pos, @NotNull Level level, int ttl) {
		if (ttl > 0) {
			BlockState state = level.getBlockState(pos);
			if (state.getBlock() instanceof JellyBlock j) {
				float v = j.type.fallDamageMultiplier;
				return v == 0 ? 0f : min(v, getFallDamageMultiplier(pos.below(), level, --ttl));
			}
		}
		return 1f;
	}


	/**
	 * note: stepOn方法，只要玩家在{@code pos.above()}一格方块内就会被调用，不准确
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, @NotNull Entity entity) {
		Vec3 movement = entity.getDeltaMovement();
		if (type.deltaYMovement != 0 && movement.y() <= 0 && !entity.isShiftKeyDown()) {
			entity.setDeltaMovement(movement.add(0d, type.deltaYMovement, 0d));
		}
		super.entityInside(state, level, pos, entity);
	}

	/**
	 * @param deltaYMovement       Y加速度
	 * @param fallDamageMultiplier 摔落减伤,[0-1],0=无伤,1=全伤
	 */
	public record JellyType(double deltaYMovement, float fallDamageMultiplier) {
		public static final JellyType GREEN = new JellyType(2.0, 0.2F);
		public static final JellyType RED = new JellyType(4.0, 0.1F);
		public static final JellyType SOFT = new JellyType(2.0, 0F);
		public static final JellyType BLUE = new JellyType(0.0, 0F);
	}

}
