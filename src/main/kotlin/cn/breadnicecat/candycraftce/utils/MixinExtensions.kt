package cn.breadnicecat.candycraftce.utils

import cn.breadnicecat.candycraftce.mixin.data.AccessorBlockFamilyProvider
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.world.level.block.Block

/**
 * Created by NiceCat on 2026/1/17.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
@Suppress("UnstableApiUsage")
object MixinExtensions {
    fun BlockModelGenerators.BlockFamilyProvider.accessor() = this as AccessorBlockFamilyProvider

    var strippables: Map<Block, Block>
        get() = AxeItemAccessor.getStrippedBlocks()
        set(value) = AxeItemAccessor.setStrippedBlocks(value)
}


object MixinInterfaces {
}