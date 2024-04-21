package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

/**
 * Created in 2024/4/4 0:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class FluidEntry<T extends Fluid> extends SimpleEntry<Fluid, T> {
	public FluidEntry(ResourceKey<Fluid> key, Supplier<T> getter) {
		super(key, getter);
	}

	public FluidEntry(Pair<ResourceKey<Fluid>, Supplier<T>> wrapper) {
		super(wrapper);
	}
}
