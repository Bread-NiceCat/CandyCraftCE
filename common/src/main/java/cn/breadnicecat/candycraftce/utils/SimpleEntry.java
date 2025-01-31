package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
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
public class SimpleEntry<BASE, E extends BASE> extends RegistryEntry<BASE> implements Supplier<E>, HolderSupplier<BASE> {
	private final ResourceKey<BASE> key;
	private final Supplier<E> sup;
	private final Holder<BASE> holder;
	
	//wrapper
	public SimpleEntry(SimpleEntry<BASE, E> other) {
		this(other.key, other.sup, other.holder);
	}
	
	public SimpleEntry(ResourceKey<BASE> key, Supplier<E> sup, Holder<BASE> holder) {
		super(key);
		this.key = Objects.requireNonNull(key, "key");
		this.sup = Objects.requireNonNull(sup, "sup");
		this.holder = Objects.requireNonNull(holder, "holder");
	}
	
	@Override
	public E get() {
		return sup.get();
	}
	
	
	public boolean isPresent() {
		return holder.isBound();
	}
	
	public void ifPresent(Consumer<E> e) {
		if (isPresent()) e.accept(get());
	}
	
	public Optional<E> optional() {
		return isPresent() ? Optional.of(get()) : Optional.empty();
	}
	
	public ResourceKey<BASE> getKey() {
		return key;
	}
	
	public Holder<BASE> getHolder() {
		return holder;
	}
	
	public <T> T as(Function<SimpleEntry<BASE, E>, T> factory) {
		return factory.apply(this);
	}
}
