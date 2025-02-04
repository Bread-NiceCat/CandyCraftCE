package cn.breadnicecat.candycraftce.mixin.fabric;

import cn.breadnicecat.candycraftce.misc.PuddingColor;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.color.block.BlockColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created in 2024/7/6 下午7:31
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(BlockColors.class)
public abstract class MixinBlockColors {
	@Inject(method = "createDefault",
			at = @At(value = "RETURN")
	)
	private static void createDefault(CallbackInfoReturnable<BlockColors> cir, @Local BlockColors blockColors) {
		PuddingColor._registerBlockColors(blockColors);
	}
}
