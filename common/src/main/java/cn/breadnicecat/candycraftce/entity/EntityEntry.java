package cn.breadnicecat.candycraftce.entity;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;
import org.jetbrains.annotations.Nullable;

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
	
	public EntityEntry(Class<T> clazz, SimpleEntry<EntityType<?>, EntityType<T>> wrapper) {
		super(wrapper);
		this.clazz = clazz;
		
	}
	
	public @Nullable SpawnEggItem getEggItem() {
		return SpawnEggItem.byId(get());
	}
	
	public boolean isLivingEntity() {
		return is(LivingEntity.class);
	}
	
	public boolean isMob() {
		return is(Mob.class);
	}
	
	public boolean is(Class<? extends Entity> e) {
		return e.isAssignableFrom(clazz);
	}
}
