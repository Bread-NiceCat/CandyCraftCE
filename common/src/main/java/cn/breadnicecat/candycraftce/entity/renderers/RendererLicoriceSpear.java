package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.entity.models.ModelLicoriceSpear;
import cn.breadnicecat.candycraftce.mixin.ThrownTridentRendererAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefixEntityTex;

/**
 * Created in 2024/7/2 21:34
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RendererLicoriceSpear extends ThrownTridentRenderer {
	public static ResourceLocation TEX = prefixEntityTex("licorice_spear");
	
	public RendererLicoriceSpear(EntityRendererProvider.Context context) {
		super(context);
		((ThrownTridentRendererAccessor) this).setModel(new TridentModel(context.bakeLayer(ModelLicoriceSpear.MAIN)));
	}
	
	@Override
	public void render(ThrownTrident entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(ThrownTrident entity) {
		return TEX;
	}
}
