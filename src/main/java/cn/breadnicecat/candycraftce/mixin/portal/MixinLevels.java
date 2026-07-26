package cn.breadnicecat.candycraftce.mixin.portal;

import cn.breadnicecat.candycraftce.core.block.blocks.CaramelPortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by NiceCat on 2026/2/26.
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
@Mixin(ServerLevel.class)
public class MixinLevels {
	@Inject(method = "gameEvent", at = @At("HEAD"))
	public void gameEvent(GameEvent event, Vec3 position, GameEvent.Context context, CallbackInfo ci) {
		Level self = (Level) (Object) this;
		if (event == GameEvent.FLUID_PLACE) {
			BlockPos pos = BlockPos.containing(position);
			FluidState fluid = self.getFluidState(pos);
			if (fluid.is(Fluids.LAVA) && fluid.isSource()) {
				CaramelPortalBlock.onLavaPlace(self, pos);
			}
		}
	}
}
