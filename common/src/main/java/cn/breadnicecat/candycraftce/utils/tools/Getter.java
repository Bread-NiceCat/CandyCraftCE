package cn.breadnicecat.candycraftce.utils.tools;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/22 13:15
 */
public interface Getter<T> extends Supplier<T> {
	T get();
	
	default boolean isPresent() {
		throw new UnsupportedOperationException();
	}
	
	default void ifPresent(Consumer<T> t) {
		if (isPresent()) t.accept(get());
	}
}
