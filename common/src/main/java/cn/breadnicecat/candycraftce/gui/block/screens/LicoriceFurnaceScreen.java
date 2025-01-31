package cn.breadnicecat.candycraftce.gui.block.screens;

import cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE;
import cn.breadnicecat.candycraftce.gui.block.menus.LicoriceFurnaceMenu;
import cn.breadnicecat.candycraftce.item.CSugarFuels;
import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE.TICKED_DATA;
import static cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE.TICKED_TOTAL_DATA;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.guiTex;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class LicoriceFurnaceScreen extends AbstractContainerScreen<LicoriceFurnaceMenu> {
	public static final String I18_BURN_TIME= ResourceUtils.i18Key("gui","burn_time");
	public static final ResourceLocation LICORICE = guiTex("gui_licorice_furnace");
	protected ResourceLocation guiStyle = LICORICE;
	
	public LicoriceFurnaceScreen(LicoriceFurnaceMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
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
			guiGraphics.blit(guiStyle, leftPos + 57, topPos + 36 + k, 176, k, 14, p);
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
		renderBackground(graphics, mouseX, mouseY, partialTick);
		super.render(graphics, mouseX, mouseY, partialTick);
		renderTooltip(graphics, mouseX, mouseY);
	}
	
	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		int color = guiStyle == LICORICE ? 0xd7d7d7 : 0x704338;
		guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, color, false);
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
//			guiGraphics.renderTooltip(font, Component.literal(ticked+"/"+tickedTotal),x,y);
			guiGraphics.renderTooltip(font, Component.literal((int) (1000f * ticked / tickedTotal) / 10f + "%"), x, y);
	}
	
	@Override
	protected @NotNull List<Component> getTooltipFromContainerItem(ItemStack stack) {
		List<Component> list = super.getTooltipFromContainerItem(stack);
		if (CSugarFuels.isFuel(stack)) {
			list.add(Component.translatable(I18_BURN_TIME,
					Float.toString(CSugarFuels.getBurnDuration(stack)*TickUtils.TICK2SEC)));
		}
		return list;
	}
}
