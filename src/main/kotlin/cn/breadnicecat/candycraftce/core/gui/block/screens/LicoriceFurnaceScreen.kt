package cn.breadnicecat.candycraftce.core.gui.block.screens


import cn.breadnicecat.candycraftce.core.block.blockentities.LicoriceFurnaceBE.Companion.LIT_TIME_DATA
import cn.breadnicecat.candycraftce.core.block.blockentities.LicoriceFurnaceBE.Companion.LIT_TIME_TOTAL_DATA
import cn.breadnicecat.candycraftce.core.block.blockentities.LicoriceFurnaceBE.Companion.TICKED_DATA
import cn.breadnicecat.candycraftce.core.block.blockentities.LicoriceFurnaceBE.Companion.TICKED_TOTAL_DATA
import cn.breadnicecat.candycraftce.core.gui.block.menus.LicoriceFurnaceMenu
import cn.breadnicecat.candycraftce.utils.CUtils.guiTex
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.MCTimeUnit.Companion.tick
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

/**
 * Created in 2024/2/3
 * Project: candycraftce
 * 
 * @author [Bread_NiceCat](https://github.com/Bread-NiceCat)
 * 
 * 
 */
class LicoriceFurnaceScreen(abstractContainerMenu: LicoriceFurnaceMenu, inventory: Inventory, component: Component) :
    AbstractContainerScreen<LicoriceFurnaceMenu>(abstractContainerMenu, inventory, component) {

    var guiStyle: ResourceLocation = LICORICE

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        guiGraphics.blit(guiStyle, leftPos, topPos, 0, 0, 176, 166)
        //燃料条
        val litTime: Int = menu.containerData.get(LIT_TIME_DATA)
        val litTimeTotal: Int = menu.containerData.get(LIT_TIME_TOTAL_DATA)
        if (litTime > 0) {
            val p = if (litTime > litTimeTotal) 14 else (14f * litTime / litTimeTotal).toInt() //渲染高度
            //反向渲染
            val k = 14 - p //未渲染高度
            guiGraphics.blit(guiStyle, leftPos + 57, topPos + 36 + k, 176, k, 14, p)
        }
        //进度条
        val ticked: Int = menu.containerData.get(TICKED_DATA)
        val tickedTotal: Int = menu.containerData.get(TICKED_TOTAL_DATA)
        if (ticked > 0) {
            val w = (22f * ticked / tickedTotal).toInt()
            guiGraphics.blit(guiStyle, leftPos + 80, topPos + 35, 176, 14, w, 16)
        }
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBackground(graphics)
        super.render(graphics, mouseX, mouseY, partialTick)
        renderTooltip(graphics, mouseX, mouseY)
    }

    override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
        val color = if (guiStyle === LICORICE) 0xd7d7d7 else 0x704338
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, color, false)
    }

    override fun renderTooltip(guiGraphics: GuiGraphics, x: Int, y: Int) {
        val litTime = menu.containerData.get(LIT_TIME_DATA)
        val ticked = menu.containerData.get(TICKED_DATA)
        val tickedTotal = menu.containerData.get(TICKED_TOTAL_DATA)
        super.renderTooltip(guiGraphics, x, y)
        if (x >= leftPos + 57 && x <= leftPos + 71 && y >= topPos + 37 && y <= topPos + 51) {
            guiGraphics.renderTooltip(
                font,
                Component.literal((((10f * litTime).toInt()).tick.toSecond / 10f).toString() + " s"),
                x,
                y
            )
        }
        if (tickedTotal != 0 && x >= leftPos + 80 && x <= leftPos + 102 && y >= topPos + 35 && y <= topPos + 51) {
            guiGraphics.renderTooltip(
                font,
                Component.literal(((1000f * ticked / tickedTotal).toInt() / 10f).toString() + "%"),
                x,
                y
            )
        }
    }

    companion object {
        val LICORICE = "gui_licorice_furnace".modLoc().guiTex()
    }

}
