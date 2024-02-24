package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.Bindings;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/8/9 13:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class CCTab {
	public static final LinkedHashSet<Supplier<ItemStack>> ENTRIES = new LinkedHashSet<>();
	public static final String TITLE_KEY = "itemGroup.candycraftce";
	public static final ResourceKey<CreativeModeTab> TAB = Bindings.registerTab(prefix("candycraftce"), builder -> builder
			.title(Component.translatable(TITLE_KEY))
			.icon(() -> CItems.PEZ.get().getDefaultInstance())
			.displayItems((parameters, output) -> ENTRIES.stream().map(Supplier::get).forEach(output::accept))
			.build());


	public static void add(Supplier<ItemStack> stack) {
		ENTRIES.add(stack);
	}

	public static void add(ItemLike item) {
		add(() -> item.asItem().getDefaultInstance());
	}
}
