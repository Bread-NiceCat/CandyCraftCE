package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created in 2024/9/22 03:44
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class SimpleEntry<R, E extends R> extends RegistryEntry<R> implements Supplier<E>, HolderSupplier<R> {
	private final ResourceKey<R> key;
	private final Supplier<E> sup;
	private final Holder<R> holder;
	
	public SimpleEntry(SimpleEntry<R, E> other) {
		this(other.key, other.sup, other.holder);
	}
	
	public SimpleEntry(ResourceKey<R> key, Supplier<E> sup, Holder<R> holder) {
		super(key);
		this.key = key;
		this.sup = sup;
		this.holder = holder;
	}
	
	public static <S extends R, R> SimpleEntry<R, S> of(ResourceKey<R> k, Supplier<S> supplier, Holder<R> holder) {
		return new SimpleEntry<>(k, supplier, holder);
	}
	
	@Override
	public E get() {
		return sup.get();
	}
	
	public ResourceKey<R> getKey() {
		return key;
	}
	
	public Holder<R> getHolder() {
		return holder;
	}
	
	public <T> T as(Function<SimpleEntry<R, E>, T> factory) {
		return factory.apply(this);
	}
}
