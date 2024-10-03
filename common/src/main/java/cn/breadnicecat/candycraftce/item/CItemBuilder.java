package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.BlockEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/10/28 23:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CItemBuilder<I extends Item> implements ICandyBuilder<CItemBuilder<I>> {
	private final String name;
	private final LinkedHashSet<ResourceKey<CreativeModeTab>> extraTabs = new LinkedHashSet<>();
	private final Function<Properties, I> factory;
	public Properties properties = new Properties();
	public boolean ctab = true;
	private Function<Properties, Properties> propertiesMu = p -> p;
	
	public static <I extends Item> CItemBuilder<I> create(String name, Function<Properties, I> factory) {
		return new CItemBuilder<>(name, factory);
	}
	
	public static CItemBuilder<Item> create(String name) {
		return create(name, Item::new);
	}
	
	
	protected CItemBuilder(String name, Function<Properties, I> factory) {
		this.name = name;
		this.factory = factory;
	}
	
	public static CItemBuilder<BlockItem> block(BlockEntry<? extends Block> block) {
		return create(block.getId().getPath(), (p) -> new BlockItem(block.get(), p));
	}
	
	
	/**
	 * 新的Properties,不会自动copy
	 * 注:默认直接是新的Properties
	 */
	public CItemBuilder<I> setProperties(@NotNull Properties prop) {
		this.properties = Objects.requireNonNull(prop);
		return this;
	}
	
	public CItemBuilder<I> modifyProperties(@NotNull Function<Properties, Properties> prop) {
		this.propertiesMu = propertiesMu.andThen(prop);
		return this;
	}
	
	
	public CItemBuilder<I> setCtab(boolean ctab) {
		this.ctab = ctab;
		return this;
	}
	
	public CItemBuilder<I> setTab(ResourceKey<CreativeModeTab> tab) {
		if (tab == CCTab.TAB.getKey()) {
			ctab = true;
		} else {
			extraTabs.add(tab);
		}
		return this;
	}
	
	/**
	 * @param food 调用properties#food
	 *             NOTE: 以美食为主当然要单独开辟一的API啦！
	 * @see Properties#food(FoodProperties)
	 */
	@Override
	public CItemBuilder<I> setFood(FoodProperties food) {
		properties.food(food);
		return this;
	}
	
	/**
	 * @param duration tick
	 */
	@Override
	public CItemBuilder<I> sugarFuel(int duration) {
		properties.component(CDataComponents.SUGAR_BURN_TIME.get(), duration);
		return this;
	}
	
	
	public ItemEntry<I> save() {
		ItemEntry<I> entry = register(name, () -> factory.apply(propertiesMu.apply(properties)));
		CItems.ITEMS.add(entry);
		if (ctab) CCTab.add(entry);
		extraTabs.forEach(k -> setTab(k, entry::getDefaultInstance));
		return entry;
	}
	
	private static <I extends Item> @NotNull ItemEntry<I> register(String name, Supplier<I> factory) {
		return new ItemEntry<>(CandyCraftCE.register(BuiltInRegistries.ITEM, prefix(name), factory));
	}
	
	@SafeVarargs
	@ExpectPlatform
	public static void setTab(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack>... stack) {
		impossibleCode();
	}
	
}
