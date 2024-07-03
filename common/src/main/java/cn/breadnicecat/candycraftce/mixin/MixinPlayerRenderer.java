package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.entity.layers.LayerCaramelArrow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created in 2024/7/3 上午12:34
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer {
	
	@Inject(method = "<init>", at = @At("TAIL"))
	public void init(EntityRendererProvider.Context context, boolean useSlimModel, CallbackInfo ci) {
		PlayerRenderer self = (PlayerRenderer) (Object) this;
		self.addLayer(new LayerCaramelArrow(context, self));
	}
}







