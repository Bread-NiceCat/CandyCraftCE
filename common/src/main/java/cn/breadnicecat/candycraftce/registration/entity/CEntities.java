package cn.breadnicecat.candycraftce.registration.entity;

import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created in 2023/8/23 23:46
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class CEntities {

//	public static final HashMap<ResourceLocation, EntityEntry<?>> ENTITIES = new HashMap<>();
//	@Environment(EnvType.CLIENT)
//	public static final HashMap<ResourceLocation, HashMap<String, ModelAnimation>> ANIMS = new HashMap<>();

/*	public static final EntityEntry<EntityMonarchMecha> MONARCH_MECHA = CEntityBuilder.of("monarch_mecha",
					EntityType.Builder.of(EntityMonarchMecha::new, MobCategory.CREATURE)
							.sized(3.6f, 12.5f))
			.attribute(EntityMonarchMecha::createAttribute)
//			.renderer(() -> RendererMonarchMecha::new)
//			.layer(() -> () -> RendererMonarchMecha.MAIN_LAYER, () -> ModelMonarchMecha::createMainModel)
			.save();*/

	public static class CEntityBuilder<T extends Entity> {

		private final ResourceLocation name;
		private final EntityType.Builder<T> builder;
		private final LinkedList<Consumer<EntityType<T>>> cons = new LinkedList<>();

		private CEntityBuilder(@NotNull String name, @NotNull EntityType.Builder<T> builder) {
			this.name = ResourceUtils.prefix(name);
			this.builder = Objects.requireNonNull(builder);
		}

		public static <T extends Entity> CEntityBuilder<T> of(String name, EntityType.Builder<T> builder) {
			return new CEntityBuilder<>(name, builder);
		}

		/**
		 * 在注册后调用
		 */
		public CEntityBuilder<T> accept(Consumer<EntityType<T>> consumer) {
			cons.add(consumer);
			return this;
		}

		/**
		 * 只有LivingEntity需要属性
		 */
		@SuppressWarnings("unchecked")
		public CEntityBuilder<T> attribute(Supplier<AttributeSupplier.Builder> attr) {
			accept(t -> registerAttribute((EntityType<? extends LivingEntity>) t, attr));
			return this;
		}
//		/*
//		 * CLIENT ONLY
//		 * 但是可以在SERVER上写
//		 *
//		public CEntityBuilder<T> renderer(Supplier<Function<EntityRendererProvider.Context, EntityRenderer<T>>> renderer) {
//			if (isClient()) {
//				accept(t -> registerRenderer(t, renderer.get()::apply));
//			}
//			return this;
//		}
//		/**
//		 * CLIENT ONLY
//		 * 但是可以在SERVER上写
//
//		public CEntityBuilder<T> layer(Supplier<Supplier<ModelLayerLocation>> loc, Supplier<Supplier<LayerDefinition>> definition) {
//			if (isClient()) {
//				LAYER_DEFINITIONS.put(loc.get(), definition.get());
//			}
//			return this;
//		}

//		public EntityEntry<T> save() {
//			EntityType<T> type = builder.build(name.toString());
//			EntityEntry<T> entry = new EntityEntry<>(name, type);
		//TODO register方法更改

//			Registry.register(BuiltInRegistries.ENTITY_TYPE, name, type);
//			CommonUtils.assertTrue(ENTITIES.put(name, entry) == null, "Duplicate name: " + name);
//			cons.forEach(l -> l.accept(type));
//			return entry;
//		}

		@ExpectPlatform
		private static void registerAttribute(EntityType<? extends LivingEntity> type, Supplier<AttributeSupplier.Builder> attr) {
			throw new AssertionError();
		}

		//TODO CEntities.Client
//		@Environment(EnvType.CLIENT)
//		@ExpectPlatform
//		private static <E extends Entity> void registerRenderer(EntityType<? extends E> type, EntityRendererProvider<E> factory) {
//			throw new AssertionError();
//		}
//		@Environment(EnvType.CLIENT)
//		private static final Map<Supplier<ModelLayerLocation>, Supplier<LayerDefinition>> LAYER_DEFINITIONS = new HashMap<>();
//
//		static {
//			if (isClient()) {
//				addOnModBootstrapHook(() -> LAYER_DEFINITIONS.forEach((k, v) -> registerLayer(k.get(), v)));
//			}
//		}
//		@Environment(EnvType.CLIENT)
//		@ExpectPlatform
//		private static void registerLayer(ModelLayerLocation loc, Supplier<LayerDefinition> definition) {
//			throw new AssertionError();
//		}
	}
}
