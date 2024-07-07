package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.block.CBlocks;
import net.minecraft.client.color.block.BlockColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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
			at = @At(value = "RETURN"),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private static void createDefault(CallbackInfoReturnable<BlockColors> cir, BlockColors blockColors) {
		CBlocks.registerBlockColors(blockColors);
	}
}
