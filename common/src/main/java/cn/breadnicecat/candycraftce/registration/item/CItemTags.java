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
	public static final TagKey<Item> LICORICE = bind("licorice");
	public static final TagKey<Item> HONEYCOMB = bind("honeycomb");
	public static final TagKey<Item> PEZ = bind("pez");

	private static TagKey<Item> bind(String name) {
		return TagKey.create(Registries.ITEM, ResourceUtils.prefix(name));
	}
}
