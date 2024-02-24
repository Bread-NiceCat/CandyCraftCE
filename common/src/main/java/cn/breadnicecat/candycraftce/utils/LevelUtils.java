package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2022/12/24 18:41
 */
public class LevelUtils {
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

	public static Iterable<BlockPos> getNeighbourPos(BlockPos pos) {
		return () -> new Iterator<>() {
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < Direction.values().length;
			}

			@Override
			public BlockPos next() {
				return move(pos, Direction.values()[i++], 1);
			}
		};
	}
}
