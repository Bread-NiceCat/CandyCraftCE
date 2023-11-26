package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Created in 2023/9/10 2:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class CItemTags {
	public static final TagKey<Item> LICORICE = cbind("licorice");
	public static final TagKey<Item> HONEYCOMB = cbind("honeycomb");
	public static final TagKey<Item> PEZ = cbind("pez");

	public static final TagKey<Item> EMBLEM = cbind("emblem");
	public static final TagKey<Item> CANDY_ARROWS = cbind("candy_arrows");
	public static final TagKey<Item> MARSHMALLOW_PLANKS = cbind("marshmallow_planks");

	private static TagKey<Item> cbind(String name) {
		return TagKey.create(Registries.ITEM, ResourceUtils.prefix(name));
	}
}
