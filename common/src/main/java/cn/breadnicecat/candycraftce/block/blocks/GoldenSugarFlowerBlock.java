package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created in 2024/2/15 21:28
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class GoldenSugarFlowerBlock extends CandyPlantBlock {
	public GoldenSugarFlowerBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity living && level.random.nextFloat() < 0.1 / TickUtils.TICK_PER_SEC) {//每秒概率为0.1
			//持续上效果的概率为1-0.9^2=0.19
			living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (2 * TickUtils.SEC2TICK)));
			living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, (int) (2 * TickUtils.SEC2TICK)));
		}
	}
}
