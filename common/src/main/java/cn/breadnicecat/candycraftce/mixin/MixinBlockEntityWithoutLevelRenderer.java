package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.entity.models.ModelLicoriceSpear;
import cn.breadnicecat.candycraftce.entity.renderers.RendererLicoriceSpear;
import cn.breadnicecat.candycraftce.item.CItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created in 2024/7/3 下午12:11
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * 渲染三叉戟
 * <p>
 **/
@Mixin(BlockEntityWithoutLevelRenderer.class)
public abstract class MixinBlockEntityWithoutLevelRenderer {
	@Shadow
	@Final
	private EntityModelSet entityModelSet;
	@Unique
	private ModelLicoriceSpear candycraftce$spearModel;
	
	@Inject(method = "onResourceManagerReload", at = @At("HEAD"))
	private void onReload(ResourceManager resourceManager, CallbackInfo ci) {
		this.candycraftce$spearModel = new ModelLicoriceSpear(entityModelSet.bakeLayer(ModelLicoriceSpear.MAIN));
	}
	
	@Inject(method = "renderByItem", at = @At("HEAD"))
	private void renderByItem(@NotNull ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, CallbackInfo ci) {
		if (stack.is(CItems.LICORICE_SPEAR.get())) {
			poseStack.pushPose();
			poseStack.scale(1.0f, -1.0f, -1.0f);
			VertexConsumer vertexConsumer2 = ItemRenderer.getFoilBufferDirect(buffer, candycraftce$spearModel.renderType(RendererLicoriceSpear.TEX), false, stack.hasFoil());
			this.candycraftce$spearModel.renderToBuffer(poseStack, vertexConsumer2, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
			poseStack.popPose();
		}
	}
}
