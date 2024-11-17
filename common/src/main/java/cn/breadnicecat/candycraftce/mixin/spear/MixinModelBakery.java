package cn.breadnicecat.candycraftce.mixin.spear;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static cn.breadnicecat.candycraftce.misc.mixin_ref.ItemRenderer.SPEAR_IN_HAND_MODEL;

/**
 * Created in 2024/7/3 下午4:00
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(ModelBakery.class)
public abstract class MixinModelBakery {
	@Shadow
	protected abstract void loadSpecialItemModelAndDependencies(ModelResourceLocation modelLocation);
	
	@SuppressWarnings("rawtypes")
	@Inject(method = "<init>", at = @At(value = "TAIL"))
	void init(BlockColors blockColors, ProfilerFiller profilerFiller, Map modelResources, Map blockStateResources, CallbackInfo ci) {
		loadSpecialItemModelAndDependencies(SPEAR_IN_HAND_MODEL);
	}
}
