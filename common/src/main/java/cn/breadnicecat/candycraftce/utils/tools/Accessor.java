package cn.breadnicecat.candycraftce.utils.tools;

import java.util.function.Supplier;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/22 13:21
 * <p>
 * Wisdom Util ;)
 */
public interface Accessor<T> extends Setter<T>, Getter<T> {
	default Supplier<T> frozen() {
		return new Frozen<>(this);
	}

	class Frozen<T> implements Accessor<T> {
		private final Accessor<T> su;

		public Frozen(Accessor<T> su) {
			this.su = su;
		}

		@Override
		public T get() {
			return su.get();
		}

		@Override
		public void set(T t) {
			throw new UnsupportedOperationException("frozen!");
		}

		@Override
		public Accessor<T> frozen() {
			return this;
		}
	}
}
