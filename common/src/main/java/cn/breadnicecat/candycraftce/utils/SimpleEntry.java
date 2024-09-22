package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.Holder;

import java.util.function.Supplier;

/**
 * Created in 2024/3/24 9:42
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SimpleEntry<R, T extends R> extends RegistryEntry<R> implements Supplier<T>, HolderSupplier<R> {
	private final WrappedEntry<R, T> wrapper;
	
	
	public SimpleEntry(WrappedEntry<R, T> wrapper) {
		super(wrapper.getKey());
		this.wrapper = wrapper;
	}
	
	@Override
	public T get() {
		return wrapper.get();
	}
	
	public Holder<R> getHolder() {
		return wrapper.getHolder();
	}
	
}
