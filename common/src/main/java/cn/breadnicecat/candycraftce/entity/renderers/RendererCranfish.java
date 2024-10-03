package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.entity.entities.mobs.Cranfish;
import cn.breadnicecat.candycraftce.entity.models.ModelCranfish;
import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/8/3 下午8:38
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RendererCranfish extends MobRenderer<Cranfish, ModelCranfish> {
	public static ResourceLocation TEX = ResourceUtils.entityTex("cranfish");
	
	public RendererCranfish(EntityRendererProvider.Context context) {
		super(context, new ModelCranfish(context), 0.15f);
	}
	
	@Override
	protected void setupRotations(Cranfish entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
		super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
		//extra Fish rot
		float f = 4.3f * Mth.sin(0.6f * bob);
		poseStack.mulPose(Axis.YP.rotationDegrees(f));
		if (!entity.isInWater()) {
			poseStack.translate(0.1f, 0.1f, -0.1f);
			poseStack.mulPose(Axis.ZP.rotationDegrees(90.0f));
		}
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(Cranfish entity) {
		return TEX;
	}
}
