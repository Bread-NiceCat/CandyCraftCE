package cn.breadnicecat.candycraftce.entity.neoforge;

import cn.breadnicecat.candycraftce.entity.EntityEntry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.HashMap;
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
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CEntityBuilderImpl {
	private static final HashMap<EntityEntry<LivingEntity>, Supplier<AttributeSupplier.Builder>> attrs = new HashMap<>();
	
	public static void registerAttribute(EntityEntry<LivingEntity> entity, Supplier<AttributeSupplier.Builder> attr) {
		attrs.put(entity, attr);
	}
	
	@SubscribeEvent
	public static void onAttributeCreate(EntityAttributeCreationEvent event) {
		attrs.forEach((sup, att) -> event.put(sup.get(), att.get().build()));
	}
}
