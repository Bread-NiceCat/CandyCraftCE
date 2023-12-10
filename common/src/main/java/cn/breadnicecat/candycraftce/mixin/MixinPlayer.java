package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.registration.item.CItems;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created in 2023/12/10 9:18
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
@Mixin(Player.class)
public abstract class MixinPlayer {
	@Inject(method = "turtleHelmetTick", at = @At("HEAD"))
	protected void tickHelmet(CallbackInfo ci) {
		Player player = (Player) (Object) this;
		ItemStack itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
		if (itemStack.is(CItems.WATER_MASK.getItem()) && player.isEyeInFluid(FluidTags.WATER)) {
			player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, false, false, false));
			player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0, false, false, false));
		}
	}
}
