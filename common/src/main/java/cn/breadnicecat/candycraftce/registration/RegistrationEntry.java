package cn.breadnicecat.candycraftce.registration;

import cn.breadnicecat.candycraftce.registration.item.ItemEntry;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Created in 2023/8/24 0:29
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class RegistrationEntry {
	protected final ResourceLocation id;

	public RegistrationEntry(ResourceLocation id) {
		this.id = Objects.requireNonNull(id);
	}

	public ResourceLocation getID() {
		return id;
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
