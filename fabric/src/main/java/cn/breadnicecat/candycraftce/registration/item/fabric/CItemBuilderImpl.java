package cn.breadnicecat.candycraftce.registration.item.fabric;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.registration.item.ItemEntry;
import cn.breadnicecat.candycraftce.utils.tools.Accessor;
import cn.breadnicecat.candycraftce.utils.tools.AccessorImpl;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created in 2023/10/28 23:46
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CItemBuilderImpl {
	@SuppressWarnings("unchecked")
	public static <I extends Item> ItemEntry<I> register(ResourceLocation name, Supplier<I> sup) {
		Accessor<I> ass = new AccessorImpl<>();
		//适应Forge的注册模式
		CandyCraftCE.hookPostBootstrap(() -> ass.set((I) Items.registerItem(name, sup.get())));
		return new ItemEntry<>(name) {
			@Override
			public I getItem() {
				return ass.get();
			}
		};
	}

	public static void registerTabEntry(Pair<ResourceKey<CreativeModeTab>, Supplier<ItemStack>> tab, Either<Supplier<ItemStack>, Supplier<ItemStack>> pos) {
		//Item延后注册了
		CandyCraftCE.hookPostBootstrap(() -> _registerTabEntry(tab, pos));
	}

	public static void _registerTabEntry(@NotNull Pair<ResourceKey<CreativeModeTab>, Supplier<ItemStack>> tab, Either<Supplier<ItemStack>, Supplier<ItemStack>> posSup) {
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
