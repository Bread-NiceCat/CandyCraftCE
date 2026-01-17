package cn.breadnicecat.candycraftce.utils.mixin

import cn.breadnicecat.candycraftce.mixin.data.AccessorBlockFamilyProvider
import net.minecraft.data.models.BlockModelGenerators

/**
 * Created by NiceCat on 2026/1/17.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object MixinExtensions {
    fun BlockModelGenerators.BlockFamilyProvider.accessor() = this as AccessorBlockFamilyProvider
}