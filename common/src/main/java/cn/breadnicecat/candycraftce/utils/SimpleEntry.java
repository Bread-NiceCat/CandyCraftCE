package cn.breadnicecat.candycraftce.utils;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * Created in 2024/3/24 9:42
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SimpleEntry<T> extends RegistryEntry implements Supplier<T> {
	private final Supplier<T> getter;

	public SimpleEntry(ResourceLocation id, Supplier<T> getter) {
		super(id);
		this.getter = getter;
	}

	@Override
	public T get() {
		return getter.get();
	}
}
