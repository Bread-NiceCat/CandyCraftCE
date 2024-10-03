package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
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
	public static final RegistryEntry<CreativeModeTab> TAB = register(MOD_ID,
			() -> builder().title(Component.translatable(TITLE_KEY))
					.icon(() -> CItems.DRAGIBUS.get().getDefaultInstance())
					.displayItems((parameters, output) -> ENTRIES.stream().map(Supplier::get).forEach(output::accept))
					.build());
	
	
	public static void add(Supplier<ItemStack> stack) {
		ENTRIES.add(stack);
	}
	
	public static void add(ItemLike item) {
		add(() -> item.asItem().getDefaultInstance());
	}
	
	private static RegistryEntry<CreativeModeTab> register(String name, Supplier<CreativeModeTab> builder) {
		return CandyCraftCE.register(BuiltInRegistries.CREATIVE_MODE_TAB, prefix(name), builder);
	}
	
	@ExpectPlatform
	public static CreativeModeTab.Builder builder() {
		return impossibleCode();
	}
}
