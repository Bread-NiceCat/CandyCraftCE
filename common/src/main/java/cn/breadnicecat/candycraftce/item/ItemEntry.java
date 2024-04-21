package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
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

public class ItemEntry<I extends Item> extends SimpleEntry<Item, I> implements ItemLike {


	public ItemEntry(ResourceKey<Item> key, Supplier<I> item) {
		super(key, item);
	}

	public ItemEntry(Pair<ResourceKey<Item>, Supplier<I>> wrapper) {
		super(wrapper);
	}

	public ItemStack getDefaultInstance() {
		return this.get().getDefaultInstance();
	}

	@Override
	public @NotNull Item asItem() {
		return get();
	}
}
