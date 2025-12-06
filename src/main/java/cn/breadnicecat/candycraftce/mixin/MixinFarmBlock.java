package cn.breadnicecat.candycraftce.mixin;


import cn.breadnicecat.candycraftce.core.block.CBlocks;
import cn.breadnicecat.candycraftce.core.block.blocks.PuddingFarmBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * Created in 2024/1/28 23:49
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
@Mixin(FarmBlock.class)
public abstract class MixinFarmBlock {
	@Inject(method = "turnToDirt", at = @At("HEAD"), cancellable = true)
	private static void turnToDirt(Entity entity, @NotNull BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
		if (state.is(CBlocks.INSTANCE.getPudding_farm().getBlock())) {
			PuddingFarmBlock.Companion.turnToDirt(entity, state, level, pos);
			ci.cancel();
		}
	}

	@Inject(method = "shouldMaintainFarmland", at = @At("HEAD"), cancellable = true)
	private static void shouldMaintainFarmland(@NotNull BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		Block block = CBlocks.INSTANCE.getPudding_farm().getBlock();
		if (level.getBlockState(pos).is(block)) {
			cir.setReturnValue(PuddingFarmBlock.Companion.shouldMaintainFarmland(level, pos));
		}
	}

}
