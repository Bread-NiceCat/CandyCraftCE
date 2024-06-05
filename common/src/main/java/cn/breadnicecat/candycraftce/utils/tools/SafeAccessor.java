package cn.breadnicecat.candycraftce.utils.tools;

/**
 * Created in 2024/2/18 17:05
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * 线程安全
 */
public class SafeAccessor<T> extends AccessorImpl<T> {
	public SafeAccessor() {
	}
	
	public SafeAccessor(T value) {
		super(value);
	}
	
	@Override
	public synchronized void set(T t) {
		super.set(t);
	}
	
	@Override
	public synchronized T get() {
		return super.get();
	}
}
