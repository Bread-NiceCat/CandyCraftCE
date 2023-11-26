package cn.breadnicecat.candycraftce.registration.item.items;

import cn.breadnicecat.candycraftce.registration.item.CArmorMaterials;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

/**
 * Created in 2023/11/26 8:40
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ItemWaterMask extends ArmorItem {
	public ItemWaterMask(Properties properties) {
		this(CArmorMaterials.WATER_MASK, properties);
	}

	public ItemWaterMask(ArmorMaterial armorMaterial, Properties properties) {
		super(armorMaterial, Type.HELMET, properties);
	}

}
