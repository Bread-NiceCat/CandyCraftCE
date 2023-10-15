package cn.breadnicecat.candycraftce.registration.entity.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Supplier;

/**
 * Created in 2023/8/24 17:38
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class CEntitiesCEntityBuilderImpl {

	@Environment(EnvType.CLIENT)
	public static <E extends Entity> void registerRenderer(EntityType<? extends E> type, EntityRendererProvider<E> factory) {
		EntityRendererRegistry.register(type, factory);
	}


	public static void registerAttribute(EntityType<? extends LivingEntity> type, Supplier<AttributeSupplier.Builder> attr) {
		FabricDefaultAttributeRegistry.register(type, attr.get());
	}

	@Environment(EnvType.CLIENT)
	public static void registerLayer(ModelLayerLocation loc, Supplier<LayerDefinition> definition) {
		EntityModelLayerRegistry.registerModelLayer(loc, definition::get);
	}
}
