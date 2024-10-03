package cn.breadnicecat.candycraftce.gui.block;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class MenuEntry<M extends AbstractContainerMenu> extends SimpleEntry<MenuType<?>, MenuType<M>> {
	
	public MenuEntry(SimpleEntry<MenuType<?>, MenuType<M>> wrapper) {
		super(wrapper);
	}
	
}
