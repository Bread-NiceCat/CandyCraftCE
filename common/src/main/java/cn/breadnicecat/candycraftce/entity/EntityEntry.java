package cn.breadnicecat.candycraftce.entity;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

/**
 * Created in 2024/2/25 8:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class EntityEntry<T extends Entity> extends SimpleEntry<EntityType<?>, EntityType<T>> implements Supplier<EntityType<T>> {
	public final Class<T> clazz;
	
	public EntityEntry(ResourceKey<EntityType<?>> key, Class<T> clazz, Supplier<EntityType<T>> getter) {
		super(key, getter);
		this.clazz = clazz;
	}
	
	public EntityEntry(Class<T> clazz, Pair<ResourceKey<EntityType<?>>, Supplier<EntityType<T>>> wrapper) {
		super(wrapper);
		this.clazz = clazz;
		
	}
	
	public SpawnEggItem getEggItem() {
		return SpawnEggItem.byId(get());
	}
	
	public boolean isLivingEntity() {
		return LivingEntity.class.isAssignableFrom(clazz);
	}
}
