package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.entity.entities.mobs.Bunny;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created in 2024/8/5 上午9:03
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(targets = "net.minecraft.world.entity.animal.Rabbit$RaidGardenGoal")
public abstract class MixinRaidGardenGoal {
	@Shadow
	@Final
	private Rabbit rabbit;
	
	@Inject(method = "isValidTarget", at = @At("HEAD"), cancellable = true)
	void isValidTarget(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (rabbit instanceof Bunny) {
			cir.setReturnValue(false);
		}
	}
}
