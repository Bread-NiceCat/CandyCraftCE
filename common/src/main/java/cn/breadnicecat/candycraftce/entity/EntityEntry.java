//package cn.breadnicecat.candycraftce.registration.entity;
//
//import cn.breadnicecat.candycraftce.registration.RegistrationEntry;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//
//import java.util.Objects;
//
///**
// * Created in 2023/8/24 9:10
// * Project: candycraftce
// *
// * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
// */
//public class EntityEntry<T extends Entity> extends RegistrationEntry {
//	private final EntityType<T> entity;
//
//	public EntityEntry(ResourceLocation name, EntityType<T> entity) {
//		super(name);
//		this.entity = Objects.requireNonNull(entity);
//	}
//
//	public EntityType<T> getEntity() {
//		return entity;
//	}
//}
