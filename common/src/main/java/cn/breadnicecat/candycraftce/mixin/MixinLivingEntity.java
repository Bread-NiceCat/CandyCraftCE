package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.misc.mixin_ref.$LivingEntity;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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