package cn.breadnicecat.candycraftce.registration.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.IS_DEV;
import static cn.breadnicecat.candycraftce.CandyCraftCE.hookPostBootstrap;
import static cn.breadnicecat.candycraftce.registration.block.CBlockBuilder.Factory.byFunc;
import static cn.breadnicecat.candycraftce.registration.block.CBlockBuilder.Factory.bySupply;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;


/**
 * Created in 2023/11/26 9:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * forked from CBlockBuilder =)
 */

public class CBlockBuilder<B extends Block> {

	static {
		if (!IS_DEV) CandyCraftCE.hookPostBootstrap(() -> BY_PRODUCTS = null);//清理内存占用
	}

	private static HashMap<BlockEntry<?>, CBlockBuilder<?>> BY_PRODUCTS = new HashMap<>();

	private boolean isFrozen = CandyCraftCE.isBootstrapped();
	private final String name;

	//以下是可以被复制的参数
	private CBlockBuilder.Factory<B> factory;
	public Properties properties = Properties.of();
	public Object[] param = new Object[0];
	//可复制 END

	/**
	 * 通过BlockEntry复制Builder
	 */
	@SuppressWarnings("unchecked")
	public static <B extends Block> CBlockBuilder<B> create(String name, BlockEntry<B> copyFrom) {
		return create(name, (CBlockBuilder<B>) BY_PRODUCTS.get(copyFrom));
	}

	/**
	 * 从另一个builder复制参数
	 */
	public static <B extends Block> CBlockBuilder<B> create(String name, CBlockBuilder<B> that) {
		CBlockBuilder<B> builder = new CBlockBuilder<>(name, that.factory);
		builder.properties = that.properties;
		builder.param = Arrays.copyOf(that.param, that.param.length);
		return builder;
	}

	public static <I extends Block> CBlockBuilder<I> create(String name, Function<Properties, I> factory) {
		return new CBlockBuilder<>(name, byFunc(factory));
	}

	public static <I extends Block> CBlockBuilder<I> create(String name, Supplier<I> fac) {
		return new CBlockBuilder<>(name, bySupply(fac));
	}

	public static CBlockBuilder<Block> create(String name) {
		return create(name, Block::new);
	}

	public static <I extends Block> CBlockBuilder<I> create(String name, CBlockBuilder.Factory<I> factory) {
		return new CBlockBuilder<>(name, factory);
	}

	protected CBlockBuilder(String name, CBlockBuilder.Factory<B> factory) {
		checkState();
		this.name = name;
		this.factory = factory;
		//防止有未save的builder
		hookPostBootstrap(() -> {
			if (!isFrozen) throw new IllegalStateException("unfrozen builder, haven't been save?");
		});
	}


	public CBlockBuilder<B> resetFactory(@NotNull BiFunction<Properties, Object[], B> of) {
		checkState();
		this.factory = CBlockBuilder.Factory.byBiFunc(of);
		return this;
	}

	public CBlockBuilder<B> resetFactory(@NotNull Function<Properties, B> of) {
		checkState();
		this.factory = CBlockBuilder.Factory.byFunc(of);
		return this;
	}

	public CBlockBuilder<B> resetFactory(@NotNull Supplier<B> of) {
		checkState();
		this.factory = CBlockBuilder.Factory.bySupply(of);
		return this;
	}

	/**
	 * 新的Properties
	 */
	public CBlockBuilder<B> setProperties(@NotNull Properties prop) {
		checkState();
		checkIsSupportProperties();
		this.properties = Objects.requireNonNull(prop);
		return this;
	}

	public CBlockBuilder<B> copyProperties(@NotNull Block copy) {
		checkState();
		checkIsSupportProperties();
		this.properties = Properties.copy(copy);
		return this;
	}

	private void checkIsSupportProperties() {
		if (!factory.isSupportProp())
			throw new UnsupportedOperationException("Unable to access properties. Try to upgrade `factory` level");
	}

	public CBlockBuilder<B> modifyParameters(@NotNull Function<Object[], Object[]> paramFunc) {
		checkState();
		checkIsSupportParam();
		assertTrue(this.param.length != 0, "empty!");
		setParameters(paramFunc.apply(this.param));
		return this;
	}

	public CBlockBuilder<B> setParameters(Object... param) {
		checkState();
		checkIsSupportParam();
		this.param = param;
		return this;
	}

	private void checkIsSupportParam() {
		if (!factory.isSupportParam())
			throw new UnsupportedOperationException("Unable to access parameters. Try to upgrade `factory` level");
	}

	public BlockEntry<B> save() {
		checkState();
		isFrozen = true;

		BlockEntry<B> entry = register(name, () -> factory.apply(properties, param));
		assertTrue(CBlocks.BLOCKS.put(entry.getID(), entry) == null, "Duplicate name: " + name);
		BY_PRODUCTS.put(entry, this);
		return entry;
	}


	private void checkState() {
		assertTrue(!isFrozen, "Frozen Builder");
	}

	public static <I extends Block> BlockEntry<I> register(String name, Supplier<I> sup) {
		return register(prefix(name), sup);
	}

	/**
	 * @deprecated 在forge环境中若name的命名空间非 {@link CandyCraftCE#MOD_ID} 则会报错
	 */
	@Deprecated
	@ExpectPlatform
	private static <I extends Block> BlockEntry<I> register(ResourceLocation name, Supplier<I> sup) {
		throw new AssertionError();
	}

	public interface Factory<I extends Block> extends BiFunction<Properties, Object[], I> {
		static <I extends Block> CBlockBuilder.Factory<I> bySupply(Supplier<I> supplier) {
			Objects.requireNonNull(supplier);
			return (CBlockBuilder.SupplierFactory<I>) (p, a) -> supplier.get();
		}

		static <I extends Block> CBlockBuilder.Factory<I> byFunc(Function<Properties, I> func) {
			Objects.requireNonNull(func);
			return (CBlockBuilder.WithProperties<I>) (p, a) -> func.apply(p);
		}

		static <I extends Block> CBlockBuilder.Factory<I> byBiFunc(BiFunction<Properties, Object[], I> bi) {
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

	interface WithProperties<I extends Block> extends CBlockBuilder.Factory<I> {
		@Override
		default boolean isSupportParam() {
			return false;
		}
	}

	interface SupplierFactory<I extends Block> extends CBlockBuilder.WithProperties<I> {
		@Override
		default boolean isSupportProp() {
			return false;
		}
	}

}
