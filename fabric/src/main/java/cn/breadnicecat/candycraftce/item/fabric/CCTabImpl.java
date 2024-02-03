package cn.breadnicecat.candycraftce.item.fabric;


import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Function;

/**
 * Created in 2023/8/9 13:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class CCTabImpl {


	public static ResourceKey<CreativeModeTab> register(String key, Function<CreativeModeTab.Builder, CreativeModeTab> builder) {
		ResourceKey<CreativeModeTab> key1 = createKey(key);
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key1, builder.apply(FabricItemGroup.builder()));
		return key1;
	}

	private static ResourceKey<CreativeModeTab> createKey(String name) {
		return ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceUtils.prefix(name));
	}

}
