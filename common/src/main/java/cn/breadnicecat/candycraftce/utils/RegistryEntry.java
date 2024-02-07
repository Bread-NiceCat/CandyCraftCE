package cn.breadnicecat.candycraftce.utils;

import cn.breadnicecat.candycraftce.item.ItemEntry;
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
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		ItemEntry<?> that = (ItemEntry<?>) obj;
		return Objects.equals(this.id, that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
