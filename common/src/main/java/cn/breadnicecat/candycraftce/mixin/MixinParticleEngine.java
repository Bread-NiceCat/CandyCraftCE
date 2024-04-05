package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.particle.CParticles;
import net.minecraft.client.particle.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created in 2024/4/5 下午5:59
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
@Mixin(ParticleEngine.class)
public abstract class MixinParticleEngine {
	@Inject(method = "registerProviders", at = @At("TAIL"))
	private void registerProviders(CallbackInfo ci) {
		CParticles.registerProviders((ParticleEngine) (Object) this);
	}
}