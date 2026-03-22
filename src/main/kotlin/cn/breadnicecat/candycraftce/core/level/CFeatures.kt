package cn.breadnicecat.candycraftce.core.level

import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.data.extension.level.ConfiguredFeatureDataScope.Companion.data
import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature

/**
 * Created by NiceCat on 2026/1/9.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object CFeatures {
    init {
        CUtils.sign()
    }

    val chocolate_tree = configured("chocolate_tree")
        .data {
            register(
                Feature.TREE,
                createOakLike(
                    CBlocks.marshmallow_log.block,
                    CBlocks.chocolate_leaves.block
                ).build()
            )
        }

    val chocolate_fancy_tree = configured("chocolate_fancy_tree")
        .data {
            register(
                Feature.TREE,
                createFancyOakLike(
                    CBlocks.marshmallow_log.block,
                    CBlocks.chocolate_leaves.block
                ).build()
            )
        }
    val white_chocolate_tree = configured("white_chocolate_tree")
        .data {
            register(
                Feature.TREE,
                createOakLike(
                    CBlocks.light_marshmallow_log.block,
                    CBlocks.white_chocolate_leaves.block
                ).build()
            )
        }

    val white_chocolate_fancy_tree = configured("white_chocolate_fancy_tree")
        .data {
            register(
                Feature.TREE,
                createSuperBirchLike(
                    CBlocks.light_marshmallow_log.block,
                    CBlocks.white_chocolate_leaves.block
                ).build()
            )
        }
    val caramel_tree = configured("caramel_tree")
        .data {
            register(
                Feature.TREE,
                createOakLike(
                    CBlocks.dark_marshmallow_log.block,
                    CBlocks.caramel_leaves.block
                ).build()
            )
        }
    val caramel_fancy_tree = configured("caramel_fancy_tree")
        .data {
            register(
                Feature.TREE,
                createFancyCaramelLike(
                    CBlocks.dark_marshmallow_log.block,
                    CBlocks.caramel_leaves.block
                ).build()
            )
        }
    val magical_tree = configured("magical_tree")
        .data {
            register(
                Feature.TREE,
                createDarkOakLike(
                    CBlocks.dark_marshmallow_log.block,
                    CBlocks.magical_leaves.block
                ).build()
            )
        }
    val candied_cherry_tree = configured("candied_cherry_tree")
        .data {
            register(
                Feature.TREE,
                createCandiedCherryLike(
                    CBlocks.marshmallow_log.block,
                    CBlocks.candied_cherry_leaves.block
                ).build()
            )
        }

    private fun configured(name: String): ResourceKey<ConfiguredFeature<*, *>> {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, name.modLoc())
    }
}