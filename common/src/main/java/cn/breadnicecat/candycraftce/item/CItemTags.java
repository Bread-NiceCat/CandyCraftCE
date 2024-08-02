package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Created in 2023/9/10 2:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class CItemTags {
//	public static final TagKey<Item> IT_LICORICE = create("licorice");
//	public static final TagKey<Item> IT_HONEYCOMB = create("honeycomb");
//	public static final TagKey<Item> IT_PEZ = create("pez");
	
	public static final TagKey<Item> IT_LEAF = create("leaf");
	public static final TagKey<Item> IT_EMBLEM = create("emblem");
	public static final TagKey<Item> CANDY_ARROW = create("candy_arrow");
	public static final TagKey<Item> IT_RETURN_TICKET = create("return_ticket");
	
	private static TagKey<Item> create(String name) {
		return TagKey.create(Registries.ITEM, ResourceUtils.prefix(name));
	}
}
