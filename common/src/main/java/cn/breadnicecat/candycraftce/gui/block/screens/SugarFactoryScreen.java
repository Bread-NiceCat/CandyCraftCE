package cn.breadnicecat.candycraftce.gui.block.screens;

import cn.breadnicecat.candycraftce.gui.block.menus.SugarFactoryMenu;
import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cn.breadnicecat.candycraftce.block.blockentity.entities.SugarFactoryBE.*;

/**
 * Created in 2024/2/4
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFactoryScreen extends AbstractContainerScreen<SugarFactoryMenu> {
	public static final ResourceLocation COMMON_GUI = ResourceUtils.prefixGUITex("gui_sugar_factory");
	protected ResourceLocation style = COMMON_GUI;
	protected int titleColor = 0xb5ff71;

	public SugarFactoryScreen(SugarFactoryMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
	}

	@Override
	protected void init() {
		super.init();
		topPos += 51;
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(style, leftPos, topPos, 0, 0, 174, 114);
		int ticked = menu.data.get(TICKED_DATA_SLOT);
		int tickedTotal = menu.data.get(TICKED_TOTAL_DATA_SLOT);
		if (ticked != 0) renderProcessBar(graphics, menu.data.get(RECIPE_TYPE_DATA_SLOT), (float) ticked / tickedTotal);
	}

	protected void renderProcessBar(GuiGraphics graphics, int recipeType, float progress) {
		switch (recipeType) {
			case COMMON_TYPE -> renderProcessBar(graphics, COMMON_GUI, 0, 114, progress);
			case SUGARY_TYPE -> renderProcessBar(graphics, COMMON_GUI, 0, 126, progress);
		}
	}

	protected void renderProcessBar(GuiGraphics graphics, ResourceLocation style, int u, int v, float progress) {
		graphics.blit(style, leftPos + 26 + 1, topPos + 8 + 1, u, v, (int) (120 * progress), 12);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(font, title, titleLabelX, titleLabelY + 36, titleColor);
	}
}
