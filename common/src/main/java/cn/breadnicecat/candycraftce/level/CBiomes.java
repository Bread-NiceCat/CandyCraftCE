package cn.breadnicecat.candycraftce.level;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.awt.*;

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
	public static final ResourceKey<Biome> CARAMEL_FOREST = create("caramel_forest");
	public static final ResourceKey<Biome> CHOCOLATE_FOREST = create("chocolate_forest");
	public static final ResourceKey<Biome> DEEP_SUGAR_OCEAN = create("deep_sugar_ocean");
	public static final ResourceKey<Biome> DUNGEONS = create("dungeons");
	public static final ResourceKey<Biome> ENCHANTED_FOREST = create("enchanted_forest");
	public static final ResourceKey<Biome> ICE_CREAM_FOREST = create("ice_cream_forest");
	public static final ResourceKey<Biome> ICE_CREAM_PLAINS = create("ice_cream_plains");
	public static final ResourceKey<Biome> LUKEWARM_SUGAR_OCEAN = create("lukewarm_sugar_ocean");
	public static final ResourceKey<Biome> PUDDING_PLAINS = create("pudding_plains");
	public static final ResourceKey<Biome> SUGAR_BEACH = create("sugar_beach");
	public static final ResourceKey<Biome> SUGAR_OCEAN = create("sugar_ocean");
	public static final ResourceKey<Biome> SUGAR_RIVER = create("sugar_river");
	public static final ResourceKey<Biome> WARM_SUGAR_OCEAN = create("warm_sugar_ocean");
	
	private static ResourceKey<Biome> create(String key) {
		return ResourceKey.create(Registries.BIOME, prefix(key));
	}
	
	
	private static final int OVERWORLD_WATER_COLOR = new Color(4159204).getRGB();
	private static final int OVERWORLD_WATER_FOG_COLOR = new Color(329011).getRGB();
	private static final int LAND_SKY_COLOR = new Color(15122881).getRGB();
	private static final int LAND_FOG_COLOR = new Color(14140159).getRGB();
	private static final int LAND_WATER_FOG_COLOR = new Color(10249079).getRGB();
	private static final int LAND_WATER_COLOR = new Color(16230101).getRGB();
	
	//required:
	//  temperature downfall mobSpawnSettings generationSettings
	//  specialEffects(fog water water-fog sky)
	public static void bootstrap(BootstrapContext<Biome> context) {
		context.register(DUNGEONS, new Biome.BiomeBuilder()
				.temperature(0.8f)
				.downfall(0.4f)
				.hasPrecipitation(false)
				.mobSpawnSettings(MobSpawnSettings.EMPTY)
				.generationSettings(BiomeGenerationSettings.EMPTY)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(0)
						.fogColor(0)
						.waterColor(OVERWORLD_WATER_COLOR)
						.waterFogColor(OVERWORLD_WATER_FOG_COLOR).build())
				.build());
	}
}
