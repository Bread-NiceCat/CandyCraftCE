package cn.breadnicecat.candycraftce.gui.block.fabric;

import cn.breadnicecat.candycraftce.gui.block.MenuEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class CMenusImpl {
	public static <M extends AbstractContainerMenu> MenuEntry<M> _register(ResourceLocation key, MenuType.MenuSupplier<M> factory) {
		MenuType<M> type = Registry.register(BuiltInRegistries.MENU, key, new MenuType<>(factory, FeatureFlagSet.of()));
		return new MenuEntry<>(key) {
			@Override
			public MenuType<M> get() {
				return type;
			}
		};
	}
}
