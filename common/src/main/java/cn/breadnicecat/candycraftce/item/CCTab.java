package cn.breadnicecat.candycraftce.item;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created in 2023/8/9 13:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class CCTab {
	public static final LinkedHashSet<Supplier<ItemStack>> ENTRIES = new LinkedHashSet<>();
	public static final String TITLE_KEY = "itemGroup.candycraftce";
	public static ResourceKey<CreativeModeTab> TAB_KEY = register("candycraftce", builder -> builder
			.title(Component.translatable(TITLE_KEY))
			.icon(() -> CItems.PEZ.get().getDefaultInstance())
			.displayItems((parameters, output) -> ENTRIES.stream().map(Supplier::get).forEach(output::accept))
			.build());

	@ExpectPlatform
	private static ResourceKey<CreativeModeTab> register(String key, Function<CreativeModeTab.Builder, CreativeModeTab> builder) {
		throw new AssertionError();
	}

	public static void add(Supplier<ItemStack> stack) {
		ENTRIES.add(stack);
	}

	public static void add(ItemEntry<?> item) {
		add(item::getDefaultInstance);
	}
}
