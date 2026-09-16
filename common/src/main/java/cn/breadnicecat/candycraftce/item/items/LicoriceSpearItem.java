package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.entity.entities.misc.LicoriceSpear;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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

	public static ItemAttributeModifiers createAttributes() {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 6.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.9F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity e, int timeCharged) {
		if (e instanceof Player player) {
			int i = this.getUseDuration(stack, e) - timeCharged;
			if (i >= 10) {
				float f = EnchantmentHelper.getTridentSpinAttackStrength(stack, player);
				if (!(f > 0.0F) || player.isInWaterOrRain()) {
					if (stack.getDamageValue () < stack.getMaxDamage () - 1) {
						Holder<SoundEvent> holder = EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.TRIDENT_SOUND).orElse(SoundEvents.TRIDENT_THROW);
						if (!level.isClientSide) {
							stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(e.getUsedItemHand()));
							if (f == 0.0F) {
								LicoriceSpear spear = new LicoriceSpear(level, player, stack);
								spear.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
								if (player.hasInfiniteMaterials()) {
									spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
								}

								level.addFreshEntity(spear);
								level.playSound(null, spear, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
								if (!player.hasInfiniteMaterials()) {
									player.getInventory().removeItem(stack);
								}
							}
						}
						player.awardStat(Stats.ITEM_USED.get(this));
						if (f > 0.0F) {
							float f7 = player.getYRot();
							float f1 = player.getXRot();
							float f2 = -Mth.sin(f7 * (float) (Math.PI / 180.0)) * Mth.cos(f1 * (float) (Math.PI / 180.0));
							float f3 = -Mth.sin(f1 * (float) (Math.PI / 180.0));
							float f4 = Mth.cos(f7 * (float) (Math.PI / 180.0)) * Mth.cos(f1 * (float) (Math.PI / 180.0));
							float f5 = Mth.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
							f2 *= f / f5;
							f3 *= f / f5;
							f4 *= f / f5;
							player.push((double)f2, (double)f3, (double)f4);
							player.startAutoSpinAttack(20, 8.0F, stack);
							if (player.onGround()) {
								float f6 = 1.1999999F;
								player.move(MoverType.SELF, new Vec3(0.0, 1.1999999F, 0.0));
							}
							level.playSound(null, player, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack material) {
		return material.is(CItems.LICORICE.get());
	}

	@Override
	public @NotNull Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		LicoriceSpear spear = new LicoriceSpear(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
		spear.pickup = AbstractArrow.Pickup.ALLOWED;
		return spear;
	}
}