package cn.breadnicecat.candycraftce.gui.block.menus.slot;

import cn.breadnicecat.candycraftce.misc.CSugarFuels;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/24 12:52
 */
public class SugarFuelSlot<T extends Container> extends Slot {


	public SugarFuelSlot(T pContainer, int pSlot, int pX, int pY) {
		super(pContainer, pSlot, pX, pY);
	}

	@Override
	public boolean mayPlace(@NotNull ItemStack pStack) {
		return CSugarFuels.isFuel(pStack);
	}
}
