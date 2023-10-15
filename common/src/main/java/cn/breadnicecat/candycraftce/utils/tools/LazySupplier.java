package cn.breadnicecat.candycraftce.utils.tools;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/19 19:30
 */
public class LazySupplier<T> implements Supplier<T> {
	private T value;
	private final Supplier<T> sup;

	public LazySupplier(@NotNull Supplier<T> supplier) {
		this.sup = Objects.requireNonNull(supplier);
	}

	public static <T> LazySupplier<T> of(Supplier<T> t) {
		return t instanceof LazySupplier<T> lz ? lz : new LazySupplier<>(t);
	}

	public static <T> ConLazySupplier<T> ofConcurrent(Supplier<T> t) {
		return t instanceof ConLazySupplier<T> clz ? clz : new ConLazySupplier<>(t);
	}

	@Override
	public T get() {
		if (value == null) {
			T t = sup.get();
			value = t;
			return t;
		} else {
			return value;
		}
	}

	public static class ConLazySupplier<T> extends LazySupplier<T> {

		public ConLazySupplier(@NotNull Supplier<T> supplier) {
			super(supplier);
		}

		@Override
		public synchronized T get() {
			return super.get();
		}
	}
}
