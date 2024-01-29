package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.registration.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * Created in 2023/8/11 13:31
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */

public abstract class ItemEntry<I extends Item> extends RegistryEntry implements Supplier<I> {


	public ItemEntry(ResourceLocation id) {
		super(id);
	}

	public abstract I getItem();

	@Override
	@Deprecated
	public I get() {
		return getItem();
	}

	public ItemStack getDefaultInstance() {
		return getItem().getDefaultInstance();
	}


}
