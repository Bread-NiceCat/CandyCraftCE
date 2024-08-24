package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.entity.entities.entity.CaramelArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2023/10/29 9:01
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class HoneycombArrowItem extends ArrowItem {
	public HoneycombArrowItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public @NotNull Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		return new CaramelArrow(pos.x(), pos.y(), pos.z(), level, stack, null);
	}
	
	public @NotNull AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
		return new CaramelArrow(shooter, level, ammo.copyWithCount(1), weapon);
	}
}
