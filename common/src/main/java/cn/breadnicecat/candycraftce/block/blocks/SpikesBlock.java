package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.misc.CDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/2/14 11:38
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SpikesBlock extends Block {
	public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
	
	public SpikesBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean canSurvive(BlockState state, LevelReader level, @NotNull BlockPos pos) {
		return canSupportCenter(level, pos.below(), Direction.UP);
	}
	
	@Override
	public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		entity.makeStuckInBlock(state, new Vec3(0.6, 0.4, 0.6));
		entity.hurt(CDamageTypes.stepOnSpikes(level.registryAccess()), 2);
		super.entityInside(state, level, pos, entity);
	}
	
	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		entity.causeFallDamage(fallDistance, 2.5F, CDamageTypes.stepOnSpikes(level.registryAccess()));
	}
}
