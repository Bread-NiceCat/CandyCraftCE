package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.registration.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Created in 2023/8/11 13:31
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */

public abstract class ItemEntry<I extends Item> extends RegistryEntry {


	public ItemEntry(ResourceLocation name) {
		super(name);
	}

	public abstract I getItem();

	public ItemStack getDefaultInstance() {
		return getItem().getDefaultInstance();
	}


}
