package cn.breadnicecat.candycraftce.gui.block;

import cn.breadnicecat.candycraftce.EngineFeatures;
import cn.breadnicecat.candycraftce.gui.block.menus.AdvancedSugarFactoryMenu;
import cn.breadnicecat.candycraftce.gui.block.menus.ChocolateFurnaceMenu;
import cn.breadnicecat.candycraftce.gui.block.menus.LicoriceFurnaceMenu;
import cn.breadnicecat.candycraftce.gui.block.menus.SugarFactoryMenu;
import cn.breadnicecat.candycraftce.gui.block.screens.AdvancedFactoryScreen;
import cn.breadnicecat.candycraftce.gui.block.screens.ChocolateFurnaceScreen;
import cn.breadnicecat.candycraftce.gui.block.screens.LicoriceFurnaceScreen;
import cn.breadnicecat.candycraftce.gui.block.screens.SugarFactoryScreen;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.slf4j.Logger;

import static cn.breadnicecat.candycraftce.CandyCraftCE.hookMinecraftSetup;
import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;
import static cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities.*;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

public class CMenus {
	private static final Logger LOGGER = CLogUtils.sign();

	static {
		if (isClient()) hookMinecraftSetup(CMenus::declareScreen);
	}

	public static final MenuEntry<LicoriceFurnaceMenu> LICORICE_FURNACE_MENU = register(LICORICE_FURNACE_BE.getName(), LicoriceFurnaceMenu::new);
	public static final MenuEntry<ChocolateFurnaceMenu> CHOCOLATE_FURNACE_MENU = register(CHOCOLATE_FURNACE_BE.getName(), ChocolateFurnaceMenu::new);
	public static final MenuEntry<SugarFactoryMenu> SUGAR_FACTORY_MENU = register(SUGAR_FACTORY_BE.getName(), SugarFactoryMenu::new);
	public static final MenuEntry<AdvancedSugarFactoryMenu> ADVANCED_SUGAR_FACTORY_MENU = register(ADVANCED_SUGAR_FACTORY_BE.getName(), AdvancedSugarFactoryMenu::new);

	public static void init() {
	}

	@Environment(EnvType.CLIENT)
	public static void declareScreen() {
		LOGGER.info("declareScreen");
		MenuScreens.register(LICORICE_FURNACE_MENU.get(), LicoriceFurnaceScreen::new);
		MenuScreens.register(CHOCOLATE_FURNACE_MENU.get(), ChocolateFurnaceScreen::new);
		MenuScreens.register(SUGAR_FACTORY_MENU.get(), SugarFactoryScreen::new);
		MenuScreens.register(ADVANCED_SUGAR_FACTORY_MENU.get(), AdvancedFactoryScreen::new);
	}

	public static <M extends AbstractContainerMenu> MenuEntry<M> register(String key, MenuType.MenuSupplier<M> factory) {
		return EngineFeatures.get().registerMenu(prefix(key), factory);
	}


}
