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

public class TrampojellyBootsItem extends ArmorItem {
	public TrampojellyBootsItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean held) {
		if (!level.isClientSide && entity instanceof LivingEntity living && InventoryUtils.isArmorSlot(slot)) {
			living.resetFallDistance();
			living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 0, false, false, false));
		}
	}
}