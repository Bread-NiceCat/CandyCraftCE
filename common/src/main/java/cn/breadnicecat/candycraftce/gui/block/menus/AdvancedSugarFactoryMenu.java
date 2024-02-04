package cn.breadnicecat.candycraftce.gui.block.menus;

import cn.breadnicecat.candycraftce.gui.block.CMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

/**
 * Created in 2024/2/5
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class AdvancedSugarFactoryMenu extends SugarFactoryMenu {
	protected AdvancedSugarFactoryMenu(MenuType<?> type, int id, Inventory inventory, Container container, ContainerData data) {
		super(type, id, inventory, container, data);
	}

	public AdvancedSugarFactoryMenu(int id, Inventory inventory, Container container, ContainerData data) {
		this(CMenus.ADVANCED_SUGAR_FACTORY_MENU.get(), id, inventory, container, data);
	}

	public AdvancedSugarFactoryMenu(int id, Inventory inv) {
		this(CMenus.ADVANCED_SUGAR_FACTORY_MENU.get(), id, inv, new SimpleContainer(2), new SimpleContainerData(3));
	}
}
