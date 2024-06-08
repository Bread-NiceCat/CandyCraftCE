package cn.breadnicecat.candycraftce.gui.block.menus;

import cn.breadnicecat.candycraftce.gui.block.CMenus;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ChocolateFurnaceMenu extends LicoriceFurnaceMenu {
	public ChocolateFurnaceMenu(int i, Inventory inventory) {
		super(i, inventory);
	}
	
	public ChocolateFurnaceMenu(int containerId, Inventory inventory, Container container, ContainerData containerData) {
		super(CMenus.CHOCOLATE_FURNACE_MENU.get(), containerId, inventory, container, containerData);
	}
}
