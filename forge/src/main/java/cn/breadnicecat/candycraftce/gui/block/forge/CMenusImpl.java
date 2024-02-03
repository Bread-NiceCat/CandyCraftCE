package cn.breadnicecat.candycraftce.gui.block.forge;

import cn.breadnicecat.candycraftce.forge.CandyCraftCEImpl;
import cn.breadnicecat.candycraftce.gui.block.MenuEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;

public class CMenusImpl {
	public static final DeferredRegister<MenuType<?>> REGISTER = CandyCraftCEImpl.createRegister(ForgeRegistries.MENU_TYPES);

	public static <M extends AbstractContainerMenu> MenuEntry<M> _register(ResourceLocation id, MenuType.MenuSupplier<M> factory) {
		var object = REGISTER.register(id.getPath(), () -> new MenuType<>(factory, FeatureFlagSet.of()));
		assertTrue(id.equals(object.getId()), "Unmatched ResourceLocation");
		return new MenuEntry<>(id) {
			@Override
			public MenuType<M> get() {
				return object.get();
			}
		};
	}
}
