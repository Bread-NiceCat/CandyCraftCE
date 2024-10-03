package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.entity.entities.monsters.CookieCreeper;
import cn.breadnicecat.candycraftce.entity.models.ModelCookieCreeper;
import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/10/3 01:47
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RendererCookieCreeper extends MobRenderer<CookieCreeper, ModelCookieCreeper> {
	public static final ResourceLocation TEX = ResourceUtils.entityTex("cookie_creeper");
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public RendererCookieCreeper(EntityRendererProvider.Context context) {
		super(context, new ModelCookieCreeper(context.bakeLayer(ModelCookieCreeper.MAIN)), 0.5f);
		this.addLayer((RenderLayer) new CreeperPowerLayer((RenderLayerParent) this, context.getModelSet()));
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(CookieCreeper entity) {
		return TEX;
	}
	
	protected void scale(CookieCreeper livingEntity, PoseStack poseStack, float partialTickTime) {
		float f = livingEntity.getSwelling(partialTickTime);
		float g = 1.0f + Mth.sin(f * 100.0f) * f * 0.01f;
		f = Mth.clamp(f, 0.0f, 1.0f);
		f *= f;
		f *= f;
		float h = (1.0f + f * 0.4f) * g;
		float i = (1.0f + f * 0.1f) / g;
		poseStack.scale(h, i, h);
	}
	
	protected float getWhiteOverlayProgress(CookieCreeper livingEntity, float partialTicks) {
		float f = livingEntity.getSwelling(partialTicks);
		if ((int) (f * 10.0f) % 2 == 0) {
			return 0.0f;
		}
		return Mth.clamp(f, 0.5f, 1.0f);
	}
	
	
}
