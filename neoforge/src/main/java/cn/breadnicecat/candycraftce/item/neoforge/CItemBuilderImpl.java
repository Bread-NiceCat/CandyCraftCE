package cn.breadnicecat.candycraftce.item.neoforge;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CItemBuilderImpl {
	private static final HashMap<ResourceKey<CreativeModeTab>, List<Supplier<ItemStack>>> its = new HashMap<>();
	
	public static void setTab(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack>[] stack) {
		its.computeIfAbsent(tab, (i) -> new LinkedList<>()).addAll(Arrays.asList(stack));
	}
	
	@SubscribeEvent
	public static void onBuildCreativeModeTabContents(@NotNull BuildCreativeModeTabContentsEvent event) {
		List<Supplier<ItemStack>> l = its.get(event.getTabKey());
		if (l != null && event.hasPermissions()) {
			l.forEach((i) -> event.accept(i.get()));
		}
	}
}
