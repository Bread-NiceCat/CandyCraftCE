package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.misc.PuddingColor;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

import static cn.breadnicecat.candycraftce.entity.CEntityTypes.*;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.make;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static net.minecraft.sounds.SoundEvents.AMBIENT_CAVE;
import static net.minecraft.world.entity.MobCategory.CREATURE;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.UNDERGROUND_ORES;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION;

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
	public static final Object2IntOpenHashMap<ResourceKey<Biome>> BIOME_PUDDING_COLORS = make(new Object2IntOpenHashMap<>(), (colors) -> {
		colors.defaultReturnValue(PuddingColor.getDefaultPuddingColor());
		colors.put(CARAMEL_FOREST, new Color(11557928).getRGB());
		colors.put(CHOCOLATE_FOREST, new Color(15641275).getRGB());
		//ENCHANTED_FOREST flexible
		colors.put(ICE_CREAM_FOREST, new Color(16768494).getRGB());
		colors.put(ICE_CREAM_PLAINS, new Color(16768494).getRGB());
		colors.put(PUDDING_PLAINS, new Color(15641275).getRGB());
		colors.put(SUGAR_BEACH, new Color(16030419).getRGB());
		colors.put(DEEP_SUGAR_OCEAN, new Color(12623044).getRGB());
		colors.put(SUGAR_OCEAN, new Color(16030419).getRGB());
		colors.put(LUKEWARM_SUGAR_OCEAN, new Color(16360666).getRGB());
		colors.put(WARM_SUGAR_OCEAN, new Color(16360666).getRGB());
		colors.put(SUGAR_RIVER, new Color(16030419).getRGB());
	});
	
	private static final int OVERWORLD_WATER_COLOR = new Color(4159204).getRGB();
	private static final int OVERWORLD_WATER_FOG_COLOR = new Color(329011).getRGB();
	
	private static ResourceKey<Biome> create(String key) {
		return ResourceKey.create(Registries.BIOME, prefix(key));
	}
	
	public static void bootstrap(BootstrapContext<Biome> context) {
		HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
		HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
		builder(DUNGEONS, context)
				.climate(0.8f, 0.4f, false)
				.effects(0, 0, OVERWORLD_WATER_COLOR, OVERWORLD_WATER_FOG_COLOR)
				.backgroundMusic(Musics.createGameMusic(AMBIENT_CAVE))
				.save();
		builder(CARAMEL_FOREST, context)
				.climate(2f, 0f, false)
				.applyCommonEntities(0.5f)
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.TREES_CARAMEL)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.FLOWER_CARAMEL)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.GRASS_FOREST)
				.applyCommonOres()
				.addFeature(UNDERGROUND_ORES, CPlacedFeatures.ORE_NOUGAT)
				.skyColor(new Color(0xffcc80))
				.fogColor(new Color(13404478))
				.waterColor(new Color(16772659))
				.waterFogColor(new Color(11557928))
				.applyGameMusic()
				.save();
		builder(CHOCOLATE_FOREST, context)
				.climate(0.7f, 0.8f)
				.applyCommonEntities()
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.TREES_CHOCOLATE)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.FLOWER_FOREST)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.GRASS_FOREST)
				.applyCommonOres()
				.skyColor(new Color(16761583))
				.fogColor(new Color(15641275))
				.waterColor(new Color(8729856))
				.waterFogColor(new Color(6893063))
				.applyGameMusic()
				.save();
		builder(ENCHANTED_FOREST, context)
				.climate(0.7f, 0.8f)
				.applyCommonEntities()
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.TREES_ENCHANTED)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.FLOWER_FOREST)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.GRASS_FOREST)
				.applyCommonOres()
				.skyColor(new Color(176, 208, 210))
				.fogColor(new Color(15641275))
				.waterColor(new Color(11579647))
				.waterFogColor(new Color(11579647))
				.applyGameMusic()
				.save();
		builder(ICE_CREAM_FOREST, context)
				.climate(0f, 0.4f)
				.applyCommonEntities()
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.TREES_WHITE_FOREST)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.FLOWER_WHITE_FOREST)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.GRASS_WHITE_FOREST)
				.applyCommonOres()
				.skyColor(new Color(220, 220, 220))
				.fogColor(new Color(16768494))
				.waterColor(new Color(10141930))
				.waterFogColor(new Color(10929639))
				.applyGameMusic()
				.save();
		builder(ICE_CREAM_PLAINS, context)
				.climate(0f, 0.6f)
				.applyCandyCarvers()
				.applyCommonOres()
				.skyColor(new Color(220, 220, 220))
				.fogColor(new Color(16768494))
				.waterColor(new Color(10141930))
				.waterFogColor(new Color(10929639))
				.applyGameMusic()
				.save();
		builder(PUDDING_PLAINS, context)
				.climate(0.8f, 0.4f)
				.applyCommonEntities()
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.TREES_PLAINS)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.GRASS_PLAINS)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.FLOWER_PLAINS)
				.applyCommonOres()
				.skyColor(new Color(15122881))
				.fogColor(new Color(14140159))
				.waterColor(new Color(16230101))
				.waterFogColor(new Color(10249079))
				.applyGameMusic()
				.save();
		builder(SUGAR_BEACH, context)
				.climate(0.5f, 0.5f)
				.addSpawn(CREATURE, WAFFLE_SHEEP.get(), 12, 2, 4)
				.addSpawn(CREATURE, CANDY_CANE_PIG.get(), 10, 2, 4)
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.TREES_PLAINS)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.GRASS_PLAINS)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.FLOWER_FOREST)
				.applyCommonOres()
				.skyColor(new Color(16764144))
				.fogColor(new Color(16754907))
				.waterColor(new Color(16230101))
				.waterFogColor(new Color(10249079))
				.applyGameMusic()
				.save();
		builder(LUKEWARM_SUGAR_OCEAN, context)
				.climate(0.5f, 0.5f)
				.applyWaterEntities(1.5f)
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.WATER_GRASS_OCEAN)
				.applyCommonOres()
				.skyColor(new Color(16764144))
				.fogColor(new Color(16754907))
				.waterColor(new Color(16230101))
				.waterFogColor(new Color(9991810))
				.applyGameMusic()
				.save();
		builder(DEEP_SUGAR_OCEAN, context)
				.climate(0.5f, 0.5f)
				.applyWaterEntities(1.25F)
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.WATER_GRASS_OCEAN)
				.applyCommonOres()
				.skyColor(new Color(12301760))
				.fogColor(new Color(12752601))
				.waterColor(new Color(10849991))
				.waterFogColor(new Color(6187159))
				.applyGameMusic()
				.save();
		builder(SUGAR_OCEAN, context)
				.climate(0.5f, 0.5f)
				.applyWaterEntities()
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.WATER_GRASS_OCEAN)
				.applyCommonOres()
				.skyColor(new Color(16764144))
				.fogColor(new Color(16754907))
				.waterColor(new Color(16230101))
				.waterFogColor(new Color(10249079))
				.applyGameMusic()
				.save();
		builder(WARM_SUGAR_OCEAN, context)
				.climate(0.5f, 0.5f)
				.applyWaterEntities()
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.WATER_GRASS_OCEAN)
				.applyCommonOres()
				.skyColor(new Color(16764144))
				.fogColor(new Color(16754907))
				.waterColor(new Color(16230101))
				.waterFogColor(new Color(9991810))
				.applyGameMusic()
				.save();
		builder(SUGAR_RIVER, context)
				.climate(0.5f, 0.5f)
				.applyWaterEntities()
				.applyCandyCarvers()
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.TREES_PLAINS)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.FLOWER_FOREST)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.GRASS_FOREST)
				.addFeature(VEGETAL_DECORATION, CPlacedFeatures.WATER_GRASS_RIVER)
				.applyCommonOres()
				.skyColor(new Color(16764144))
				.fogColor(new Color(16754907))
				.waterColor(new Color(16230101))
				.waterFogColor(new Color(10249079))
				.applyGameMusic()
				.save();
	}
	
	private static Builder builder(ResourceKey<Biome> key, BootstrapContext<Biome> context) {
		return new Builder(key, context);
	}
	
	static class Builder {
		private final ResourceKey<Biome> key;
		private final BootstrapContext<Biome> context;
		public @NotNull Biome.BiomeBuilder builder = new Biome.BiomeBuilder();
		public @NotNull BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder();
		public @NotNull MobSpawnSettings.Builder mob = new MobSpawnSettings.Builder();
		public @NotNull BiomeGenerationSettings.Builder gen;
		
		public Builder(ResourceKey<Biome> key, BootstrapContext<Biome> context) {
			this.key = key;
			this.context = context;
			this.gen = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
		}
		//=============
		//Base Settings
		//=============
		
		public Builder climate(float temperature, float downfall) {
			builder.temperature(temperature);
			builder.downfall(downfall);
			return this;
		}
		
		public Builder climate(float temperature, float downfall, boolean hasPrecipitation) {
			climate(temperature, downfall);
			builder.hasPrecipitation(hasPrecipitation);
			return this;
		}
		
		public Builder effects(int fog, int water, int waterFog, int sky) {
			fogColor(new Color(fog));
			waterColor(new Color(water));
			waterFogColor(new Color(waterFog));
			skyColor(new Color(sky));
			return this;
		}
		
		
		public Builder fogColor(Color color) {
			effects.fogColor(color.getRGB());
			return this;
		}
		
		public Builder waterColor(Color color) {
			effects.waterColor(color.getRGB());
			return this;
		}
		
		public Builder waterFogColor(Color color) {
			effects.waterFogColor(color.getRGB());
			return this;
		}
		
		public Builder skyColor(Color color) {
			effects.skyColor(color.getRGB());
			return this;
		}
		
		public Builder backgroundMusic(@Nullable Music backgroundMusic) {
			effects.backgroundMusic(backgroundMusic);
			return this;
		}
		
		public Builder addSpawn(MobCategory classification, EntityType<?> type, int weight, int minCount, int maxCount) {
			mob.addSpawn(classification, new SpawnerData(type, weight, minCount, maxCount));
			return this;
		}
		
		public Builder addFeature(GenerationStep.Decoration decoration, ResourceKey<PlacedFeature> feature) {
			gen.addFeature(decoration, feature);
			return this;
		}
		
		public Builder addCarver(GenerationStep.Carving carving, ResourceKey<ConfiguredWorldCarver<?>> carver) {
			gen.addCarver(carving, carver);
			return this;
		}
		
		public void save() {
			context.register(key, builder
					.specialEffects(effects.build())
					.mobSpawnSettings(mob.build())
					.generationSettings(gen.build())
					.build());
		}
		
		//==============
		//Extra Settings
		//==============
		Builder applyGameMusic() {
			backgroundMusic(Musics.GAME);
			return this;
		}
		
		Builder applyCandyCarvers() {
			addCarver(GenerationStep.Carving.AIR, CConfiguredCarver.CAVE);
			addCarver(GenerationStep.Carving.AIR, CConfiguredCarver.CAVE_EXTRA_UNDERGROUND);
			addCarver(GenerationStep.Carving.AIR, CConfiguredCarver.CANYON);
			return this;
		}
		
		Builder applyCommonOres() {
			addFeature(UNDERGROUND_ORES, CPlacedFeatures.ORE_LICORICE_LOWER);
			addFeature(UNDERGROUND_ORES, CPlacedFeatures.ORE_LICORICE_UPPER);
			
			addFeature(UNDERGROUND_ORES, CPlacedFeatures.ORE_HONEYCOMB_LOWER);
			addFeature(UNDERGROUND_ORES, CPlacedFeatures.ORE_HONEYCOMB_MIDDLE);
			addFeature(UNDERGROUND_ORES, CPlacedFeatures.ORE_HONEYCOMB_UPPER);
			
			addFeature(UNDERGROUND_ORES, CPlacedFeatures.ORE_JELLY);
			addFeature(UNDERGROUND_ORES, CPlacedFeatures.ORE_PEZ);
			return this;
		}
		
		Builder applyCommonEntities() {
			return applyCommonEntities(1f);
		}
		
		Builder applyCommonEntities(float rate) {
			addSpawn(CREATURE, WAFFLE_SHEEP.get(), 12, Math.round(3 * rate), Math.round(4 * rate));
			addSpawn(CREATURE, CANDY_CANE_PIG.get(), 10, Math.round(3 * rate), Math.round(4 * rate));
			addSpawn(CREATURE, BUNNY.get(), 10, Math.round(3 * rate), Math.round(4 * rate));
			return this;
		}
		
		Builder applyWaterEntities() {
			return applyWaterEntities(1f);
		}
		
		Builder applyWaterEntities(float rate) {
			addSpawn(CREATURE, CRANFISH.get(), 10, Math.round(3 * rate), Math.round(6 * rate));
			return this;
		}
	}
}
