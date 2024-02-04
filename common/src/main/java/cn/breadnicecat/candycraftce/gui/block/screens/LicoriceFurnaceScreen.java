package cn.breadnicecat.candycraftce.gui.block.screens;

import cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE;
import cn.breadnicecat.candycraftce.gui.block.menus.LicoriceFurnaceMenu;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE.TICKED_DATA;
import static cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE.TICKED_TOTAL_DATA;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefixGUITex;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class LicoriceFurnaceScreen extends AbstractContainerScreen<LicoriceFurnaceMenu> {
	public static final ResourceLocation LICORICE = prefixGUITex("gui_licorice_furnace");
	protected ResourceLocation guiStyle;

	public LicoriceFurnaceScreen(LicoriceFurnaceMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
		guiStyle = LICORICE;
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		guiGraphics.blit(guiStyle, leftPos, topPos, 0, 0, 176, 166);
		//燃料条
		int litTime = menu.containerData.get(LicoriceFurnaceBE.LIT_TIME_DATA);
		int litTimeTotal = menu.containerData.get(LicoriceFurnaceBE.LIT_TIME_TOTAL_DATA);
		if (litTime > 0) {
			int p = litTime > litTimeTotal ? 14 : (int) (14f * litTime / litTimeTotal);//渲染高度
			//反向渲染
			int k = 14 - p;//未渲染高度
			guiGraphics.blit(guiStyle, leftPos + 57, topPos + 37 + k, 176, k, 14, p);
		}
		//进度条
		int ticked = menu.containerData.get(TICKED_DATA);
		int tickedTotal = menu.containerData.get(TICKED_TOTAL_DATA);
		if (ticked > 0) {
			int w = (int) (22f * ticked / tickedTotal);
			guiGraphics.blit(guiStyle, leftPos + 80, topPos + 35, 176, 14, w, 16);
		}
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		renderBackground(graphics);
		super.render(graphics, mouseX, mouseY, partialTick);
		renderTooltip(graphics, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xd7d7d7, false);
	}

	@Override
	protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
		int litTime = menu.containerData.get(LicoriceFurnaceBE.LIT_TIME_DATA);
		int ticked = menu.containerData.get(TICKED_DATA);
		int tickedTotal = menu.containerData.get(TICKED_TOTAL_DATA);
		super.renderTooltip(guiGraphics, x, y);
		if (x >= leftPos + 57 && x <= leftPos + 71 && y >= topPos + 37 && y <= topPos + 51) {
			guiGraphics.renderTooltip(font, Component.literal((int) (10f * litTime * TickUtils.TICK2SEC) / 10f + " s"), x, y);
		}
		if (tickedTotal != 0 && x >= leftPos + 80 && x <= leftPos + 102 && y >= topPos + 35 && y <= topPos + 51)
			guiGraphics.renderTooltip(font, Component.literal((int) (1000f * ticked / tickedTotal) / 10f + "%"), x, y);
	}
}
