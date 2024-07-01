package cn.breadnicecat.candycraftce.entity.fabric;

import cn.breadnicecat.candycraftce.entity.EntityEntry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

/**
 * Created in 2024/7/1 21:17
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CEntityBuilderImpl {
	public static void registerAttribute(EntityEntry<LivingEntity> entity, Supplier<AttributeSupplier.Builder> attr) {
		FabricDefaultAttributeRegistry.register(entity.get(), attr.get());
	}
	
}
