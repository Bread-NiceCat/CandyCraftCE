package cn.breadnicecat.candycraftce.entity;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import cn.breadnicecat.candycraftce.utils.tools.Triple;
import com.google.common.collect.ImmutableMap;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.hookMinecraftSetup;
import static cn.breadnicecat.candycraftce.CandyCraftCE.register;
import static cn.breadnicecat.candycraftce.item.CItems._spawn_egg;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.check;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/27 10:49
 */
public class CEntityBuilder<T extends Entity> {
	private static final Logger LOGGER = CLogUtils.getModLogger();
	
	private final String name;
	private final Class<T> clazz;
	private final EntityType.Builder<T> builder;
	private Supplier<AttributeSupplier.Builder> attribute;
	private Triple<SpawnPlacementType, Heightmap.Types, SpawnPlacements.SpawnPredicate<?>> placement;
	private Function<EntityEntry<T>, Supplier<ItemEntry<SpawnEggItem>>> egg;
	
	private Consumer<ClientBuilder<T>> clientBuilder;
	
	private static @Nullable List<Supplier<ItemEntry<SpawnEggItem>>> eggs = new LinkedList<>();
	
	static {
		//把蛋都排到最后去
		CItems.hookEntityEggs(eggs);
		CandyCraftCE.hookPostBootstrap(() -> eggs = null);
	}
	
	public CEntityBuilder(String name, Class<T> clazz, EntityType.EntityFactory<T> factory, MobCategory category) {
		this.clazz = clazz;
		builder = EntityType.Builder.of(factory, category);
		this.name = name;
	}
	
	@NotNull
	public static <T extends Entity> CEntityBuilder<T> create(String name, Class<T> clazz, EntityType.EntityFactory<T> pFactory, MobCategory pCategory) {
		return new CEntityBuilder<>(name, clazz, pFactory, pCategory);
	}
	
	public CEntityBuilder<T> sized(float weigh, float height) {
		builder.sized(weigh, height);
		return this;
	}
	
	public CEntityBuilder<T> modify(@NotNull Consumer<EntityType.Builder<T>> consumer) {
		consumer.accept(builder);
		return this;
	}
	
	/**
	 * 地形生成时生成实体的要求
	 * 只允许Mob调用
	 */
	public CEntityBuilder<T> setPlacements(SpawnPlacementType placement, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {
		this.placement = Triple.of(placement, heightmapType, predicate);
		return this;
	}
	
	
	public CEntityBuilder<T> clientTrackingRange(int clientTrackingRange) {
		builder.clientTrackingRange(clientTrackingRange);
		return this;
	}
	
	public CEntityBuilder<T> updateInterval(int updateInterval) {
		builder.updateInterval(updateInterval);
		return this;
	}
	
	/**
	 * @apiNote 只允许Mob类型的生物拥有生物蛋
	 */
	public CEntityBuilder<T> spawnEgg(Function<EntityEntry<T>, Supplier<ItemEntry<SpawnEggItem>>> egg) {
		this.egg = egg;
		return this;
	}
	
	
	/**
	 * @apiNote 只允许Mob类型的生物拥有生物蛋
	 */
	@SuppressWarnings("unchecked")
	public CEntityBuilder<T> spawnEgg(int backgroundColor, int highlightColor) {
		return spawnEgg((r) -> _spawn_egg((EntityEntry<Mob>) r, backgroundColor, highlightColor, new Item.Properties()));
	}
	
	/**
	 * @apiNote 只有LivingEntity拥有attribute
	 */
	public CEntityBuilder<T> attribute(Supplier<AttributeSupplier.Builder> builder) {
		attribute = builder;
		return this;
	}
	
	public CEntityBuilder<T> client(Consumer<ClientBuilder<T>> consumer) {
		if (CandyCraftCE.isClient()) {
			CommonUtils.check(clientBuilder == null, "You have created a ClientBuilder");
			clientBuilder = consumer;
		}
		return this;
	}
	
	@SuppressWarnings({"unchecked"})
	public EntityEntry<T> save() {
		ResourceLocation prefix = prefix(name);
		EntityEntry<T> s = new EntityEntry<>(clazz, register(ENTITY_TYPE, prefix, () -> builder.build(prefix.toString())));
		CEntityTypes.ENTITIES.put(name, s);
		if (clientBuilder != null) {
			hookMinecraftSetup(() -> {
				LOGGER.info("Creating EntityClientBuilder for {}", prefix);
				ClientBuilder<T> cb = new ClientBuilder<>();
				clientBuilder.accept(cb);
				EntityRenderers.register(s.get(), cb.rendererProvider);
			});
		}
		if (attribute != null) {
			CommonUtils.check(s.isLivingEntity(), "Only LivingEntity has Attribute");
			registerAttribute((EntityEntry<LivingEntity>) s, attribute);
		}
		if (egg != null) {
			CommonUtils.check(s.isMob(), "Only Mob has SpawnEgg");
			eggs.add(egg.apply(s));
		}
		if (placement != null) {
			CommonUtils.check(s.isMob(), "Only Mob have SpawnPlacement");
			hookMinecraftSetup(() -> SpawnPlacements.register((EntityType<Mob>) s.get(), placement.a(), placement.b(), (SpawnPlacements.SpawnPredicate<Mob>) placement.c()));
		}
		
		return s;
	}
	
	@ExpectPlatform
	public static void registerAttribute(EntityEntry<LivingEntity> entity, Supplier<AttributeSupplier.Builder> attr) {
		impossibleCode();
	}
	
	/**
	 * 由mixin调用,注册layer
	 */
	@Environment(EnvType.CLIENT)
	public static void _createRoots(ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder) {
		LOGGER.info("Create Model Roots");
		ClientBuilder.layers.forEach((k, v) -> builder.put(k, v.get()));
	}
	
	//NOTE: although we won't use it in client,it needs to load this class when generate a lambda expression
	//	@Environment(EnvType.CLIENT)
	public static class ClientBuilder<T extends Entity> {
		
		//涉及到ResourceReload，所以初始化完后保留
		private static final HashMap<ModelLayerLocation, Supplier<LayerDefinition>> layers = new HashMap<>();
		
		
		private ClientBuilder() {
			CommonUtils.check(CandyCraftCE.isClient(), "not in client!");
		}
		
		private EntityRendererProvider<T> rendererProvider;
		
		public ClientBuilder<T> setRenderer(EntityRendererProvider<T> provider) {
			rendererProvider = provider;
			return this;
		}
		
		@SuppressWarnings({"rawtypes", "unchecked"})
		public ClientBuilder<T> setRendererForce(EntityRendererProvider provider) {
			rendererProvider = provider;
			return this;
		}
		
		public ClientBuilder<T> addLayer(ModelLayerLocation location, Supplier<LayerDefinition> layer) {
			layers.put(location, layer);
			return this;
		}
	}
}
	
