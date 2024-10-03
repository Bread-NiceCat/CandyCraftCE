package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.world.level.material.Fluid;

/**
 * Created in 2024/4/4 0:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class FluidEntry<T extends Fluid> extends SimpleEntry<Fluid, T> {
	
	public FluidEntry(SimpleEntry<Fluid, T> wrapper) {
		super(wrapper);
	}
}
