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
 * Created in 2024/2/15 21:35
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class AcidMintFlowerBlock extends CandyPlantBlock {
	public AcidMintFlowerBlock(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity living && level.random.nextFloat() < 0.1 / TickUtils.TICK_PER_SEC) {
			living.addEffect(new MobEffectInstance(MobEffects.POISON, 2 * TickUtils.SEC2TICK));
			living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 2 * TickUtils.SEC2TICK));
		}
	}
}
