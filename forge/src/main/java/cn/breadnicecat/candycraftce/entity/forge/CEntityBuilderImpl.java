package cn.breadnicecat.candycraftce.entity.forge;

import cn.breadnicecat.candycraftce.entity.EntityEntry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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
