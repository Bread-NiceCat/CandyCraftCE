package cn.breadnicecat.candycraftce.gui.block;

import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public abstract class MenuEntry<M extends AbstractContainerMenu> extends RegistryEntry implements Supplier<MenuType<M>> {
	public MenuEntry(ResourceLocation id) {
		super(id);
	}

}
