package cn.breadnicecat.candycraftce.level;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/8/2 下午6:33
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CBiomes {
	public static ResourceKey<Biome> CHOCOLATE_FOREST = create(prefix("chocolate_forest"));
	public static ResourceKey<Biome> ICE_CREAM_FOREST = create(prefix("ice_cream_forest"));
	public static ResourceKey<Biome> ICE_CREAM_PLAINS = create(prefix("ice_cream_plains"));
	
	private static ResourceKey<Biome> create(ResourceLocation loc) {
		return ResourceKey.create(Registries.BIOME, loc);
	}
}
