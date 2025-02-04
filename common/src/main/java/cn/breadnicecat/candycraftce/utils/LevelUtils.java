package cn.breadnicecat.candycraftce.utils;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2022/12/24 18:41
 */
public class LevelUtils {
	public static ItemEntity spawnItemEntity(@NotNull Level level, Vec3i pos, ItemStack stack) {
		return spawnItemEntity(level, Vec3.atCenterOf(pos), stack);
	}
	
	public static ItemEntity spawnItemEntity(@NotNull Level level, Vec3 pos, ItemStack stack) {
		return spawnItemEntity(level, pos.x(), pos.y(), pos.z(), stack);
		
	}
	
	public static ItemEntity spawnItemEntity
			(@NotNull Level level, double posX, double posY, double posZ, ItemStack stack) {
		if (!stack.isEmpty()) {
			ItemEntity entity = new ItemEntity(level, posX, posY, posZ, stack);
			level.addFreshEntity(entity);
			return entity;
		}
		return null;
	}
	
	public static BlockPos move(BlockPos pos, Direction direction, int distance) {
		return switch (direction) {
			case DOWN -> pos.below(distance);
			case UP -> pos.above(distance);
			case NORTH -> pos.north(distance);
			case SOUTH -> pos.south(distance);
			case WEST -> pos.west(distance);
			case EAST -> pos.east(distance);
		};
	}
	
	public static Stream<BlockPos> getNeighbourPos(BlockPos pos) {
		return Stream.of(Direction.values())
				.map(direction -> pos.relative(direction, 1));
	}
	
	
	public static void particleBlock(ParticleOptions particle, ClientLevel level, BlockPos pos, double step) {
		particleBlock(particle, level, pos, pos, step);
	}
	
	public static void particleBlock(ParticleOptions particle, ClientLevel level, BlockPos from, BlockPos to, double step) {
		particleBlock(particle, level, from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ(), step);
	}
	
	/**
	 * 在 [(x,y,z),(x2,y2,z2)](两端都包括) 生成粒子块，就像领地插件圈地时的粒子效果
	 */
	public static void particleBlock(ParticleOptions particle, ClientLevel level, double x, double y, double z, double x2, double y2, double z2, double step) {
		if (x > x2) {
			double xx = x2;
			x2 = x;
			x = xx;
		}
		if (y > y2) {
			double yy = y2;
			y2 = y;
			y = yy;
		}
		if (z > z2) {
			double zz = z2;
			z2 = z;
			z = zz;
		}
		x2++;
		y2++;
		z2++;
		for (double u = x; u < x2 + step; u += step) {
			level.addParticle(particle, u, y, z, 0, 0, 0);
			level.addParticle(particle, u, y2, z, 0, 0, 0);
			level.addParticle(particle, u, y, z2, 0, 0, 0);
			level.addParticle(particle, u, y2, z2, 0, 0, 0);
		}
		for (double v = y; v < y2 + step; v += step) {
			level.addParticle(particle, x, v, z, 0, 0, 0);
			level.addParticle(particle, x2, v, z, 0, 0, 0);
			level.addParticle(particle, x, v, z2, 0, 0, 0);
			level.addParticle(particle, x2, v, z2, 0, 0, 0);
		}
		for (double w = z; w < z2 + step; w += step) {
			level.addParticle(particle, x, y, w, 0, 0, 0);
			level.addParticle(particle, x2, y, w, 0, 0, 0);
			level.addParticle(particle, x, y2, w, 0, 0, 0);
			level.addParticle(particle, x2, y2, w, 0, 0, 0);
		}
	}
}
