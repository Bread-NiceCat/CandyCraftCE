package cn.breadnicecat.candycraftce.entity.entities.misc;

import cn.breadnicecat.candycraftce.entity.CEntityTypes;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/7/1 23:45
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CaramelArrow extends AbstractArrow {
	
	public CaramelArrow(EntityType<? extends CaramelArrow> entityType, Level level) {
		super(entityType, level);
	}
	
	public CaramelArrow(Level level) {
		this(CEntityTypes.CARAMEL_ARROW.get(), level);
	}
	
	public CaramelArrow(double x, double y, double z, Level level) {
		this(level);
		setPos(x, y, z);
	}
	
	
	public CaramelArrow(double x, double y, double z, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(CEntityTypes.CARAMEL_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
	}
	
	public CaramelArrow(LivingEntity owner, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(CEntityTypes.CARAMEL_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
	}
	
	@Override
	protected @NotNull ItemStack getPickupItem() {
		return CItems.HONEYCOMB_ARROW.getDefaultInstance();
	}
	
	@Override
	protected @NotNull ItemStack getDefaultPickupItem() {
		return CItems.HONEYCOMB_ARROW.getDefaultInstance();
	}
	
}

