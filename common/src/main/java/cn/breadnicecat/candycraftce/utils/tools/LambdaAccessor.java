package cn.breadnicecat.candycraftce.utils.tools;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/24 20:59
 */
public class LambdaAccessor<T> implements Accessor<T> {

	private final Supplier<T> getter;
	private final Consumer<T> setter;

	public LambdaAccessor(Supplier<T> getter, Consumer<T> setter) {
		this.getter = getter;
		this.setter = setter;
	}

	public static <T> LambdaAccessor<T> of(Supplier<T> getter, Consumer<T> setter) {
		return new LambdaAccessor<>(getter, setter);
	}


	@Override
	public T get() {
		return getter.get();
	}

	@Override
	public void accept(T t) {
		setter.accept(t);
	}
}
