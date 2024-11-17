package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.block.CBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static cn.breadnicecat.candycraftce.level.CConfiguredFeatures.*;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static net.minecraft.data.worldgen.placement.PlacementUtils.register;
import static net.minecraft.world.level.levelgen.Heightmap.Types.*;

/**
 * Created in 2024/5/1 下午2:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * 仅列举部分使用到的
 */
public class CPlacedFeatures {
	public static final ResourceKey<PlacedFeature> FLOWER_CARAMEL = create("flower_caramel");
	public static final ResourceKey<PlacedFeature> FLOWER_FOREST = create("flower_forest");
	public static final ResourceKey<PlacedFeature> FLOWER_PLAINS = create("flower_plains");
	public static final ResourceKey<PlacedFeature> FLOWER_WHITE_FOREST = create("flower_white_forest");
	
	public static final ResourceKey<PlacedFeature> GRASS_FOREST = create("grass_forest");
	public static final ResourceKey<PlacedFeature> GRASS_PLAINS = create("grass_plains");
	public static final ResourceKey<PlacedFeature> GRASS_WHITE_FOREST = create("grass_white_forest");
	
	public static final ResourceKey<PlacedFeature> WATER_GRASS_OCEAN = create("water_grass_ocean");
	public static final ResourceKey<PlacedFeature> WATER_GRASS_RIVER = create("water_grass_river");
	
	public static final ResourceKey<PlacedFeature> ORE_HONEYCOMB_LOWER = create("ore_honeycomb_lower");
	public static final ResourceKey<PlacedFeature> ORE_HONEYCOMB_MIDDLE = create("ore_honeycomb_middle");
	public static final ResourceKey<PlacedFeature> ORE_HONEYCOMB_UPPER = create("ore_honeycomb_upper");
	public static final ResourceKey<PlacedFeature> ORE_JELLY = create("ore_jelly");
	public static final ResourceKey<PlacedFeature> ORE_LICORICE_LOWER = create("ore_licorice_lower");
	public static final ResourceKey<PlacedFeature> ORE_LICORICE_UPPER = create("ore_licorice_upper");
	public static final ResourceKey<PlacedFeature> ORE_PEZ = create("ore_pez");
	public static final ResourceKey<PlacedFeature> ORE_NOUGAT = create("ore_nougat");
	
	public static final ResourceKey<PlacedFeature> TREES_CARAMEL = create("trees_caramel");
	public static final ResourceKey<PlacedFeature> TREES_CHOCOLATE = create("trees_chocolate");
	public static final ResourceKey<PlacedFeature> TREES_ENCHANTED = create("trees_enchanted");
	public static final ResourceKey<PlacedFeature> TREES_PLAINS = create("trees_plains");
	public static final ResourceKey<PlacedFeature> TREES_WHITE_FOREST = create("trees_white_forest");
	
	public static final ResourceKey<PlacedFeature> CHOCOLATE_TREE_CHECKED = create("chocolate_tree_checked");
	public static final ResourceKey<PlacedFeature> CHOCOLATE_FANCY_TREE_CHECKED = create("chocolate_fancy_tree_checked");
	public static final ResourceKey<PlacedFeature> WHITE_CHOCOLATE_TREE_CHECKED = create("white_chocolate_tree_checked");
	public static final ResourceKey<PlacedFeature> WHITE_CHOCOLATE_FANCY_TREE_CHECKED = create("white_chocolate_fancy_tree_checked");
	public static final ResourceKey<PlacedFeature> CARAMEL_TREE_CHECKED = create("caramel_tree_checked");
	public static final ResourceKey<PlacedFeature> CARAMEL_FANCY_TREE_CHECKED = create("caramel_fancy_tree_checked");
	public static final ResourceKey<PlacedFeature> MAGICAL_TREE_CHECKED = create("magical_tree_checked");
	public static final ResourceKey<PlacedFeature> CANDIED_CHERRY_TREE_CHECKED = create("candied_cherry_tree_checked");
	
	protected static ResourceKey<PlacedFeature> create(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, prefix(name));
	}
	
	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);
		HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
		
		checkSurvive(context, CHOCOLATE_TREE_CHECKED, configured.getOrThrow(CHOCOLATE_TREE), CBlocks.CHOCOLATE_SAPLING.get());
		checkSurvive(context, CHOCOLATE_FANCY_TREE_CHECKED, configured.getOrThrow(CHOCOLATE_FANCY_TREE), CBlocks.CHOCOLATE_SAPLING.get());
		checkSurvive(context, WHITE_CHOCOLATE_TREE_CHECKED, configured.getOrThrow(WHITE_CHOCOLATE_TREE), CBlocks.WHITE_CHOCOLATE_SAPLING.get());
		checkSurvive(context, WHITE_CHOCOLATE_FANCY_TREE_CHECKED, configured.getOrThrow(WHITE_CHOCOLATE_FANCY_TREE), CBlocks.WHITE_CHOCOLATE_SAPLING.get());
		checkSurvive(context, CARAMEL_TREE_CHECKED, configured.getOrThrow(CARAMEL_TREE), CBlocks.CARAMEL_SAPLING.get());
		checkSurvive(context, CARAMEL_FANCY_TREE_CHECKED, configured.getOrThrow(CARAMEL_FANCY_TREE), CBlocks.CARAMEL_SAPLING.get());
		checkSurvive(context, MAGICAL_TREE_CHECKED, configured.getOrThrow(MAGICAL_TREE), CBlocks.CHOCOLATE_SAPLING.get());
		checkSurvive(context, CANDIED_CHERRY_TREE_CHECKED, configured.getOrThrow(CANDIED_CHERRY_TREE), CBlocks.CANDIED_CHERRY_SAPLING.get());
		
		flower(context, FLOWER_PLAINS, configured.getOrThrow(FLOWER_PATCH), 8);
		flower(context, FLOWER_FOREST, configured.getOrThrow(FLOWER_PATCH), 32);
		flower(context, FLOWER_WHITE_FOREST, configured.getOrThrow(FLOWER_PATCH), 48);
		flower(context, FLOWER_CARAMEL, configured.getOrThrow(FLOWER_WITH_MINT_PATCH), 16);
		
		grass(context, GRASS_PLAINS, configured.getOrThrow(GRASS_PATCH), 14);
		grass(context, GRASS_FOREST, configured.getOrThrow(GRASS_PATCH), 8);
		grass(context, GRASS_WHITE_FOREST, configured.getOrThrow(GRASS_PATCH), 6);
		waterGrass(context, WATER_GRASS_RIVER, configured.getOrThrow(WATER_GRASS_PATCH), 1);
		waterGrass(context, WATER_GRASS_OCEAN, configured.getOrThrow(WATER_GRASS_PATCH), 5);
		tree(context, TREES_CHOCOLATE, configured.getOrThrow(CHOCOLATE_TREE_FOREST), simpleWeighedInt(6, 9, 7, 1));
		tree(context, TREES_PLAINS, configured.getOrThrow(CHOCOLATE_TREE_PLAINS), simpleWeighedInt(0, 19, 1, 1));
		tree(context, TREES_WHITE_FOREST, configured.getOrThrow(WHITE_CHOCOLATE_TREE_FOREST), simpleWeighedInt(4, 9, 6, 1));
		treeInline(context, TREES_CARAMEL, placed.getOrThrow(CARAMEL_FANCY_TREE_CHECKED), simpleWeighedInt(6, 9, 7, 1));
		treeInline(context, TREES_ENCHANTED, placed.getOrThrow(MAGICAL_TREE_CHECKED), simpleWeighedInt(6, 9, 7, 1));
		
		Holder.Reference<ConfiguredFeature<?, ?>> oreHoneycomb = configured.getOrThrow(HONEYCOMB_ORE);
		ore(context, ORE_HONEYCOMB_LOWER, oreHoneycomb, 20, TrapezoidHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(192)));
		ore(context, ORE_HONEYCOMB_MIDDLE, oreHoneycomb, 10, TrapezoidHeight.of(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)));
		ore(context, ORE_HONEYCOMB_UPPER, oreHoneycomb, 10, TrapezoidHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(72)));
		ore(context, ORE_JELLY, configured.getOrThrow(JELLY_ORE), 4, TrapezoidHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(32)));
		Holder.Reference<ConfiguredFeature<?, ?>> oreLicorice = configured.getOrThrow(LICORICE_ORE);
		ore(context, ORE_LICORICE_LOWER, oreLicorice, 10, TrapezoidHeight.of(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)));
		ore(context, ORE_LICORICE_UPPER, oreLicorice, 70, TrapezoidHeight.of(VerticalAnchor.absolute(80), VerticalAnchor.top()));
		ore(context, ORE_NOUGAT, configured.getOrThrow(NOUGAT_ORE), 4, UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(20)));
		ore(context, ORE_PEZ, configured.getOrThrow(PEZ_ORE), 14, TrapezoidHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(80)));
	}
	
	/**
	 * @param datamap [data1,weigh1,data2,weigh2,...],length=2n,>0
	 */
	private static WeightedListInt simpleWeighedInt(int... datamap) {
		assertTrue(datamap.length % 2 == 0, "unmatched data map");
		assertTrue(datamap.length > 0, "data map can't be empty");
		SimpleWeightedRandomList.Builder<IntProvider> builder = SimpleWeightedRandomList.builder();
		for (int i = 0; i < datamap.length; i += 2) {
			builder.add(ConstantInt.of(datamap[0]), datamap[i + 1]);
		}
		return new WeightedListInt(builder.build());
	}
	
	private static void ore(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> ore, int count, HeightProvider height) {
		register(context, key, ore, List.of(
				CountPlacement.of(count),
				InSquarePlacement.spread(),
				BiomeFilter.biome(),
				HeightRangePlacement.of(height)
		));
	}
	
	private static void flower(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> flower, int rarityChance) {
		register(context, key, flower, List.of(
				NoiseThresholdCountPlacement.of(-0.8F, 15, 4),
				RarityFilter.onAverageOnceEvery(rarityChance),
				InSquarePlacement.spread(),
				HeightmapPlacement.onHeightmap(MOTION_BLOCKING),
				BiomeFilter.biome()
		));
	}
	
	private static void tree(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> treeChecked, IntProvider count) {
		register(context, key, treeChecked, List.of(
				CountPlacement.of(count),
				InSquarePlacement.spread(),
				HeightmapPlacement.onHeightmap(OCEAN_FLOOR),
				SurfaceWaterDepthFilter.forMaxDepth(0),
				BiomeFilter.biome()
		));
	}
	
	private static void treeInline(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<PlacedFeature> treeChecked, IntProvider count) {
		tree(context, key, Holder.direct(
						new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR,
								new SimpleRandomFeatureConfiguration(HolderSet.direct(treeChecked)))),
				count);
	}
	
	private static void grass(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> grass, int count) {
		register(context, key, grass, List.of(
				CountPlacement.of(count),
				InSquarePlacement.spread(),
				HeightmapPlacement.onHeightmap(WORLD_SURFACE_WG),
				BiomeFilter.biome()
		));
	}
	
	private static void waterGrass(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> waterGrass, int count) {
		register(context, key, waterGrass, List.of(
				CountPlacement.of(count),
				InSquarePlacement.spread(),
				HeightmapPlacement.onHeightmap(OCEAN_FLOOR_WG),
				BiomeFilter.biome()
		));
	}
	
	private static void checkSurvive(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> feature, Block survivor) {
		register(context, key, feature, PlacementUtils.filteredByBlockSurvival(survivor));
	}
	
}
