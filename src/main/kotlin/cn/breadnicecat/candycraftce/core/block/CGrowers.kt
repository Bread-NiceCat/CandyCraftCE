package cn.breadnicecat.candycraftce.core.block

import cn.breadnicecat.candycraftce.core.level.CFeatures
import net.minecraft.resources.ResourceKey
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature

/**
 * Created by NiceCat on 2026/1/9.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 * 给树苗使用
 */
object CGrowers {
    val chocolate_grower: AbstractTreeGrower = object : AbstractTreeGrower() {
        override fun getConfiguredFeature(
            random: RandomSource,
            hasFlowers: Boolean,
        ): ResourceKey<ConfiguredFeature<*, *>> {
            return if (random.nextFloat() < 0.1) CFeatures.chocolate_fancy_tree else CFeatures.chocolate_tree
        }
    }
    val white_chocolate_grower: AbstractTreeGrower = object : AbstractTreeGrower() {
        override fun getConfiguredFeature(
            random: RandomSource,
            hasFlowers: Boolean,
        ): ResourceKey<ConfiguredFeature<*, *>> {
            return if (random.nextFloat() < 0.1) CFeatures.white_chocolate_fancy_tree else CFeatures.white_chocolate_tree
        }
    }
    val caramel_grower: AbstractTreeGrower = object : AbstractTreeGrower() {
        override fun getConfiguredFeature(
            random: RandomSource,
            hasFlowers: Boolean,
        ): ResourceKey<ConfiguredFeature<*, *>> {
            return CFeatures.caramel_tree
        }
    }
    val candied_cherry_grower: AbstractTreeGrower = object : AbstractTreeGrower() {
        override fun getConfiguredFeature(
            random: RandomSource,
            hasFlowers: Boolean,
        ): ResourceKey<ConfiguredFeature<*, *>> {
            return CFeatures.candied_cherry_tree
        }

    }
}