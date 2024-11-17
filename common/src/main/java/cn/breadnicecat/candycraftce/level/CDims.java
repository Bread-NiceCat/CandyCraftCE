package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.block.CBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

import java.util.List;
import java.util.Optional;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.make;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/12/31 10:06
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CDims {
	public static final int LAND_HEIGHT = 384, LAND_MIN_Y = -64, LAND_MAX_Y = LAND_MIN_Y + LAND_HEIGHT;
	public static final int DUNGEONS_HEIGHT = 256, DUNGEONS_MIN_Y = 0, DUNGEONS_MAX_Y = DUNGEONS_MIN_Y + DUNGEONS_HEIGHT;
	
	public static final ResourceLocation CANDYCRAFT_LOCATION = prefix("candyland");
	public static final ResourceLocation DUNGEONS_LOCATION = prefix("dungeons");
	
	public static final ResourceKey<Level> CANDYLAND = ResourceKey.create(Registries.DIMENSION, CANDYCRAFT_LOCATION);
	public static final ResourceKey<Level> DUNGEONS = ResourceKey.create(Registries.DIMENSION, DUNGEONS_LOCATION);
	public static final ResourceKey<LevelStem> CANDYLAND_STEM = ResourceKey.create(Registries.LEVEL_STEM, CANDYCRAFT_LOCATION);
	public static final ResourceKey<LevelStem> DUNGEONS_STEM = ResourceKey.create(Registries.LEVEL_STEM, DUNGEONS_LOCATION);
	
	public static void bootstrap(BootstrapContext<LevelStem> context) {
		HolderGetter<DimensionType> types = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(DUNGEONS_STEM, new LevelStem(types.getOrThrow(CDimTypes.DUNGEONS_TYPE),
				new FlatLevelSource(make(() -> {
					var settings = new FlatLevelGeneratorSettings(
							Optional.empty(),
							biomes.getOrThrow(CBiomes.DUNGEONS),
							List.of()
					);
					List<FlatLayerInfo> layersInfo = settings.getLayersInfo();
					layersInfo.add(new FlatLayerInfo(1, CBlocks.JAWBREAKER_BRICKS.get()));
					layersInfo.add(new FlatLayerInfo(DUNGEONS_HEIGHT - 2, Blocks.AIR));
					layersInfo.add(new FlatLayerInfo(1, CBlocks.JAWBREAKER_BRICKS.get()));
					settings.updateLayers();
					return settings;
				}))));
//		HolderLookup.Provider provider = VanillaRegistries.createLookup();
//		var presets = provider.lookup(Registries.WORLD_PRESET).orElseThrow();
//		WorldPresets.bootstrap((BootstrapContext) context);
//		var presets = context.lookup(Registries.WORLD_PRESET);
//		WorldPreset normal = presets.getOrThrow(WorldPresets.NORMAL).value();
//		LevelStem overworld = normal.overworld().orElseThrow();
//		context.register(CANDYLAND_STEM, new LevelStem(types.getOrThrow(CDimTypes.CANDYLAND_TYPE),
//				make(() -> {
//					return new NoiseBasedChunkGenerator(overworld.generator().getBiomeSource(),
//							Holder.direct(((NoiseBasedChunkGenerator) overworld.generator()).generatorSettings().value()));
//				}))
//		);
		
	}
	
}
