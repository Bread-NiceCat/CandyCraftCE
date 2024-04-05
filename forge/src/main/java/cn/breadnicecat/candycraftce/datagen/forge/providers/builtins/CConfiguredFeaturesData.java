package cn.breadnicecat.candycraftce.datagen.forge.providers.builtins;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.level.CConfiguredFeatures;
import cn.breadnicecat.candycraftce.level.CandiedCherryFoliagePlacer;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;

/**
 * Created in 2024/2/9 19:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * @see net.minecraft.data.worldgen.features.TreeFeatures#bootstrap(net.minecraft.data.worldgen.BootstapContext)
 * <p>
 */
public class CConfiguredFeaturesData extends CConfiguredFeatures {

	private static final SimpleStateProvider PUDDING = BlockStateProvider.simple(CBlocks.PUDDING.get());

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		FeatureUtils.register(context, CHOCOLATE_TREE, Feature.TREE, createOakLike(MARSHMALLOW_LOG.get(), CHOCOLATE_LEAVES.get()).build());
		FeatureUtils.register(context, WHITE_CHOCOLATE_TREE, Feature.TREE, createOakLike(LIGHT_MARSHMALLOW_LOG.get(), WHITE_CHOCOLATE_LEAVES.get()).build());
		FeatureUtils.register(context, CARAMEL_TREE, Feature.TREE, createOakLike(DARK_MARSHMALLOW_LOG.get(), CARAMEL_LEAVES.get()).build());

		FeatureUtils.register(context, CHOCOLATE_FANCY_TREE, Feature.TREE, createFancyOakLike(MARSHMALLOW_LOG.get(), CHOCOLATE_LEAVES.get()).build());
		FeatureUtils.register(context, WHITE_CHOCOLATE_FANCY_TREE, Feature.TREE, createFancyOakLike(LIGHT_MARSHMALLOW_LOG.get(), WHITE_CHOCOLATE_LEAVES.get()).build());
		FeatureUtils.register(context, CARAMEL_FANCY_TREE, Feature.TREE, createFancyOakLike(DARK_MARSHMALLOW_LOG.get(), CARAMEL_LEAVES.get()).build());
		FeatureUtils.register(context, MAGIC_FANCY_TREE, Feature.TREE, createFancyOakLike(MARSHMALLOW_LOG.get(), MAGIC_LEAVES.get()).build());

		FeatureUtils.register(context, CANDIED_CHERRY_TREE, Feature.TREE, createCandiedCherryLike(MARSHMALLOW_LOG.get(), CANDIED_CHERRY_LEAVES.get()).build());
	}

	private static TreeConfiguration.TreeConfigurationBuilder createOakLike(Block log, Block leaves) {
		return createStraightBlobTree(log, leaves, 4, 2, 0, 2)
				.ignoreVines();
	}
	
	private static TreeConfiguration.TreeConfigurationBuilder createCandiedCherryLike(Block log, Block leaves) {
		return new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(log),
				new StraightTrunkPlacer(7, 0, 0),
				BlockStateProvider.simple(leaves),
				new CandiedCherryFoliagePlacer(),
				new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
				.ignoreVines()
				.dirt(PUDDING);
	}

	private static TreeConfiguration.TreeConfigurationBuilder createFancyOakLike(Block log, Block leaves) {
		return new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(log),
				new FancyTrunkPlacer(3, 11, 0),
				BlockStateProvider.simple(leaves),
				new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
				new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
				.ignoreVines()
				.dirt(PUDDING);
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
