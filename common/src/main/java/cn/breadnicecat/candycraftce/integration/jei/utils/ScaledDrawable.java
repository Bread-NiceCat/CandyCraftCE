package cn.breadnicecat.candycraftce.integration.jei.utils;

import com.google.common.base.Preconditions;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;

import static java.lang.String.format;

public class ScaledDrawable implements IDrawable {
	private final IDrawable drawable;
	private final int division;
	
	public ScaledDrawable(IDrawable drawable, int division) {
		int width = drawable.getWidth();
		int height = drawable.getHeight();
		Preconditions.checkArgument(width % division == 0, format("drawable width %s must be divisible by the division %s", width, division));
		Preconditions.checkArgument(height % division == 0, format("drawable height %s must be divisible by the division %s", height, division));
		this.drawable = drawable;
		this.division = division;
	}
	
	@Override
	public int getWidth() {
		return drawable.getWidth() / division;
	}
	
	@Override
	public int getHeight() {
		return drawable.getHeight() / division;
	}
	
	@Override
	public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
		var poseStack = guiGraphics.pose();
		poseStack.pushPose();
		{
			poseStack.translate(xOffset, yOffset, 0);
			poseStack.scale(1 / (float) division, 1 / (float) division, 1);
			this.drawable.draw(guiGraphics);
		}
		poseStack.popPose();
	}
	
	@Override
	public void draw(GuiGraphics guiGraphics) {
		var poseStack = guiGraphics.pose();
		poseStack.pushPose();
		{
			poseStack.scale(1 / (float) division, 1 / (float) division, 1);
			this.drawable.draw(guiGraphics);
		}
		poseStack.popPose();
	}
}