//package cn.breadnicecat.candycraftce.utils;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import com.mojang.math.Matrix3f;
//import com.mojang.math.Matrix4f;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.Font;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
///**
// * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
// * @date 2023/1/26 20:25
// */
//@OnlyIn(Dist.CLIENT)
//public class RenderUtils {
//	/**
//	 * @param location 从texture目录开始
//	 */
//	public static TextureAtlasSprite getTexAtlasOrThrow(ResourceLocation atlas, ResourceLocation location) {
//		TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(atlas).apply(location);
//		if (sprite instanceof MissingTextureAtlasSprite) {
//			throw new IllegalStateException("missing texture-atlas of " + location);
//		}
//		return sprite;
//	}
//
//	public static void drawFont(PoseStack stack, String text, float x, float y, int color) {
//		drawFont(stack, Minecraft.getInstance().font, text, x, y, color);
//	}
//
//	public static void drawFont(PoseStack stack, Font font, String text, float x, float y, int color) {
//		font.draw(stack, text, x, y, color);
//	}
//
//	public static void draw(PoseStack stack, TextureAtlasSprite tex, RenderType type, int startX, int startY, int px) {
//		MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
//		VertexConsumer buffer = immediate.getBuffer(type);
//		Matrix3f normal = stack.last().normal();
//		Matrix4f pose = stack.last().pose();
//		int stopX = startX + px;
//		int stopY = startY + px;
//		buffer.vertex(pose, startX, startY, 0).color(1f, 1f, 1f, 1f).uv(tex.getU0(), tex.getV0()).uv2(0xF000F0).normal(normal, 1, 0, 0).endVertex();
//		buffer.vertex(pose, startX, stopY, 0).color(1f, 1f, 1f, 1f).uv(tex.getU0(), tex.getV1()).uv2(0xF000F0).normal(normal, 1, 0, 0).endVertex();
//		buffer.vertex(pose, stopX, stopY, 0).color(1f, 1f, 1f, 1f).uv(tex.getU1(), tex.getV1()).uv2(0xF000F0).normal(normal, 1, 0, 0).endVertex();
//		buffer.vertex(pose, stopX, startY, 0).color(1f, 1f, 1f, 1f).uv(tex.getU1(), tex.getV0()).uv2(0xF000F0).normal(normal, 1, 0, 0).endVertex();
//		immediate.endBatch();
//	}
//
//}
