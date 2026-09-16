package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.utils.InventoryUtils;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WaterMaskItem extends ArmorItem {
	public WaterMaskItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean held) {
		if (!level.isClientSide && entity instanceof LivingEntity living && InventoryUtils.isArmorSlot(slot)) {
			living.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 5, 0, false, false, false));
			if (living.isUnderWater()) {
				living.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false, false));
			}
		}
	}
}