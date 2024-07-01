package cn.breadnicecat.candycraftce.item.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.impl.itemgroup.FabricItemGroupBuilderImpl;
import net.fabricmc.fabric.impl.itemgroup.ItemGroupEventsImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Created in 2024/7/1 22:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CItemBuilderImpl {
	@SuppressWarnings("UnstableApiUsage")
	public static void setTab(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack>[] stacks) {
		ItemGroupEvents.modifyEntriesEvent(tab).register(i->{
			if(i.shouldShowOpRestrictedItems())Arrays.stream(stacks).forEach(st->i.accept(st.get()));;
		});
	}
}
