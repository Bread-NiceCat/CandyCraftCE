package cn.breadnicecat.candycraftce.utils.tools;

/**
 * Created in 2023/6/7 11:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class AccessorImpl<T> implements Accessor<T> {
	T value;
	
	public AccessorImpl() {
	
	}
	
	public AccessorImpl(T value) {
		this.value = value;
	}
	
	@Override
	public T get() {
		return value;
	}
	
	@Override
	public void set(T t) {
		value = t;
	}
}
