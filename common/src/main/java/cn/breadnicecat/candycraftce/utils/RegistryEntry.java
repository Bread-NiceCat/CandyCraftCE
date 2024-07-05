package cn.breadnicecat.candycraftce.utils;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Created in 2023/8/24 0:29
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public abstract class RegistryEntry<R> {
	protected final ResourceKey<R> key;
	
	public RegistryEntry(ResourceKey<R> key) {
		this.key = Objects.requireNonNull(key);
	}
	
	public ResourceKey<R> getKey() {
		return key;
	}
	
	public ResourceLocation getId() {
		return key.location();
	}
	
	public String getName() {
		return getId().getPath();
	}
	
	@Override
	public String toString() {
		return key.toString();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(key.registry(), key.location());
	}
}
