package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Created in 2024/7/8 上午1:10
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(ItemColors.class)
public abstract class MixinItemColors {
	@Inject(method = "createDefault",
			at = @At(value = "RETURN"),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private static void createDefault(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir, ItemColors itemColors) {
		CItems.registerItemColors(blockColors, itemColors);
	}
}
