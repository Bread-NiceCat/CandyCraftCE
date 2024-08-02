package cn.breadnicecat.candycraftce.mixin;

import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cn.breadnicecat.candycraftce.misc.CHaGens.*;

/**
 * Created in 2024/8/2 上午11:35
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(SplashManager.class)
public abstract class MixinSplashManager {
	@Unique
	private static final SplashRenderer candycraftce$DILUC = new SplashRenderer("Happy Birthday,Diluc Ragnvindr!");
	@Unique
	private static final SplashRenderer candycraftce$XIAO = new SplashRenderer("Happy Birthday,Xiao!");
	@Unique
	private static final SplashRenderer candycraftce$FURINA = new SplashRenderer("Happy Birthday,Furina de Fontaine!");
	@Unique
	private static final SplashRenderer candycraftce$PAIMON$ITTO = new SplashRenderer("Happy Birthday,Arataki Itto and Paimon!");
	@Unique
	private static final SplashRenderer candycraftce$NAHIDA = new SplashRenderer("Happy Birthday,Nahida!");
	@Unique
	private static final SplashRenderer candycraftce$KAZUHA = new SplashRenderer("Happy Birthday,Kaedehara Kazuha!");
	@Unique
	private static final SplashRenderer candycraftce$BREAD = new SplashRenderer("Happy Birthday,Bread_NiceCat!");
	@Unique
	private static final SplashRenderer candycraftce$TIGHNARI = new SplashRenderer("Happy Birthday,Tighnari!");
	
	@Inject(method = "getSplash", at = @At("HEAD"), cancellable = true)
	private void getSplash(CallbackInfoReturnable<SplashRenderer> cir) {
		if (xiao) {
			cir.setReturnValue(candycraftce$XIAO);
		}
		if (diluc) {
			cir.setReturnValue(candycraftce$DILUC);
		}
		if (furina) {
			cir.setReturnValue(candycraftce$FURINA);
		}
		if (paimon$itto) {
			cir.setReturnValue(candycraftce$PAIMON$ITTO);
		}
		if (nahida) {
			cir.setReturnValue(candycraftce$NAHIDA);
		}
		if (kazuha) {
			cir.setReturnValue(candycraftce$KAZUHA);
		}
		if (tighnari) {
			cir.setReturnValue(candycraftce$TIGHNARI);
		}
		if (bread) {
			cir.setReturnValue(candycraftce$BREAD);
		}
	}
}
