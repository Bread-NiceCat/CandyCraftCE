package cn.breadnicecat.candycraftce.entity.entities.entity;

import cn.breadnicecat.candycraftce.entity.CEntities;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import static cn.breadnicecat.candycraftce.mixin.ThrownTridentAccessor.getID_FOIL;
import static cn.breadnicecat.candycraftce.mixin.ThrownTridentAccessor.getID_LOYALTY;

/**
 * Created in 2024/7/2 21:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class LicoriceSpear extends ThrownTrident {
	public LicoriceSpear(EntityType<? extends ThrownTrident> entityType, Level level) {
		super(entityType, level);
		this.tridentItem = CItems.LICORICE_SPEAR.getDefaultInstance();
	}
	
	public LicoriceSpear(Level level, LivingEntity shooter, ItemStack stack) {
		this(CEntities.LICORICE_SPEAR.get(), level);
		setPos(shooter.getX(), shooter.getEyeY() - (double) 0.1f, shooter.getZ());
		setOwner(shooter);
		if (shooter instanceof Player) {
			pickup = Pickup.ALLOWED;
		}
		this.tridentItem = stack;
		this.entityData.set(getID_LOYALTY(), (byte) EnchantmentHelper.getLoyalty(stack));
		this.entityData.set(getID_FOIL(), stack.hasFoil());
	}
	
}
