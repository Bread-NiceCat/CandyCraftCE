package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.level.foliage_placer.CandiedCherryFoliagePlacer;
import cn.breadnicecat.candycraftce.level.foliage_placer.FancyCaramelFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static net.minecraft.data.worldgen.features.FeatureUtils.register;

/**
 * Created in 2024/2/9 19:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * @see net.minecraft.data.worldgen.features.TreeFeatures#bootstrap
 * <p>
 */
public class CConfiguredFeatures {
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_TREE = bind("chocolate_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_FANCY_TREE = bind("chocolate_fancy_tree");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHOCOLATE_TREE = bind("white_chocolate_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHOCOLATE_FANCY_TREE = bind("white_chocolate_fancy_tree");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> CARAMEL_TREE = bind("caramel_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> CARAMEL_FANCY_TREE = bind("caramel_fancy_tree");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> MAGICAL_TREE = bind("magical_tree");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> CANDIED_CHERRY_TREE = bind("candied_cherry_tree");
	
	private static ResourceKey<ConfiguredFeature<?, ?>> bind(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, prefix(name));
	}
	
	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		//common
		register(context, CHOCOLATE_TREE, Feature.TREE, createOakLike(MARSHMALLOW_LOG.get(), CHOCOLATE_LEAVES.get()).build());
		register(context, CHOCOLATE_FANCY_TREE, Feature.TREE, createFancyOakLike(MARSHMALLOW_LOG.get(), CHOCOLATE_LEAVES.get()).build());
		
		register(context, WHITE_CHOCOLATE_TREE, Feature.TREE, createOakLike(LIGHT_MARSHMALLOW_LOG.get(), WHITE_CHOCOLATE_LEAVES.get()).build());
		register(context, WHITE_CHOCOLATE_FANCY_TREE, Feature.TREE, createSuperBirchLike(LIGHT_MARSHMALLOW_LOG.get(), WHITE_CHOCOLATE_LEAVES.get()).build());
		
		register(context, CARAMEL_TREE, Feature.TREE, createOakLike(DARK_MARSHMALLOW_LOG.get(), CARAMEL_LEAVES.get()).build());
		register(context, CARAMEL_FANCY_TREE, Feature.TREE, createFancyCaramelLike(DARK_MARSHMALLOW_LOG.get(), CARAMEL_LEAVES.get()).build());
		
		register(context, MAGICAL_TREE, Feature.TREE, createDarkOakLike(DARK_MARSHMALLOW_LOG.get(), MAGICAL_LEAVES.get()).build());
		
		register(context, CANDIED_CHERRY_TREE, Feature.TREE, createCandiedCherryLike(MARSHMALLOW_LOG.get(), CANDIED_CHERRY_LEAVES.get()).build());
	}
	
	private static final SimpleStateProvider PUDDING = BlockStateProvider.simple(CBlocks.PUDDING.get());
	
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
	//https://zh.minecraft.wiki/w/%E8%87%AA%E5%AE%9A%E4%B9%89%E4%B8%96%E7%95%8C%E7%94%9F%E6%88%90/configured_feature
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
