package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.devImmune;
import static cn.breadnicecat.candycraftce.CandyCraftCE.hookPostBootstrap;
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
	static {
		CandyCraftCE.hookPostBootstrap(devImmune(() -> BY_PRODUCTS = null));//清理内存占用
	}

	private static HashMap<ItemEntry<?>, CItemBuilder<?>> BY_PRODUCTS = new HashMap<>();

	private boolean isFrozen = CandyCraftCE.isBootstrapped();
	private final String name;
	private BiFunction<Item.Properties, Object[], I> factory;

	public Item.Properties properties = new Item.Properties();
	public Object[] param = new Object[0];
	public boolean ctab;


	/**
	 * @see #registerTabEntry(Pair, Either)
	 */
	public Map<ResourceKey<CreativeModeTab>, Either<Supplier<ItemStack>, Supplier<ItemStack>>> ex_tabs = new HashMap<>();

	/**
	 * 通过ItemEntry复制Builder
	 */
	@SuppressWarnings("unchecked")
	public CItemBuilder(String name, ItemEntry<I> copyFrom) {
		this(name, (CItemBuilder<I>) BY_PRODUCTS.get(copyFrom));
	}

	/**
	 * 从另一个builder复制参数
	 */
	public CItemBuilder(String name, CItemBuilder<I> that) {
		this(name, that.factory);
		this.properties = that.properties;
		this.param = that.param;
		this.ctab = that.ctab;

	}

	public CItemBuilder(String name, Function<Item.Properties, I> factory) {
		this(name, lower(factory));
	}

	public CItemBuilder(String name, Supplier<I> fac) {
		this(name, higher(fac));
	}

	/**
	 * 默认处于CTab中，参见{@link #setCtab}
	 *
	 * @param factory 带参的factory
	 * @apiNote 所有的构造都要委托给此
	 */
	public CItemBuilder(String name, BiFunction<Item.Properties, Object[], I> factory) {
		checkState();
		this.name = name;
		this.factory = factory;
		//防止有未save的builder
		hookPostBootstrap(() -> {
			if (!isFrozen) throw new IllegalStateException("unfrozen builder");
		});
		setCtab();
	}


	public CItemBuilder<I> setFactory(@NotNull BiFunction<Item.Properties, Object[], I> of) {
		checkState();
		this.factory = Objects.requireNonNull(of);
		return this;
	}

	public CItemBuilder<I> setFactory(@NotNull Function<Item.Properties, I> of) {
		checkState();
		setFactory(lower(of));
		return this;
	}

	public CItemBuilder<I> setFactory(@NotNull Supplier<I> of) {
		checkState();
		setFactory(higher(of));
		return this;
	}

	/**
	 * 新的Properties
	 */
	public CItemBuilder<I> setProperties(@NotNull Item.Properties prop) {
		checkState();
		this.properties = Objects.requireNonNull(prop);
		return this;
	}

	/**
	 * 对默认的进行修饰
	 */
	public CItemBuilder<I> setProperties(Function<Item.Properties, Item.Properties> prop) {
		checkState();
		setProperties(prop.apply(this.properties));
		return this;
	}

	/**
	 * @param food 调用properties#food
	 * @see net.minecraft.world.item.Item.Properties#food(FoodProperties)
	 */
	public CItemBuilder<I> setFood(FoodProperties food) {
		checkState();
		properties.food(food);
		return this;
	}

	/**
	 * nutrition          饱食度
	 * <p>
	 * saturation         饱和度 # 饱和度=2*饱食度*饱和度修饰符,这里已经进行转化
	 *
	 * @see FoodData#eat(int, float)
	 */
	public CItemBuilder<I> setFood(int nutrition, int saturation) {
		checkState();
		float saturationModifier = saturation / 2f / nutrition;
		return setFood(new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationModifier).build());
	}

	public CItemBuilder<I> setParameters(Object... param) {
		checkState();
		this.param = param;
		return this;
	}

	public CItemBuilder<I> setCtab(boolean ctab) {
		checkState();
		this.ctab = ctab;
		return this;
	}

	public CItemBuilder<I> setCtab() {
		checkState();
		setCtab(true);
		return this;
	}

	/**
	 * @param tab 在此tab中的最前面
	 */
	public CItemBuilder<I> setTabPrepend(ResourceKey<CreativeModeTab> tab) {
		checkState();
		setTabAfter(tab, () -> ItemStack.EMPTY);
		return this;
	}

	public CItemBuilder<I> setTab(ResourceKey<CreativeModeTab> tab) {
		checkState();
		setTabBefore(tab, () -> ItemStack.EMPTY);
		return this;
	}

	//Empty | i i i i | Empty

	public CItemBuilder<I> setTabBefore(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> before) {
		checkState();
		ex_tabs.put(tab, Either.left(before));
		return this;
	}

	public CItemBuilder<I> setTabAfter(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> after) {
		checkState();
		ex_tabs.put(tab, Either.right(after));
		return this;
	}


	public ItemEntry<I> save() {
		checkState();
		isFrozen = true;

		ItemEntry<I> entry = register(name, () -> factory.apply(properties, param));
		assertTrue(CItems.ITEMS.put(entry.getID(), entry) == null, "Duplicate name: " + name);
		BY_PRODUCTS.put(entry, this);

		if (ctab) CCTab.add(entry);
		ex_tabs.forEach((k, pos) -> registerTabEntry(Pair.of(k, entry::getDefaultInstance), pos));
		return entry;
	}


	private void checkState() {
		assertTrue(!isFrozen, "Frozen Builder");
	}

	public static <I> BiFunction<Item.Properties, Object[], I> lower(Function<Item.Properties, I> fac) {
		Objects.requireNonNull(fac);
		return (f, s) -> fac.apply(f);
	}

	public static <I extends Item> Function<Item.Properties, I> higher(Supplier<I> met) {
		Objects.requireNonNull(met);
		return (ignore) -> met.get();
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
