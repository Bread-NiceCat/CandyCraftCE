package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.registration.block.blocks.PuddingFarm;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cn.breadnicecat.candycraftce.registration.block.CBlocks.PUDDING_FARMLAND;

/**
 * Created in 2024/1/28 23:49
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
@Mixin(FarmBlock.class)
public abstract class MixinFarmBlock {
	@Inject(method = "turnToDirt", at = @At("HEAD"), cancellable = true)
	private static void turnToDirt(Entity entity, BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
		if (state.is(PUDDING_FARMLAND.getBlock())) {
			PuddingFarm.turnToDirt(entity, state, level, pos);
			ci.cancel();
		}
	}

	@Inject(method = "shouldMaintainFarmland", at = @At("HEAD"), cancellable = true)
	private static void shouldMaintainFarmland(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (level.getBlockState(pos).is(PUDDING_FARMLAND.getBlock())) {
			cir.setReturnValue(PuddingFarm.shouldMaintainFarmland(level, pos));
		}
	}
}
