package cn.breadnicecat.candycraftce.utils;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

/**
 * Created in 2024/3/24 9:42
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SimpleEntry<R, T extends R> extends RegistryEntry<R> implements Supplier<T> {
	private final Supplier<T> getter;
	
	public SimpleEntry(ResourceKey<R> key, Supplier<T> getter) {
		super(key);
		this.getter = getter;
	}
	
	public SimpleEntry(Pair<ResourceKey<R>, Supplier<T>> wrapper) {
		this(wrapper.getFirst(), wrapper.getSecond());
	}
	
	@Override
	public T get() {
		return getter.get();
	}
}
