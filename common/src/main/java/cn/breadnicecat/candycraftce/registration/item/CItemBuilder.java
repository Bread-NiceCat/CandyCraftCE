package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.registration.block.BlockEntry;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/10/28 23:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CItemBuilder<I extends Item> {
	private final String name;
	private Function<Properties, I> factory;
	public Properties properties = new Properties();
	public boolean ctab = true;

	/**
	 * 用法参见{@link #registerTabEntry(Pair, Either)}
	 */
	public Map<ResourceKey<CreativeModeTab>, Either<Supplier<ItemStack>, Supplier<ItemStack>>> ex_tabs = new HashMap<>();


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

	public static CItemBuilder<BlockItem> block(BlockEntry<Block> block) {
		String namespace = block.getID().getNamespace();
		assertTrue(namespace.equals(CandyCraftCE.MOD_ID), () -> "wrong namespace, require equ " + CandyCraftCE.MOD_ID);
		return create(namespace, (p) -> new BlockItem(block.getBlock(), p));
	}


	/**
	 * 新的Properties
	 * 注:默认直接是新的Properties
	 */
	public CItemBuilder<I> setProperties(@NotNull Properties prop) {
		this.properties = Objects.requireNonNull(prop);
		return this;
	}


	/**
	 * @param food 调用properties#food
	 *             NOTE: 以美食为主当然要单独开辟一的API啦！
	 * @see Properties#food(FoodProperties)
	 */
	public CItemBuilder<I> setFood(FoodProperties food) {
		properties.food(food);
		return this;
	}

	/**
	 * @param nutrition  饱食度
	 * @param saturation 饱和度 # 饱和度=2*饱食度*饱和度修饰符,这里已经进行转化
	 * @param modifier   可以为null,对FoodProperties进行最后的修饰
	 *                   <p>注意:不是对现有的FoodProperties修饰,而是直接赋值
	 * @see FoodData#eat(int, float)
	 */
	public CItemBuilder<I> setFood(int nutrition, int saturation, @Nullable Consumer<FoodProperties> modifier) {
		float saturationModifier = saturation / 2f / nutrition;
		FoodProperties food = new FoodProperties.Builder()
				.nutrition(nutrition).saturationMod(saturationModifier).build();
		if (modifier != null) modifier.accept(food);
		return setFood(food);
	}

	public CItemBuilder<I> setCtab(boolean ctab) {
		this.ctab = ctab;
		return this;
	}


	/**
	 * @param tab 在此tab中的最前面
	 */
	public CItemBuilder<I> setTabPrepend(ResourceKey<CreativeModeTab> tab) {
		setTabAfter(tab, () -> ItemStack.EMPTY);
		return this;
	}

	public CItemBuilder<I> setTab(ResourceKey<CreativeModeTab> tab) {
		setTabBefore(tab, () -> ItemStack.EMPTY);
		return this;
	}

	//Empty | i i i i | Empty
	public CItemBuilder<I> setTabBefore(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> before) {
		ex_tabs.put(tab, Either.left(before));
		return this;
	}

	public CItemBuilder<I> setTabAfter(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> after) {
		ex_tabs.put(tab, Either.right(after));
		return this;
	}


	public ItemEntry<I> save() {
		ItemEntry<I> entry = register(name, () -> factory.apply(properties));
		assertTrue(CItems.ITEMS.put(entry.getID(), entry) == null, "Duplicate name: " + name);

		if (ctab) CCTab.add(entry);
		ex_tabs.forEach((k, pos) -> registerTabEntry(Pair.of(k, entry::getDefaultInstance), pos));
		return entry;
	}


	public static <I extends Item> ItemEntry<I> register(String name, Supplier<I> sup) {
		return register(prefix(name), sup);
	}

	/**
	 * @deprecated 在forge环境中若name的命名空间非 {@link CandyCraftCE#MOD_ID} 则会报错
	 */
	@Deprecated
	@ExpectPlatform
	private static <I extends Item> ItemEntry<I> register(ResourceLocation name, Supplier<I> sup) {
		throw new AssertionError();
	}

	/**
	 * @param tab 目标Tab,此ItemStack
	 * @param pos 位置,Left为前,Right为后。{@link ItemStack#EMPTY}为边界,Right即开头，Left即末尾
	 */
	@ExpectPlatform
	public static void registerTabEntry(Pair<ResourceKey<CreativeModeTab>, Supplier<ItemStack>> tab, Either<Supplier<ItemStack>, Supplier<ItemStack>> pos) {
		throw new AssertionError();
	}

}
