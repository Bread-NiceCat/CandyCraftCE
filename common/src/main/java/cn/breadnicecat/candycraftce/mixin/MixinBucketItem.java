package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.level.multiblocks.VectorPortalShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.breadnicecat.candycraftce.block.blocks.CaramelPortalBlock.CONFIG;
import static cn.breadnicecat.candycraftce.block.blocks.CaramelPortalBlock.PLACER;

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
	
	@Inject(method = "checkExtraContent", at = @At("HEAD"), cancellable = true)
	public void checkExtraContent(Player player, Level level, ItemStack containerStack, BlockPos pos, CallbackInfo ci) {
		if (this.content.isSame(Fluids.LAVA)) {
			VectorPortalShape.findPortal(level, pos, CONFIG).ifPresent(t -> {
				for (BlockPos frame : t.getAllFrames()) {
					BlockState state = level.getBlockState(frame);
					if (state.is(CBlocks.SUGAR_BLOCK.get())) {
						level.setBlockAndUpdate(frame, CBlocks.CARAMEL_BLOCK.defaultBlockState());
					}
				}
				t.build(level, PLACER);
				ci.cancel();
			});
		}
	}
}
