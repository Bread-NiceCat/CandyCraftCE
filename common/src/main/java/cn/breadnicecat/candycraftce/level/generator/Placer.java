package cn.breadnicecat.candycraftce.level.generator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface Placer {
	boolean addBlock(Level level, BlockPos blockState);
}

