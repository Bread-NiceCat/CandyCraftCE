package cn.breadnicecat.candycraftce.core.gui.block.menus.slots

import cn.breadnicecat.candycraftce.core.misc.CSugarFuels
import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

/**
 * @author [Bread_NiceCat](https://gitee.com/Bread_NiceCat)
 * @date 2023/1/24 12:52
 */
class SugarFuelSlot<T : Container>(pContainer: T, pSlot: Int, pX: Int, pY: Int) : Slot(pContainer, pSlot, pX, pY) {
    override fun mayPlace(pStack: ItemStack): Boolean {
        return CSugarFuels.isFuel(pStack)
    }
}
