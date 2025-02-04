package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.entity.entities.mobs.Bunny;
import cn.breadnicecat.candycraftce.entity.models.ModelBunny;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.entityTex;

/**
 * Created in 2024/8/5 上午8:29
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RendererBunny extends MobRenderer<Bunny, ModelBunny> {
	private static final ResourceLocation TEX = entityTex("bunny/eye");
	
	public RendererBunny(EntityRendererProvider.Context context) {
		super(context, new ModelBunny(context), 0.3f);
		addLayer(new LayerBunnyBody(this));
	}
	
	@Override
	public void render(Bunny entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(Bunny entity) {
		return TEX;
	}
	
	/**
	 * Created in 2024/8/5 上午9:10
	 * Project: candycraftce
	 *
	 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
	 * <p>
	 *
	 * <p>
	 **/
	public static class LayerBunnyBody extends RenderLayer<Bunny, ModelBunny> {
		private static final ResourceLocation TEX = entityTex("bunny/body");
		
		public LayerBunnyBody(RenderLayerParent<Bunny, ModelBunny> renderer) {
			super(renderer);
		}
		
		@Override
		public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Bunny bunny, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
			ModelBunny m = getParentModel();
			m.setupAnim(bunny, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			int color = bunny.getColor();
			m.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucent(TEX)), packedLight, getOverlayCoords(bunny, 0.0F), color);
		}
	}
}
