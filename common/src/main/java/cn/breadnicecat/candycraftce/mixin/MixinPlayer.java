package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created in 2024/7/3 上午2:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(Player.class)
public abstract class MixinPlayer {
	
	@Shadow
	@Final
	private Abilities abilities;
	
	@Inject(method = "getProjectile", at = @At("HEAD"), cancellable = true)
	public void getProjectile(ItemStack weaponStack, CallbackInfoReturnable<ItemStack> cir) {
		if (abilities.instabuild) {
			if (weaponStack.is(CItems.CARAMEL_BOW.get())) {
				cir.setReturnValue(CItems.HONEYCOMB_ARROW.getDefaultInstance());
			}
		}
	}
}
