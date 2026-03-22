package cn.breadnicecat.candycraftce.core.misc

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items.AIR

object CSugarFuels {

    fun isFuel(item: Item): Boolean {
        return item !== AIR //todo
    }

    fun isFuel(stack: ItemStack): Boolean {
        return isFuel(stack.item)
    }

    fun getBurnDuration(item: Item?): Int {
        return if (item === AIR) 0 else 1//todo
    }

    fun getBurnDuration(stack: ItemStack): Int {
        return if (stack.isEmpty) 0 else 1 //todo
    }
}
