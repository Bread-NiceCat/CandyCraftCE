package cn.breadnicecat.candycraftce.gui.block;

import cn.breadnicecat.candycraftce.gui.block.menus.LicoriceFurnaceMenu;
import cn.breadnicecat.candycraftce.gui.block.screens.LicoriceFurnaceScreen;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.slf4j.Logger;

import static cn.breadnicecat.candycraftce.CandyCraftCE.hookMinecraftSetup;
import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;
import static cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities.LICORICE_FURNACE_BE;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

public class CMenus {
	private static final Logger LOGGER = CLogUtils.sign();

	static {
		if (isClient()) hookMinecraftSetup(CMenus::declareScreen);
	}

	public static final MenuEntry<LicoriceFurnaceMenu> LICORICE_FURNACE_MENU = register(LICORICE_FURNACE_BE.getName(), LicoriceFurnaceMenu::new);

	public static void init() {
		CommonUtils.logInit(LOGGER);
	}

	@Environment(EnvType.CLIENT)
	public static void declareScreen() {
		LOGGER.info("declareScreen");
		MenuScreens.register(LICORICE_FURNACE_MENU.get(), LicoriceFurnaceScreen::new);
	}

	public static <M extends AbstractContainerMenu> MenuEntry<M> register(String key, MenuType.MenuSupplier<M> factory) {
		return _register(prefix(key), factory);
	}

	@ExpectPlatform
	@Deprecated
	private static <M extends AbstractContainerMenu> MenuEntry<M> _register(ResourceLocation key, MenuType.MenuSupplier<M> factory) {
		throw new AssertionError();
	}

}
