package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created in 2023/8/11 13:31
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */

public abstract class ItemEntry<I extends Item> extends RegistryEntry implements Supplier<I>, ItemLike {


	public ItemEntry(ResourceLocation id) {
		super(id);
	}

	public ItemStack getDefaultInstance() {
		return this.get().getDefaultInstance();
	}

	@Override
	public @NotNull Item asItem() {
		return get();
	}
}
