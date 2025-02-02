package cn.breadnicecat.candycraftce.entity.layers;

import cn.breadnicecat.candycraftce.entity.entities.mobs.Bunny;
import cn.breadnicecat.candycraftce.entity.models.ModelBunny;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.entityTex;

/**
 * Created in 2024/8/5 上午9:10
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class LayerBunnyBody extends RenderLayer<Bunny, ModelBunny> {
	private static final ResourceLocation TEX = entityTex("bunny/body");
	
	public LayerBunnyBody(RenderLayerParent<Bunny, ModelBunny> renderer) {
		super(renderer);
	}
	
	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Bunny bunny, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		ModelBunny m = getParentModel();
		m.setupAnim(bunny, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		int color = bunny.getRGB();
		m.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucent(TEX)), packedLight, LivingEntityRenderer.getOverlayCoords(bunny, 0.0F), color);
	}
}
