package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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
	
	@ModifyVariable(method = "getProjectile", at = @At("RETURN"), argsOnly = true)
	public ItemStack getProjectile(ItemStack weaponStack) {
		if (weaponStack.is(CItems.CARAMEL_BOW.get())) {
			return CItems.HONEYCOMB_ARROW.getDefaultInstance();
		} else {
			return weaponStack;
		}
	}
}
