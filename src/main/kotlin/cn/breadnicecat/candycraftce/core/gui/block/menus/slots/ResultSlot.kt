package cn.breadnicecat.candycraftce.core.gui.block.menus.slots

import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

/**
 * 原版[net.minecraft.world.inventory.ResultSlot]过于繁琐，这里写一个简单化的版本
 *
 * @author [Bread_NiceCat](https://gitee.com/Bread_NiceCat)
 * @date 2023/1/23 17:05
 */
open class ResultSlot(pContainer: Container, pSlot: Int, pX: Int, pY: Int) : Slot(pContainer, pSlot, pX, pY) {
    override fun mayPlace(pStack: ItemStack): Boolean {
        return false
    }
}
