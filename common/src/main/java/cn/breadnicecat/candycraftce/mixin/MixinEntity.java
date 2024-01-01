package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.level.CDims;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created in 2024/1/1 9:28
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
@Mixin(Entity.class)
public abstract class MixinEntity {

	@Shadow
	public abstract float getYRot();

	@Shadow
	public abstract float getXRot();

	@Shadow
	public abstract Vec3 position();

	@Shadow
	private Level level;

	@Shadow
	public abstract Level level();

	@Inject(method = "findDimensionEntryPoint", at = @At("HEAD"), cancellable = true)
	private void findDimensionEntryPoint(ServerLevel destination, CallbackInfoReturnable<PortalInfo> cir) {
		PortalInfo info = CommonUtils.cis_trans(level.dimension(), destination.dimension(), Level.OVERWORLD, CDims.CANDYLAND,
				() -> new PortalInfo(position().with(Direction.Axis.Y, CDims.LAND_MAX_Y + 16), Vec3.ZERO, getXRot(), getYRot()),
				() -> {
					BlockPos pos1 = destination.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, destination.getSharedSpawnPos());
					return new PortalInfo(Vec3.atCenterOf(pos1), Vec3.ZERO, getXRot(), getYRot());
				},
				null);
		if (info != null) cir.setReturnValue(info);
	}
}
