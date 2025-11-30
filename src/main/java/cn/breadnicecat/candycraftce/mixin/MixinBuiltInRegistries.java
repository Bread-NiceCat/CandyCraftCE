package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltInRegistries.class)
public abstract class MixinBuiltInRegistries {
	@Inject(at = @At("TAIL"), method = "freeze")
	private static void init(CallbackInfo info) {
		CandyCraftCE.onPostInitialize();
	}
}