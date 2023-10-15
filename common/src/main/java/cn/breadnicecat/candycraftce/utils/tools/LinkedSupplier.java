package cn.breadnicecat.candycraftce.utils.tools;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created in 2023/6/29 10:54
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
@FunctionalInterface
public interface LinkedSupplier<T> extends Supplier<T> {
	/**
	 * get后执行
	 */
	default LinkedSupplier<T> thenAccept(Consumer<T> consumer) {
		return () -> {
			T t = get();
			consumer.accept(t);
			return t;
		};
	}

	/**
	 * get前执行
	 */
	default LinkedSupplier<T> beforeSupply(Runnable pre) {
		return () -> {
			pre.run();
			return get();
		};
	}

	/**
	 * get后执行
	 *
	 * @param handler 返回值作为新的返回值
	 */
	default LinkedSupplier<T> handle(Function<T, T> handler) {
		return () -> handler.apply(get());
	}

	/**
	 * 在当前Supplier基础上加上一层try-catch
	 *
	 * @param errorHandler 返回值作为新的返回值
	 */
	default LinkedSupplier<T> onException(Function<Throwable, T> errorHandler) {
		return () -> {
			try {
				return get();
			} catch (Throwable a) {
				return errorHandler.apply(a);
			}
		};
	}

	static <T> LinkedSupplier<T> of(Supplier<T> t) {
		if (t instanceof LinkedSupplier<T> lt) return lt;
		return t::get;
	}
}
