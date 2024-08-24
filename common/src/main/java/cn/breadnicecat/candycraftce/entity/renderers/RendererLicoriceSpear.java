package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.entity.entities.entity.LicoriceSpear;
import cn.breadnicecat.candycraftce.entity.models.ModelLicoriceSpear;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.entityTex;

/**
 * Created in 2024/7/2 21:34
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RendererLicoriceSpear extends EntityRenderer<LicoriceSpear> {
	public static ResourceLocation TEX = entityTex("licorice_spear");
	private final TridentModel model;
	
	public RendererLicoriceSpear(EntityRendererProvider.Context context) {
		super(context);
		model = new TridentModel(context.bakeLayer(ModelLicoriceSpear.MAIN));
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(LicoriceSpear entity) {
		return TEX;
	}
	
	@Override
	public void render(@NotNull LicoriceSpear entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90.0F));
		VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)), false, entity.getPickupItemStackOrigin().hasFoil());
		this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}
}
