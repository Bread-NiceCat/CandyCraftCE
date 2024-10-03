package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.entity.entities.misc.LicoriceSpear;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/7/3 上午11:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class LicoriceSpearItem extends TridentItem implements ProjectileItem {
	public LicoriceSpearItem(Properties properties) {
		super(properties);
	}
	
	/**
	 * Vanilla Copy
	 */
	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity e, int timeCharged) {
		if (!(e instanceof Player player)) {
			return;
		}
		int i = this.getUseDuration(stack, player) - timeCharged;
		if (i < 10) {
			return;
		}
		if (!level.isClientSide) {
			//changed
			LicoriceSpear spear = new LicoriceSpear(level, player, stack);
			spear.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
			if (player.hasInfiniteMaterials()) {
				spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
			}
			level.addFreshEntity(spear);
			level.playSound(null, spear, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
			if (!player.hasInfiniteMaterials()) {
				player.getInventory().removeItem(stack);
			}
		}
		player.awardStat(Stats.ITEM_USED.get(this));
	}
	
	@Override
	public @NotNull Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		LicoriceSpear spear = new LicoriceSpear(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
		spear.pickup = AbstractArrow.Pickup.ALLOWED;
		return spear;
	}
}
