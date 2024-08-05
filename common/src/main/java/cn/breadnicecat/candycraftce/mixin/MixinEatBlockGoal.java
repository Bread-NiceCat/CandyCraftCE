package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.block.blocks.CustardPuddingBlock;
import cn.breadnicecat.candycraftce.entity.entities.mobs.WaffleSheep;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created in 2024/8/4 下午10:42
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(EatBlockGoal.class)
public abstract class MixinEatBlockGoal {
	@Shadow
	private int eatAnimationTick;
	
	@Shadow
	@Final
	private Mob mob;
	
	@Shadow
	@Final
	private Level level;
	
	@Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
	private void canUse(CallbackInfoReturnable<Boolean> cir) {
		if (mob instanceof WaffleSheep) {
			if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
				cir.setReturnValue(false);
			} else {
				BlockPos blockPos = this.mob.blockPosition();
				cir.setReturnValue(this.level.getBlockState(blockPos.below()).is(CBlocks.CUSTARD_PUDDING.get()));
			}
		}
	}
	
	@Inject(method = "tick", at = @At("RETURN"))
	private void tick(CallbackInfo ci) {
		BlockPos pos = mob.blockPosition();
		BlockPos below = pos.below();
		if ((this.level.getBlockState(below)).is(CBlocks.CUSTARD_PUDDING.get())) {
			if (this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				//2001
				this.level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, below, Block.getId(CBlocks.CUSTARD_PUDDING.defaultBlockState()));
				this.level.setBlock(below, CustardPuddingBlock.DIRT_LIKE.defaultBlockState(), 2);
			}
			this.mob.ate();
		}
	}
}
