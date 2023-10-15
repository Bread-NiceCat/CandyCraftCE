package cn.breadnicecat.candycraftce.registration.item.fabric;

import cn.breadnicecat.candycraftce.registration.item.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

/**
 * Created in 2023/9/9 15:26
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class CItemsItemBuilderImpl {
	@SuppressWarnings("unchecked")
	public static <I extends Item> ItemEntry<I> register(ResourceLocation name, Supplier<I> sup) {
		I item = (I) Items.registerItem(name, sup.get());
		return new ItemEntry<>(name) {
			@Override
			public I getItem() {
				return item;
			}
		};
	}
}
