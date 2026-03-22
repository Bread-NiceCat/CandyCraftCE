package cn.breadnicecat.candycraftce.data.extension.level

import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.core.level.foliage_placer.CandiedCherryFoliagePlacer
import cn.breadnicecat.candycraftce.core.level.foliage_placer.FancyCaramelFoliagePlacer
import net.minecraft.data.worldgen.BootstapContext
import net.minecraft.data.worldgen.features.FeatureUtils
import net.minecraft.resources.ResourceKey
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer
import java.util.*
import java.util.function.Consumer

/**
 * Created by NiceCat on 2026/1/9.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class ConfiguredFeatureDataScope private constructor(val cf: ResourceKey<ConfiguredFeature<*, *>>) {
    companion object {
        private val consumers: MutableList<Consumer<BootstapContext<ConfiguredFeature<*, *>>>> = LinkedList()
        fun bootstrap(context: BootstapContext<ConfiguredFeature<*, *>>) {
            consumers.forEach { it.accept(context) }
        }

        fun <CF : ResourceKey<ConfiguredFeature<*, *>>> CF.data(action: ConfiguredFeatureDataScope.() -> Unit): CF {
            action(ConfiguredFeatureDataScope(this))
            return this
        }
    }

    val pudding: SimpleStateProvider = BlockStateProvider.simple(CBlocks.pudding.block)
    fun <FC : FeatureConfiguration, F : Feature<FC>> register(type: F, config: FC) {
        consumers.add {
            FeatureUtils.register(it, cf, type, config)
        }
    }

    fun createSuperBirchLike(log: Block, leaves: Block): TreeConfigurationBuilder {
        return createStraightBlobTree(log, leaves, 5, 2, 6, 2).ignoreVines()
    }

    fun createOakLike(log: Block, leaves: Block): TreeConfigurationBuilder {
        return createStraightBlobTree(log, leaves, 4, 2, 0, 2)
            .ignoreVines()
    }

    fun createFancyCaramelLike(log: Block, leaves: Block): TreeConfigurationBuilder {
        return TreeConfigurationBuilder(
            BlockStateProvider.simple(log),
            StraightTrunkPlacer(16, 0, 0),
            BlockStateProvider.simple(leaves),
            FancyCaramelFoliagePlacer(),
            TwoLayersFeatureSize(1, 0, 4, OptionalInt.empty())
        )
            .ignoreVines()
            .dirt(pudding)
    }

    //人都看麻了
    //https://zh.minecraft.wiki/w/%E8%87%AA%E5%AE%9A%E4%B9%89%E4%B8%96%E7%95%8C%E7%94%9F%E6%88%90/configured_feature
    //x_layers_feature_size指的应该是你在特定的高度下，树叶表现出来的半径
    //limit下面如果只有树干则为0，上面如果有一圈叶子就为1
    fun createCandiedCherryLike(log: Block, leaves: Block): TreeConfigurationBuilder {
        return TreeConfigurationBuilder(
            BlockStateProvider.simple(log),
            StraightTrunkPlacer(7, 0, 0),
            BlockStateProvider.simple(leaves),
            CandiedCherryFoliagePlacer(),
            TwoLayersFeatureSize(1, 0, 2, OptionalInt.empty())
        )
            .ignoreVines()
            .dirt(pudding)
    }

    fun createFancyOakLike(log: Block, leaves: Block): TreeConfigurationBuilder {
        return TreeConfigurationBuilder(
            BlockStateProvider.simple(log),
            FancyTrunkPlacer(3, 11, 0),
            BlockStateProvider.simple(leaves),
            FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
        ).ignoreVines()
            .dirt(pudding)
    }

    fun createDarkOakLike(log: Block, leaves: Block): TreeConfigurationBuilder {
        return TreeConfigurationBuilder(
            BlockStateProvider.simple(log),
            DarkOakTrunkPlacer(6, 2, 1),
            BlockStateProvider.simple(leaves),
            DarkOakFoliagePlacer(
                ConstantInt.of(0),
                ConstantInt.of(0)
            ),
            ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
        ).ignoreVines()
            .dirt(pudding)
    }

    fun createStraightBlobTree(
        logBlock: Block,
        leavesBlock: Block,
        baseHeight: Int,
        heightRandA: Int,
        heightRandB: Int,
        radius: Int,
    ): TreeConfigurationBuilder {
        return TreeConfigurationBuilder(
            BlockStateProvider.simple(logBlock),
            StraightTrunkPlacer(baseHeight, heightRandA, heightRandB),
            BlockStateProvider.simple(leavesBlock),
            BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3),
            TwoLayersFeatureSize(1, 0, 1)
        )
            .dirt(pudding)
    }

}