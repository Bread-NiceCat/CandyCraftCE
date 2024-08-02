package cn.breadnicecat.candycraftce.gui.block.screens;

import cn.breadnicecat.candycraftce.gui.block.menus.LicoriceFurnaceMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.guiTex;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ChocolateFurnaceScreen extends LicoriceFurnaceScreen {
	public static final ResourceLocation CHOCOLATE = guiTex("gui_chocolate_furnace");
	
	public ChocolateFurnaceScreen(LicoriceFurnaceMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
		guiStyle = CHOCOLATE;
	}
	
}
