package cn.breadnicecat.candycraftce.entity;

import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

/**
 * Created in 2024/2/25 8:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public abstract class EntityEntry<T extends Entity> extends RegistryEntry implements Supplier<EntityType<T>> {
	public EntityEntry(ResourceLocation id) {
		super(id);
	}
}
