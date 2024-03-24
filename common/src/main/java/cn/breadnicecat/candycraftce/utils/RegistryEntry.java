package cn.breadnicecat.candycraftce.utils;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Created in 2023/8/24 0:29
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class RegistryEntry {
	protected final ResourceLocation id;

	public RegistryEntry(ResourceLocation id) {
		this.id = Objects.requireNonNull(id);
	}

	public ResourceLocation getID() {
		return id;
	}

	public String getName() {
		return id.getPath();
	}

	@Override
	public String toString() {
		return id.toString();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
