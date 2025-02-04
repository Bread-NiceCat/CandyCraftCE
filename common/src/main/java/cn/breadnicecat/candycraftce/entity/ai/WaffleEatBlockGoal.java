package cn.breadnicecat.candycraftce.entity.ai;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.block.blocks.CustardPuddingBlock;
import cn.breadnicecat.candycraftce.entity.entities.mobs.WaffleSheep;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;

/**
 * Created in 2025/2/4 14:19
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class WaffleEatBlockGoal extends EatBlockGoal {
	private final WaffleSheep mob;
	private final Level level;
	
	public WaffleEatBlockGoal(WaffleSheep sheep) {
		super(sheep);
		this.mob = sheep;
		level = sheep.level();
	}
	
	@Override
	public boolean canUse() {
		if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 100 : 2000) == 0) {
			BlockPos blockPos = this.mob.blockPosition();
			return this.level.getBlockState(blockPos.below()).is(CBlocks.CUSTARD_PUDDING.get());
		} else {
			return false;
		}
	}
	
	@Override
	public void tick() {
		BlockPos pos = mob.blockPosition();
		BlockPos below = pos.below();
		if ((this.level.getBlockState(below)).is(CBlocks.CUSTARD_PUDDING.get())) {
			if (this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				//2001
				this.level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, below, Block.getId(CBlocks.CUSTARD_PUDDING.defaultBlockState()));
				this.level.setBlock(below, CustardPuddingBlock.DIRT_ALT.defaultBlockState(), 2);
			}
			this.mob.ate();
		}
	}
}
