package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.level.CaramelPortal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created in 2024/6/6 下午12:49
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * 放置岩浆的时候点燃焦糖门
 * required(fabric lack api
 * <p>
 **/
@Mixin(BucketItem.class)
public abstract class MixinBucketItem {
	@Shadow
	@Final
	private Fluid content;
	
	@Inject(method = "checkExtraContent", at = @At("HEAD"))
	public void checkExtraContent(Player player, Level level, ItemStack containerStack, BlockPos pos, CallbackInfo ci) {
		if (Fluids.LAVA.isSame(this.content)) {
			CaramelPortal.tryBuildIn(level, pos);
		}
	}
}
