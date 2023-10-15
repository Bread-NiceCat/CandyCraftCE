package cn.breadnicecat.candycraftce.registration.entity.forge;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created in 2023/8/24 9:55
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CEntitiesCEntityBuilderImpl {
	private static final HashMap<EntityType<? extends LivingEntity>, Supplier<AttributeSupplier.Builder>> attrs = new HashMap<>();

	public static void registerAttribute(EntityType<? extends LivingEntity> type, Supplier<AttributeSupplier.Builder> attr) {
		attrs.put(type, attr);
	}

	@SubscribeEvent
	public static void onCreateAttr(EntityAttributeCreationEvent event) {
		attrs.forEach((k, a) -> event.put(k, a.get().build()));
	}

	@OnlyIn(Dist.CLIENT)
	public static <E extends Entity> void registerRenderer(EntityType<? extends E> type, EntityRendererProvider<E> factory) {
		EntityRenderers.register(type, factory);
	}

	private static final HashMap<ModelLayerLocation, Supplier<LayerDefinition>> layers = new HashMap<>();

	@SubscribeEvent
	public static void onCreateLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
		layers.forEach(event::registerLayerDefinition);
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerLayer(ModelLayerLocation loc, Supplier<LayerDefinition> definition) {
		layers.put(loc, definition);
	}
}
