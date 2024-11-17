package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.level.foliage_placer.CandiedCherryFoliagePlacer;
import cn.breadnicecat.candycraftce.level.foliage_placer.FancyCaramelFoliagePlacer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.OptionalInt;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.level.CPlacedFeatures.*;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static java.util.List.of;
import static net.minecraft.data.worldgen.features.FeatureUtils.register;
import static net.minecraft.data.worldgen.placement.PlacementUtils.isEmpty;
import static net.minecraft.world.level.levelgen.feature.Feature.*;
import static net.minecraft.world.level.levelgen.placement.BlockPredicateFilter.forPredicate;

/**
 * Created in 2024/2/9 19:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * @see net.minecraft.data.worldgen.features.TreeFeatures#bootstrap
 * <p>
 */
public class CConfiguredFeatures {
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_TREE = create("chocolate_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_FANCY_TREE = create("chocolate_fancy_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHOCOLATE_TREE = create("white_chocolate_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHOCOLATE_FANCY_TREE = create("white_chocolate_fancy_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> CARAMEL_TREE = create("caramel_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> CARAMEL_FANCY_TREE = create("caramel_fancy_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> MAGICAL_TREE = create("magical_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> CANDIED_CHERRY_TREE = create("candied_cherry_tree");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PATCH = create("flower_patch");
	public final static ResourceKey<ConfiguredFeature<?, ?>> FLOWER_WITH_MINT_PATCH = create("flower_with_mint_patch");
	public final static ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATCH = create("grass_patch");
	public final static ResourceKey<ConfiguredFeature<?, ?>> WATER_GRASS_PATCH = create("water_grass_patch");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_TREE_FOREST = create("chocolate_tree_forest");
	public final static ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_TREE_PLAINS = create("chocolate_tree_plains");
	public final static ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHOCOLATE_TREE_FOREST = create("white_chocolate_tree_forest");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> HONEYCOMB_ORE = create("honeycomb_ore");
	public final static ResourceKey<ConfiguredFeature<?, ?>> JELLY_ORE = create("jelly_ore");
	public final static ResourceKey<ConfiguredFeature<?, ?>> LICORICE_ORE = create("licorice_ore");
	public final static ResourceKey<ConfiguredFeature<?, ?>> NOUGAT_ORE = create("nougat_ore");
	public final static ResourceKey<ConfiguredFeature<?, ?>> PEZ_ORE = create("pez_ore");
	
	private static final SimpleStateProvider PUDDING = BlockStateProvider.simple(CBlocks.PUDDING.get());
	
	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);
		HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
		//trees
		register(context, CHOCOLATE_TREE, TREE, createOakLike(MARSHMALLOW_LOG.get(), CHOCOLATE_LEAVES.get()).build());
		register(context, CHOCOLATE_FANCY_TREE, TREE, createFancyOakLike(MARSHMALLOW_LOG.get(), CHOCOLATE_LEAVES.get()).build());
		register(context, WHITE_CHOCOLATE_TREE, TREE, createOakLike(LIGHT_MARSHMALLOW_LOG.get(), WHITE_CHOCOLATE_LEAVES.get()).build());
		register(context, WHITE_CHOCOLATE_FANCY_TREE, TREE, createSuperBirchLike(LIGHT_MARSHMALLOW_LOG.get(), WHITE_CHOCOLATE_LEAVES.get()).build());
		register(context, CARAMEL_TREE, TREE, createOakLike(DARK_MARSHMALLOW_LOG.get(), CARAMEL_LEAVES.get()).build());
		register(context, CARAMEL_FANCY_TREE, TREE, createFancyCaramelLike(DARK_MARSHMALLOW_LOG.get(), CARAMEL_LEAVES.get()).build());
		register(context, MAGICAL_TREE, TREE, createDarkOakLike(DARK_MARSHMALLOW_LOG.get(), MAGICAL_LEAVES.get()).build());
		register(context, CANDIED_CHERRY_TREE, TREE, createCandiedCherryLike(MARSHMALLOW_LOG.get(), CANDIED_CHERRY_LEAVES.get()).build());
		
		register(context, FLOWER_PATCH, FLOWER, weightedSimplePatch(64, 7, 3,
				SimpleWeightedRandomList.<BlockState>builder()
						.add(FRAISE_TAGADA_FLOWER.defaultBlockState(), 9)
						.add(GOLDEN_SUGAR_FLOWER.defaultBlockState(), 1),
				of(isEmpty(), forPredicate(BlockPredicate.wouldSurvive(FRAISE_TAGADA_FLOWER.defaultBlockState(), Vec3i.ZERO))))
		);
		register(context, FLOWER_WITH_MINT_PATCH, FLOWER, weightedSimplePatch(64, 7, 3,
				SimpleWeightedRandomList.<BlockState>builder()
						.add(FRAISE_TAGADA_FLOWER.defaultBlockState(), 6)
						.add(GOLDEN_SUGAR_FLOWER.defaultBlockState(), 1)
						.add(ACID_MINT_FLOWER.defaultBlockState(), 3),
				of(isEmpty(), forPredicate(BlockPredicate.wouldSurvive(FRAISE_TAGADA_FLOWER.defaultBlockState(), Vec3i.ZERO))))
		);
		register(context, GRASS_PATCH, RANDOM_PATCH, weightedSimplePatch(32, 7, 3,
				SimpleWeightedRandomList.<BlockState>builder()
						.add(SWEET_GRASS_0.defaultBlockState(), 1)
						.add(SWEET_GRASS_1.defaultBlockState(), 2)
						.add(SWEET_GRASS_2.defaultBlockState(), 3)
						.add(SWEET_GRASS_3.defaultBlockState(), 4),
				of(isEmpty(), forPredicate(BlockPredicate.wouldSurvive(SWEET_GRASS_0.defaultBlockState(), Vec3i.ZERO))))
		);
		register(context, WATER_GRASS_PATCH, RANDOM_PATCH, weightedSimplePatch(32, 7, 3,
				SimpleWeightedRandomList.<BlockState>builder()
						.add(MINT.defaultBlockState())
						.add(ROPE_RASPBERRY.defaultBlockState())
						.add(BANANA_SEAWEED.defaultBlockState()),
				of(forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
						forPredicate(BlockPredicate.wouldSurvive(MINT.defaultBlockState(), Vec3i.ZERO))))
		);
		//ore
		register(context, HONEYCOMB_ORE, ORE, candyEnvOre(17, 0, WHITE_HONEYCOMB_ORE.defaultBlockState(), CBlocks.HONEYCOMB_ORE.defaultBlockState()));
		register(context, JELLY_ORE, ORE, candyEnvOre(9, 0, WHITE_JELLY_ORE.defaultBlockState(), CBlocks.JELLY_ORE.defaultBlockState()));
		register(context, LICORICE_ORE, ORE, candyEnvOre(9, 0, WHITE_LICORICE_ORE.defaultBlockState(), CBlocks.LICORICE_ORE.defaultBlockState()));
		register(context, NOUGAT_ORE, ORE, candyEnvOre(8, 0, WHITE_NOUGAT_ORE.defaultBlockState(), CBlocks.NOUGAT_ORE.defaultBlockState()));
		register(context, PEZ_ORE, ORE, candyEnvOre(4, 0.5f, WHITE_PEZ_ORE.defaultBlockState(), CBlocks.PEZ_ORE.defaultBlockState()));
		
		//impls
		Holder.Reference<PlacedFeature> treeFancyChocolate = placed.getOrThrow(CHOCOLATE_FANCY_TREE_CHECKED);
		Holder.Reference<PlacedFeature> treeChocolate = placed.getOrThrow(CHOCOLATE_TREE_CHECKED);
		Holder.Reference<PlacedFeature> treeFancyWhiteChocolate = placed.getOrThrow(WHITE_CHOCOLATE_FANCY_TREE_CHECKED);
		Holder.Reference<PlacedFeature> treeWhiteChocolate = placed.getOrThrow(WHITE_CHOCOLATE_TREE_CHECKED);
		
		register(context, CHOCOLATE_TREE_FOREST, RANDOM_SELECTOR, new RandomFeatureConfiguration(
				of(new WeightedPlacedFeature(treeFancyChocolate, 1 / 3F)),
				treeChocolate
		));
		register(context, CHOCOLATE_TREE_PLAINS, RANDOM_SELECTOR, new RandomFeatureConfiguration(
				of(new WeightedPlacedFeature(treeFancyChocolate, 1 / 10F)),
				treeChocolate
		));
		register(context, WHITE_CHOCOLATE_TREE_FOREST, RANDOM_SELECTOR, new RandomFeatureConfiguration(
				of(new WeightedPlacedFeature(treeFancyWhiteChocolate, 2 / 3F)),
				treeWhiteChocolate
		));
		
	}
	
	private static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, prefix(name));
	}
	
	private static OreConfiguration candyEnvOre(int size, float discardChanceOnAirExposure,
	                                            BlockState oreWhiteOverride, BlockState oreBlackOverride) {
		return new OreConfiguration(List.of(
				OreConfiguration.target(new TagMatchTest(CBlockTags.BT_BLACK_ORE_OVERRIDEABLE), oreBlackOverride),
				OreConfiguration.target(new TagMatchTest(CBlockTags.BT_WHITE_ORE_OVERRIDEABLE), oreWhiteOverride)
		), size, discardChanceOnAirExposure);
	}
	
	private static RandomPatchConfiguration weightedSimplePatch(int tries, int xz_spread, int y_spread,
	                                                            SimpleWeightedRandomList.Builder<BlockState> entries, List<PlacementModifier> placement) {
		return new RandomPatchConfiguration(tries, xz_spread, y_spread, Holder.direct(new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(SIMPLE_BLOCK, new SimpleBlockConfiguration(
						new WeightedStateProvider(entries)))), placement)));
		
	}
	
	private static TreeConfiguration.TreeConfigurationBuilder createSuperBirchLike(Block log, Block leaves) {
		return createStraightBlobTree(log, leaves, 5, 2, 6, 2).ignoreVines();
	}
	
	private static TreeConfiguration.TreeConfigurationBuilder createOakLike(Block log, Block leaves) {
		return createStraightBlobTree(log, leaves, 4, 2, 0, 2)
				.ignoreVines();
	}
	
	private static TreeConfiguration.TreeConfigurationBuilder createFancyCaramelLike(Block log, Block leaves) {
		return new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(log),
				new StraightTrunkPlacer(16, 0, 0),
				BlockStateProvider.simple(leaves),
				new FancyCaramelFoliagePlacer(),
				new TwoLayersFeatureSize(1, 0, 4, OptionalInt.empty()))
				.ignoreVines()
				.dirt(PUDDING);
	}
	
	//人都看麻了
	//https://zh.minecraft.wiki/w/%E8%87%AA%E5%AE%9A%E4%B9%89%E4%B8%96%E7%95%8C%E7%94%9F%E6%88%90/configured_feature)
	//xLayersFeatureSize指的应该是你在特定的高度下，树叶表现出来的半径
	//limit下面如果只有树干则为0，上面如果有一圈叶子就为1
	private static TreeConfiguration.TreeConfigurationBuilder createCandiedCherryLike(Block log, Block leaves) {
		return new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(log),
				new StraightTrunkPlacer(7, 0, 0),
				BlockStateProvider.simple(leaves),
				new CandiedCherryFoliagePlacer(),
				new TwoLayersFeatureSize(1, 0, 2, OptionalInt.empty()))
				.ignoreVines()
				.dirt(PUDDING);
	}
	
	private static TreeConfiguration.TreeConfigurationBuilder createFancyOakLike(Block log, Block leaves) {
		return new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(log),
				new FancyTrunkPlacer(3, 11, 0),
				BlockStateProvider.simple(leaves),
				new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
				new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
		).ignoreVines().dirt(PUDDING);
	}
	
	private static TreeConfiguration.TreeConfigurationBuilder createDarkOakLike(Block log, Block leaves) {
		return new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(log),
				new DarkOakTrunkPlacer(6, 2, 1),
				BlockStateProvider.simple(leaves),
				new DarkOakFoliagePlacer(ConstantInt.of(0),
						ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
		).ignoreVines().dirt(PUDDING);
	}
	
	private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block logBlock, Block leavesBlock, int baseHeight, int heightRandA, int heightRandB, int radius) {
		return new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(logBlock),
				new StraightTrunkPlacer(baseHeight, heightRandA, heightRandB),
				BlockStateProvider.simple(leavesBlock),
				new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3),
				new TwoLayersFeatureSize(1, 0, 1))
				.dirt(PUDDING);
	}
}
