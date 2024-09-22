package cn.breadnicecat.candycraftce.mixin.fabric;

import cn.breadnicecat.candycraftce.item.CItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
			at = @At(value = "RETURN")
	)
	private static void createDefault(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir, @Local ItemColors itemColors) {
		CItems._registerItemColors(blockColors, itemColors);
	}
}
