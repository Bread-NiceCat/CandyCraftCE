package cn.breadnicecat.candycraftce.utils.tools;

/**
 * Created in 2023/6/7 11:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class AccessorImpl<T> implements Accessor<T> {
	T value;

	@Override
	public T get() {
		return value;
	}

	@Override
	public void set(T t) {
		value = t;
	}
}
