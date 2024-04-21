package cn.breadnicecat.candycraftce.gui.block;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class MenuEntry<M extends AbstractContainerMenu> extends SimpleEntry<MenuType<?>, MenuType<M>> {
	public MenuEntry(ResourceKey<MenuType<?>> key, Supplier<MenuType<M>> getter) {
		super(key, getter);
	}

	public MenuEntry(Pair<ResourceKey<MenuType<?>>, Supplier<MenuType<M>>> wrapper) {
		super(wrapper);
	}

}
