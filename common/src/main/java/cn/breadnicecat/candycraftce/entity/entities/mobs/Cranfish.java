package cn.breadnicecat.candycraftce.entity.entities.mobs;

import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/8/3 下午8:08
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class Cranfish extends AbstractFish {
	public Cranfish master = null;
	
	public Cranfish(EntityType<? extends AbstractFish> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
	}
	
	@Override
	protected @NotNull SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}
	
	@Override
	public @NotNull ItemStack getBucketItemStack() {
		return CItems.CRANFISH_BUCKET.getDefaultInstance();
	}
}
