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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.hookPostBootstrap;
import static cn.breadnicecat.candycraftce.CandyCraftCE.isInDev;
import static cn.breadnicecat.candycraftce.registration.item.CItemBuilder.Factory.byFunc;
import static cn.breadnicecat.candycraftce.registration.item.CItemBuilder.Factory.bySupply;
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
		if (!isInDev()) CandyCraftCE.hookPostBootstrap(() -> BY_PRODUCTS = null);//清理内存占用
	}

	private static HashMap<ItemEntry<?>, CItemBuilder<?>> BY_PRODUCTS = new HashMap<>();

	private boolean isFrozen = CandyCraftCE.isBootstrapped();
	private final String name;
//	/**
//	 * 副本会对{@link #)等修饰方法做限制
//	 */
//	private boolean isCopy=false;

	//以下是可以被复制的参数
	private Factory<I> factory;
	public Properties properties = new Properties();
	public Object[] param = new Object[0];
	/**
	 * 默认处于CTab中，参见{@link #setCtab}
	 */
	public boolean ctab = true;
	//可复制 END

	/**
	 * 用法@see #registerTabEntry(Pair, Either)
	 */
	public Map<ResourceKey<CreativeModeTab>, Either<Supplier<ItemStack>, Supplier<ItemStack>>> ex_tabs = new HashMap<>();

	/**
	 * 通过ItemEntry复制Builder
	 */
	@SuppressWarnings("unchecked")
	public static <I extends Item> CItemBuilder<I> create(String name, ItemEntry<I> copyFrom) {
		return create(name, (CItemBuilder<I>) BY_PRODUCTS.get(copyFrom));
	}

	/**
	 * 从另一个builder复制参数
	 */
	public static <I extends Item> CItemBuilder<I> create(String name, CItemBuilder<I> that) {
		CItemBuilder<I> builder = new CItemBuilder<>(name, that.factory);
		builder.properties = that.properties;
		builder.param = Arrays.copyOf(that.param, that.param.length);
		return builder;
	}

	public static <I extends Item> CItemBuilder<I> create(String name, Function<Properties, I> factory) {
		return new CItemBuilder<>(name, byFunc(factory));
	}

	public static <I extends Item> CItemBuilder<I> create(String name, Supplier<I> fac) {
		return new CItemBuilder<>(name, bySupply(fac));
	}

	public static CItemBuilder<Item> create(String name) {
		return create(name, Item::new);
	}

	public static <I extends Item> CItemBuilder<I> create(String name, Factory<I> factory) {
		return new CItemBuilder<>(name, factory);
	}

	protected CItemBuilder(String name, Factory<I> factory) {
		checkState();
		this.name = name;
		this.factory = factory;
		//防止有未save的builder
		hookPostBootstrap(() -> {
			if (!isFrozen) throw new IllegalStateException("unfrozen builder, haven't been save?");
		});
	}

	public static CItemBuilder<BlockItem> block(BlockEntry<Block> block) {
		String namespace = block.getID().getNamespace();
		assertTrue(namespace.equals(CandyCraftCE.MOD_ID), () -> "wrong namespace, require equ " + CandyCraftCE.MOD_ID);
		return create(namespace, (p) -> new BlockItem(block.getBlock(), p));
	}

	public CItemBuilder<I> resetFactory(@NotNull BiFunction<Properties, Object[], I> of) {
		checkState();
		this.factory = Factory.byBiFunc(of);
		return this;
	}

	public CItemBuilder<I> resetFactory(@NotNull Function<Properties, I> of) {
		checkState();
		this.factory = Factory.byFunc(of);
		return this;
	}

	public CItemBuilder<I> resetFactory(@NotNull Supplier<I> of) {
		checkState();
		this.factory = Factory.bySupply(of);
		return this;
	}

	/**
	 * 新的Properties
	 */
	public CItemBuilder<I> setProperties(@NotNull Properties prop) {
		checkState();
		checkIsSupportProperties();
		this.properties = Objects.requireNonNull(prop);
		return this;
	}

	private void checkIsSupportProperties() {
		if (!factory.isSupportProp())
			throw new UnsupportedOperationException("Unable to access properties. Try to upgrade `factory` level");
	}


	/**
	 * @param food 调用properties#food
	 * @see Properties#food(FoodProperties)
	 */
	public CItemBuilder<I> setFood(FoodProperties food) {
		checkState();
		checkIsSupportProperties();
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
		return setFood(new FoodProperties.Builder()
				.nutrition(nutrition).saturationMod(saturationModifier).build());
	}

	public CItemBuilder<I> modifyParameters(@NotNull Function<Object[], Object[]> paramFunc) {
		checkState();
		checkIsSupportParam();
		assertTrue(this.param.length != 0, "empty!");
		setParameters(paramFunc.apply(this.param));
		return this;
	}

	public CItemBuilder<I> setParameters(Object... param) {
		checkState();
		checkIsSupportParam();
		this.param = param;
		return this;
	}

	private void checkIsSupportParam() {
		if (!factory.isSupportParam())
			throw new UnsupportedOperationException("Unable to access parameters. Try to upgrade `factory` level");
	}

	public CItemBuilder<I> setCtab(boolean ctab) {
		checkState();
		this.ctab = ctab;
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

	public interface Factory<I extends Item> extends BiFunction<Properties, Object[], I> {
		static <I extends Item> Factory<I> bySupply(Supplier<I> supplier) {
			Objects.requireNonNull(supplier);
			return (SupplierFactory<I>) (p, a) -> supplier.get();
		}

		static <I extends Item> Factory<I> byFunc(Function<Properties, I> func) {
			Objects.requireNonNull(func);
			return (WithProperties<I>) (p, a) -> func.apply(p);
		}

		static <I extends Item> Factory<I> byBiFunc(BiFunction<Properties, Object[], I> bi) {
			Objects.requireNonNull(bi);
			return bi::apply;
		}

		default boolean isSupportProp() {
			return true;
		}

		default boolean isSupportParam() {
			return true;
		}

	}

	interface WithProperties<I extends Item> extends Factory<I> {
		@Override
		default boolean isSupportParam() {
			return false;
		}
	}

	interface SupplierFactory<I extends Item> extends WithProperties<I> {
		@Override
		default boolean isSupportProp() {
			return false;
		}
	}
}
