package cn.breadnicecat.candycraftce.gui.block.screens;

import cn.breadnicecat.candycraftce.gui.block.menus.SugarFactoryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cn.breadnicecat.candycraftce.block.blockentity.entities.AdvancedFactoryBE.ADVANCED_TYPE;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefixGUITex;

/**
 * Created in 2024/2/5
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class AdvancedFactoryScreen extends SugarFactoryScreen {
	
	public static final ResourceLocation ADVANCED_STYLE = prefixGUITex("gui_advanced_sugar_factory");
	
	public AdvancedFactoryScreen(SugarFactoryMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
		style = ADVANCED_STYLE;
		titleColor = 0xb5ff71;
	}
	
	@Override
	protected void renderProcessBar(GuiGraphics graphics, int recipeType, float progress) {
		if (recipeType == ADVANCED_TYPE) {
			renderProcessBar(graphics, ADVANCED_STYLE, 0, 114, progress);
		} else super.renderProcessBar(graphics, recipeType, progress);
	}
}
