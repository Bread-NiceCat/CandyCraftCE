package cn.breadnicecat.candycraftce.core.gui.block

import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.core.gui.block.menus.LicoriceFurnaceMenu
import cn.breadnicecat.candycraftce.core.gui.block.screens.LicoriceFurnaceScreen
import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.clog
import cn.breadnicecat.candycraftce.utils.CUtils.ifClient
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.CUtils.register
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.flag.FeatureFlagSet
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType

/**
 * Created by NiceCat on 2026/3/18.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object CBlockMenus {
    init {
        CUtils.sign()
    }


    val licorice_furnace_menu = register(CBlocks.licorice_furnace.id.path, ::LicoriceFurnaceMenu);

    //	public static final MenuEntry<ChocolateFurnaceMenu> CHOCOLATE_FURNACE_MENU = register(CHOCOLATE_FURNACE_BE.getName(), ChocolateFurnaceMenu::new);
//	public static final MenuEntry<SugarFactoryMenu> SUGAR_FACTORY_MENU = register(SUGAR_FACTORY_BE.getName(), SugarFactoryMenu::new);
//	public static final MenuEntry<AdvancedSugarFactoryMenu> ADVANCED_SUGAR_FACTORY_MENU = register(ADVANCED_SUGAR_FACTORY_BE.getName(), AdvancedSugarFactoryMenu::new);
    fun <M : AbstractContainerMenu> register(name: String, factory: MenuType.MenuSupplier<M>): MenuType<M> {
        val id = name.modLoc()
        CUtils.logRegister("Menu", id)
        val type = BuiltInRegistries.MENU.register(id, MenuType(factory, FeatureFlagSet.of()))
        return type
    }

    init {
        ifClient {
            clog.info("Binding Menus and Screens")
            MenuScreens.register(licorice_furnace_menu, ::LicoriceFurnaceScreen)
        }
    }
}