package cn.breadnicecat.candycraftce.item.fabric;

import cn.breadnicecat.candycraftce.item.ItemEntry;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created in 2023/10/28 23:46
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CItemBuilderImpl {
	public static <I extends Item> ItemEntry<I> register(ResourceLocation name, @NotNull Supplier<I> sup) {
		I i = Registry.register(BuiltInRegistries.ITEM, name, sup.get());
		return new ItemEntry<>(name) {
			@Override
			public I get() {
				return i;
			}
		};
	}

//	public static void registerTabEntry(Pair<ResourceKey<CreativeModeTab>, Supplier<ItemStack>> tab, Either<Supplier<ItemStack>, Supplier<ItemStack>> pos) {
//		_registerTabEntry(tab, pos);
//	}

	public static void registerTabEntry(@NotNull Pair<ResourceKey<CreativeModeTab>, Supplier<ItemStack>> tab, Either<Supplier<ItemStack>, Supplier<ItemStack>> posSup) {
		var pos = posSup.mapBoth(Supplier::get, Supplier::get);
		ItemGroupEvents.modifyEntriesEvent(tab.getFirst()).register(c -> {
			if (!c.getContext().hasPermissions()) {
				return;
			}
			ItemStack itemStack = tab.getSecond().get();
			pos.ifLeft(k -> {
						if (k == ItemStack.EMPTY) {
							c.accept(itemStack);
						} else {
							c.addBefore(k, itemStack);
						}
					})
					.ifRight(k -> {
						if (k == ItemStack.EMPTY) {
							c.prepend(itemStack);//开头
						} else {
							c.addAfter(k, itemStack);
						}
					});
		});
	}


}
