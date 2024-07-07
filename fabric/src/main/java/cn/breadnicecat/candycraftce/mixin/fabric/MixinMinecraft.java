package cn.breadnicecat.candycraftce.mixin.fabric;

import cn.breadnicecat.candycraftce.block.PuddingColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created in 2024/7/7 下午10:17
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
	@Shadow
	@Final
	private ReloadableResourceManager resourceManager;
	
	@Inject(method = "<init>",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/ResourceLoadStateTracker;startReload(Lnet/minecraft/client/ResourceLoadStateTracker$ReloadReason;Ljava/util/List;)V",
					shift = At.Shift.BEFORE)
	)
	private void onReload(GameConfig gameConfig, CallbackInfo ci) {
		resourceManager.registerReloadListener(PuddingColor.RELOAD_LISTENER);
	}
}
