package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.entity.entities.entity.LicoriceSpear;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Created in 2024/7/3 上午11:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class LicoriceSpearItem extends TridentItem {
	public LicoriceSpearItem(Properties properties) {
		super(properties);
	}
	
	/**
	 * Vanilla Copy
	 */
	@SuppressWarnings("unused")
	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
		if (!(livingEntity instanceof Player player2)) {
			return;
		}
		int i = this.getUseDuration(stack) - timeCharged;
		if (i < 10) {
			return;
		}
		int j = EnchantmentHelper.getRiptide(stack);
		if (j > 0 && !player2.isInWaterOrRain()) {
			return;
		}
		if (!level.isClientSide) {
			stack.hurtAndBreak(1, player2, player -> player.broadcastBreakEvent(livingEntity.getUsedItemHand()));
			if (j == 0) {
				LicoriceSpear thrownTrident = new LicoriceSpear(level, player2, stack);
				thrownTrident.shootFromRotation(player2, player2.getXRot(), player2.getYRot(), 0.0f, 2.5f + (float) j * 0.5f, 1.0f);
				if (player2.getAbilities().instabuild) {
					thrownTrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
				}
				level.addFreshEntity(thrownTrident);
				level.playSound(null, thrownTrident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0f, 1.0f);
				if (!player2.getAbilities().instabuild) {
					player2.getInventory().removeItem(stack);
				}
			}
		}
		player2.awardStat(Stats.ITEM_USED.get(this));
		if (j > 0) {
			float f = player2.getYRot();
			float g = player2.getXRot();
			float h = -Mth.sin(f * ((float) Math.PI / 180)) * Mth.cos(g * ((float) Math.PI / 180));
			float k = -Mth.sin(g * ((float) Math.PI / 180));
			float l = Mth.cos(f * ((float) Math.PI / 180)) * Mth.cos(g * ((float) Math.PI / 180));
			float m = Mth.sqrt(h * h + k * k + l * l);
			float n = 3.0f * ((1.0f + (float) j) / 4.0f);
			player2.push(h *= n / m, k *= n / m, l *= n / m);
			player2.startAutoSpinAttack(20);
			if (player2.onGround()) {
				float o = 1.1999999f;
				player2.move(MoverType.SELF, new Vec3(0.0, 1.1999999284744263, 0.0));
			}
			SoundEvent soundEvent = j >= 3 ? SoundEvents.TRIDENT_RIPTIDE_3 : (j == 2 ? SoundEvents.TRIDENT_RIPTIDE_2 : SoundEvents.TRIDENT_RIPTIDE_1);
			level.playSound(null, player2, soundEvent, SoundSource.PLAYERS, 1.0f, 1.0f);
		}
	}
}
