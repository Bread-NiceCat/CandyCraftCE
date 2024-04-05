package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

/**
 * Created in 2024/4/4 0:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public abstract class FluidEntry<T extends Fluid> extends RegistryEntry implements Supplier<T> {
	public FluidEntry(ResourceLocation id) {
		super(id);
	}
}
