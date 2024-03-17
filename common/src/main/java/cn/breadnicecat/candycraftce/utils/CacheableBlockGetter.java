package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * 警告：使用此Getter有可能导致状态滞后
 * <p>
 * Created in 2024/3/9 19:55
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CacheableBlockGetter implements BlockGetter, AutoCloseable {
	private final BlockGetter level;
	private final HashMap<BlockPos, BlockEntity> be = new HashMap<>();
	private final HashMap<BlockPos, BlockState> bs = new HashMap<>();
	private final HashMap<BlockPos, FluidState> fs = new HashMap<>();
	private boolean enabled = true;

	protected CacheableBlockGetter(BlockGetter level) {
		this.level = level;
	}

	public static CacheableBlockGetter create(BlockGetter level) {
		if (level instanceof CacheableBlockGetter getter) return getter;
		else return new CacheableBlockGetter(level);
	}

	/**
	 * 删除缓存
	 */
	public void reset() {
		be.clear();
		bs.clear();
		fs.clear();
	}

	/**
	 * 启用/禁用缓存
	 * 同时删除缓存
	 */
	public CacheableBlockGetter setEnabled(boolean en) {
		this.enabled = en;
		reset();
		return this;
	}

	@Nullable
	@Override
	public BlockEntity getBlockEntity(BlockPos pos) {
		return enabled ? be.computeIfAbsent(pos, level::getBlockEntity)
				: level.getBlockEntity(pos);
	}

	@Override
	public @NotNull BlockState getBlockState(BlockPos pos) {
		return enabled ? bs.computeIfAbsent(pos, level::getBlockState)
				: level.getBlockState(pos);
	}

	@Override
	public @NotNull FluidState getFluidState(BlockPos pos) {
		return enabled ? fs.computeIfAbsent(pos, level::getFluidState)
				: level.getFluidState(pos);
	}

	@Override
	public int getHeight() {
		return level.getHeight();
	}

	@Override
	public int getMinBuildHeight() {
		return level.getMinBuildHeight();
	}

	@Override
	public void close() {
		setEnabled(false);
	}
}
