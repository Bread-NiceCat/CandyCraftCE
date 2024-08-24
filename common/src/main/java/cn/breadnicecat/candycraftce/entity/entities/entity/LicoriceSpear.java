package cn.breadnicecat.candycraftce.entity.entities.entity;

import cn.breadnicecat.candycraftce.entity.CEntities;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/7/2 21:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class LicoriceSpear extends AbstractArrow {
	public LicoriceSpear(EntityType<? extends AbstractArrow> entityType, Level level) {
		super(entityType, level);
	}
	
	public LicoriceSpear(Level level, double x, double y, double z, ItemStack itemStack) {
		super(CEntities.LICORICE_SPEAR.get(), x, y, z, level, itemStack, null);
	}
	
	public LicoriceSpear(Level level, LivingEntity shooter, ItemStack stack) {
		super(CEntities.LICORICE_SPEAR.get(), shooter, level, stack, null);
	}
	
	@Override
	protected @NotNull ItemStack getDefaultPickupItem() {
		return CItems.LICORICE_SPEAR.getDefaultInstance();
	}
	
}
