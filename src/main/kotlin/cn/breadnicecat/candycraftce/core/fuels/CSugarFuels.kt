package cn.breadnicecat.candycraftce.core.fuels

import net.minecraft.world.item.ItemStack

class CSugarFuels {
    companion object {
        fun isFuel(stack: ItemStack): Boolean {
            return getBurnDuration(stack) > 0
        }

        fun getBurnDuration(stack: ItemStack): Int {
            return if (stack.isEmpty) 0 else 20
        }
    }
}