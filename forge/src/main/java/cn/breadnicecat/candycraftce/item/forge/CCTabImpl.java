package cn.breadnicecat.candycraftce.item.forge;


import cn.breadnicecat.candycraftce.forge.CandyCraftCEImpl;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Function;

/**
 * Created in 2023/8/9 13:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class CCTabImpl {
	private static final DeferredRegister<CreativeModeTab> REGISTER = CandyCraftCEImpl.createRegister(Registries.CREATIVE_MODE_TAB);

	public static ResourceKey<CreativeModeTab> register(String key, Function<CreativeModeTab.Builder, CreativeModeTab> builder) {
		return REGISTER.register(key, () -> builder.apply(CreativeModeTab.builder())).getKey();
	}


}
