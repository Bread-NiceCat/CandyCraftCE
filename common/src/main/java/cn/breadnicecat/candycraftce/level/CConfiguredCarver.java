package cn.breadnicecat.candycraftce.level;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/10/13 12:27
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CConfiguredCarver {
	public static final ResourceKey<ConfiguredWorldCarver<?>> CAVE = create("cave");
	public static final ResourceKey<ConfiguredWorldCarver<?>> CAVE_EXTRA_UNDERGROUND = create("cave_extra_underground");
	public static final ResourceKey<ConfiguredWorldCarver<?>> CANYON = create("canyon");
	
	private static ResourceKey<ConfiguredWorldCarver<?>> create(String key) {
		return ResourceKey.create(Registries.CONFIGURED_CARVER, prefix(key));
	}
	
	public static void bootstrap(BootstrapContext<ConfiguredWorldCarver<?>> context) {
	
	}
}
