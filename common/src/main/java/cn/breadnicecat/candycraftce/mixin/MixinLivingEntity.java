package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.registration.item.CItems;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created in 2023/11/26 8:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot slot);

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	protected void hurt(@NotNull DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		//果冻靴子免摔落伤
		if (source.is(DamageTypes.FALL) && getItemBySlot(EquipmentSlot.FEET).is(CItems.TRAMPOJELLY_BOOTS.getItem())) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "tick", at = @At("HEAD"))
	protected void tickHelmet(CallbackInfo ci) {
		LivingEntity ent = (LivingEntity) (Object) this;
		ItemStack itemStack = ent.getItemBySlot(EquipmentSlot.HEAD);
		//水下面具呼吸
		if (itemStack.is(CItems.WATER_MASK.getItem()) && ent.isEyeInFluid(FluidTags.WATER)) {
			ent.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, false, false, false));
			ent.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0, false, false, false));
		}
	}
}
