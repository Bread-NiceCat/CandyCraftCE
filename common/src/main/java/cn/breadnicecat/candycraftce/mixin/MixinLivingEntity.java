package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.misc.mixin_ref.$LivingEntity;
import net.minecraft.network.syncher.SynchedEntityData;
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

import java.util.Objects;

/**
 * Created in 2023/11/26 8:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot slot);
	
	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	protected void hurt(@NotNull DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		//果冻靴子免摔落伤
		if (source.is(DamageTypes.FALL) && getItemBySlot(EquipmentSlot.FEET).is(CItems.TRAMPOJELLY_BOOTS.get())) {
			cir.setReturnValue(false);
		}
	}
	
	@Inject(method = "tick", at = @At("HEAD"))
	protected void tickHelmet(CallbackInfo ci) {
		LivingEntity ent = (LivingEntity) (Object) this;
		ItemStack itemStack = ent.getItemBySlot(EquipmentSlot.HEAD);
		//水下面具呼吸
		if (itemStack.is(CItems.WATER_MASK.get()) && ent.isEyeInFluid(FluidTags.WATER)) {
			ent.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, false, false, false));
			ent.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0, false, false, false));
		}
	}
	
	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	protected void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
		//记录被焦糖箭打中的次数
		builder.define($LivingEntity.DATA_CARAMEL_ARROW_COUNT_ID, 0);
	}
	
	/**
	 * 创建
	 */
	@SuppressWarnings("unused")
	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void clinit(CallbackInfo ci) {
		Objects.requireNonNull($LivingEntity.DATA_CARAMEL_ARROW_COUNT_ID);
	}
}
