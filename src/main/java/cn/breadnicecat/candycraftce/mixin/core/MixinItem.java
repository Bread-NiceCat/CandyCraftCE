package cn.breadnicecat.candycraftce.mixin.core;

import cn.breadnicecat.candycraftce.core.block.blocks.plant.ISugarTarget;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * <p>
 *
 */
@Mixin(Item.class)
public class MixinItem {
	@Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
	private void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if ((Object) this == Items.SUGAR) {
			BlockPos pos = context.getClickedPos();
			Level level = context.getLevel();
			if (ISugarTarget.grow(context.getItemInHand(), level, pos)) {
				if (!level.isClientSide) {
					level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
				}
				cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
			}
		}
	}
}
