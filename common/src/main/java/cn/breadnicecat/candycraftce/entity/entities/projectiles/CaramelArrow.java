package cn.breadnicecat.candycraftce.entity.entities.projectiles;

import cn.breadnicecat.candycraftce.entity.CEntities;
import cn.breadnicecat.candycraftce.item.CItems;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/7/1 23:45
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CaramelArrow extends Arrow {
	public CaramelArrow(EntityType<? extends CaramelArrow> entityType, Level level) {
		super(entityType, level);
	}
	public CaramelArrow(Level level) {
		this(CEntities.CARAMEL_ARROW.get(), level);
	}
	public CaramelArrow(Level level, double x, double y, double z) {
		this(level);
		setPos(x,y,z);
	}
	
	public CaramelArrow(Level level, LivingEntity shooter) {
		this(level,shooter.getX(), shooter.getEyeY() - (double)0.1f, shooter.getZ());
		this.setOwner(shooter);
		if (shooter instanceof Player) {
			this.pickup = Pickup.ALLOWED;
		}
	}
	
	@Override
	protected @NotNull ItemStack getPickupItem() {
		return CItems.HONEYCOMB_ARROW.getDefaultInstance();
	}
}
