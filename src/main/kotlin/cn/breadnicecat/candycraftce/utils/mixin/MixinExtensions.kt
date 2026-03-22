package cn.breadnicecat.candycraftce.utils.mixin

import cn.breadnicecat.candycraftce.mixin.core.AccessorAxeItem
import cn.breadnicecat.candycraftce.mixin.data.AccessorBlockFamilyProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.world.level.block.Block

/**
 * Created by NiceCat on 2026/1/17.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object MixinExtensions {
    fun BlockModelGenerators.BlockFamilyProvider.accessor() = this as AccessorBlockFamilyProvider

    var strippables: Map<Block, Block>
        get() = AccessorAxeItem.getSTRIPPABLES()
        set(value) = AccessorAxeItem.setSTRIPPABLES(value)

}