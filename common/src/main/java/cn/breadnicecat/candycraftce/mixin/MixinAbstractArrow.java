package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.entity.entities.CaramelArrow;
import cn.breadnicecat.candycraftce.mixin_ref.$LivingEntity;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created in 2024/7/2 23:09
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * 给实体添加被焦糖箭打中的属性
 * <p>
 **/
@Mixin(AbstractArrow.class)
public abstract class MixinAbstractArrow {
	@Inject(method = "onHitEntity",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/entity/LivingEntity;setArrowCount(I)V",
					shift = At.Shift.AFTER
			)
	)
	protected void onHitEntity(@NotNull EntityHitResult result, CallbackInfo ci) {
		LivingEntity instance = (LivingEntity) result.getEntity();
		if ((AbstractArrow) (Object) this instanceof CaramelArrow) {
			SynchedEntityData data = instance.getEntityData();
			data.set($LivingEntity.LivingEntity$DATA_CARAMEL_ARROW_COUNT_ID,
					data.get($LivingEntity.LivingEntity$DATA_CARAMEL_ARROW_COUNT_ID) + 1);
			instance.setArrowCount(instance.getArrowCount() - 1);
			
		}
	}
}
