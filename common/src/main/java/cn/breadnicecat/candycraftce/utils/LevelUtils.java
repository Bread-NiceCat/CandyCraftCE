package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2022/12/24 18:41
 */
public class LevelUtils {
	public static ItemEntity spawnItemEntity(Level level, @NotNull Vec3 pos, ItemStack stack) {
		return spawnItemEntity(level, pos.x(), pos.y(), pos.z(), stack);
	}

	public static ItemEntity spawnItemEntity(Level level, @NotNull Vec3i pos, ItemStack stack) {
		return spawnItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
	}

	public static ItemEntity spawnItemEntity(@NotNull Level level, double posX, double posY, double posZ, ItemStack stack) {
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
}
