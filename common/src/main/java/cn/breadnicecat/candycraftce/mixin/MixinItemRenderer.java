package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.item.CItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.breadnicecat.candycraftce.mixin_ref.$ItemRenderer.ItemRenderer$SPEAR_IN_HAND_MODEL;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.orElse;
import static net.minecraft.world.item.ItemDisplayContext.*;

/**
 * Created in 2024/7/3 下午2:25
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
	@Shadow
	@Final
	private ItemModelShaper itemModelShaper;
	@Shadow
	@Final
	private BlockEntityWithoutLevelRenderer blockEntityRenderer;
	@Unique
	private BakedModel candycraftce$handSpearModel;
	
	@Inject(method = "onResourceManagerReload", at = @At("HEAD"))
	public void onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci) {
		candycraftce$handSpearModel = itemModelShaper.getModelManager().getModel(ItemRenderer$SPEAR_IN_HAND_MODEL);
	}
	
	@Inject(method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V",
			at = @At(value = "HEAD"),
			cancellable = true
	)
	void renderStatic(LivingEntity entity, @NotNull ItemStack itemStack, ItemDisplayContext context, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, Level level, int combinedLight, int combinedOverlay, int seed, CallbackInfo ci) {
		if (itemStack.is(CItems.LICORICE_SPEAR.get())) {
			if (context != GUI && context != GROUND && context != FIXED) {
				//不是以物品状态呈现
				poseStack.pushPose();
				BakedModel model = orElse(candycraftce$handSpearModel.getOverrides().resolve(candycraftce$handSpearModel, itemStack, (ClientLevel) level, entity, seed),
						this.itemModelShaper.getModelManager()::getMissingModel);
				model.getTransforms().getTransform(context).apply(leftHand, poseStack);
				blockEntityRenderer.renderByItem(itemStack, context, poseStack, buffer, combinedLight, combinedOverlay);
				poseStack.popPose();
				ci.cancel();
			}
		}
	}
}
