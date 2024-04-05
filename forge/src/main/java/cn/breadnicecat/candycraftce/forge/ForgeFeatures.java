package cn.breadnicecat.candycraftce.forge;

import cn.breadnicecat.candycraftce.EngineFeatures;
import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.block.FluidEntry;
import cn.breadnicecat.candycraftce.block.blockentity.BlockEntityEntry;
import cn.breadnicecat.candycraftce.entity.EntityEntry;
import cn.breadnicecat.candycraftce.gui.block.MenuEntry;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.RecipeTypeEntry;
import cn.breadnicecat.candycraftce.sound.SoundEntry;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;
import static cn.breadnicecat.candycraftce.CandyCraftCE.hookPostBootstrap;
import static cn.breadnicecat.candycraftce.forge.CandyCraftCEImpl.registerRegister;

/**
 * Created in 2024/2/24 14:21
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
class ForgeFeatures implements EngineFeatures {


	public static Map<ResourceKey<? extends Registry<?>>, DeferredRegister<?>> deferredRegisters = new HashMap<>();

	static {
		hookPostBootstrap(deferredRegisters::clear);
	}

	public static <I> DeferredRegister<I> getOrCreate(IForgeRegistry<I> type) {
		return getOrCreate(type.getRegistryKey());
	}

	@SuppressWarnings("unchecked")
	public static <I> DeferredRegister<I> getOrCreate(ResourceKey<? extends Registry<I>> key) {
		return (DeferredRegister<I>) deferredRegisters.computeIfAbsent(key, (k) -> registerRegister(DeferredRegister.create(key, MOD_ID)));
	}

	@Override
	public <I extends Item> ItemEntry<I> registerItem(@NotNull ResourceLocation id, Supplier<I> sup) {
		RegistryObject<I> object = getOrCreate(ForgeRegistries.ITEMS).register(id.getPath(), sup);
		return new ItemEntry<>(id) {
			@Override
			public I get() {
				return object.get();
			}
		};
	}

	@Override
	public ResourceKey<CreativeModeTab> registerTab(@NotNull ResourceLocation id, Function<CreativeModeTab.Builder, CreativeModeTab> builder) {
		return getOrCreate(Registries.CREATIVE_MODE_TAB).register(id.getPath(), () -> builder.apply(CreativeModeTab.builder())).getKey();
	}


	@Override
	public <B extends Block> BlockEntry<B> registerBlock(@NotNull ResourceLocation id, Supplier<B> sup) {
		RegistryObject<B> object = getOrCreate(ForgeRegistries.BLOCKS).register(id.getPath(), sup);
		return new BlockEntry<>(id) {
			@Override
			public B get() {
				return object.get();
			}
		};
	}

	@Override
	public <B extends BlockEntity> BlockEntityEntry<B> registerBlockEntity(ResourceLocation id, Supplier<BlockEntityType<B>> b) {
		RegistryObject<BlockEntityType<B>> object = getOrCreate(ForgeRegistries.BLOCK_ENTITY_TYPES).register(id.getPath(), b);
		return new BlockEntityEntry<>(id) {
			@Override
			public BlockEntityType<B> get() {
				return object.get();
			}
		};
	}

	@Override
	public <M extends AbstractContainerMenu> MenuEntry<M> registerMenu(ResourceLocation id, MenuType.MenuSupplier<M> factory) {
		RegistryObject<MenuType<M>> object = getOrCreate(ForgeRegistries.MENU_TYPES).register(id.getPath(), () -> new MenuType<>(factory, FeatureFlagSet.of()));
		return new MenuEntry<>(id) {
			@Override
			public MenuType<M> get() {
				return object.get();
			}
		};
	}

	@Override
	public SoundEntry registerSoundEvent(@NotNull ResourceLocation eventId, Function<ResourceLocation, SoundEvent> factory) {
		RegistryObject<SoundEvent> object = getOrCreate(ForgeRegistries.SOUND_EVENTS).register(eventId.getPath(), () -> factory.apply(eventId));
		return new SoundEntry(object.getId()) {
			@Override
			public SoundEvent get() {
				return object.get();
			}
		};
	}

	@Override
	public <T extends Recipe<?>> RecipeTypeEntry<T> registerRecipe(ResourceLocation id, Supplier<RecipeType<T>> rt, Supplier<RecipeSerializerExt<T>> serializer) {
		RegistryObject<RecipeType<T>> object = getOrCreate(ForgeRegistries.RECIPE_TYPES).register(id.getPath(), rt);
		RegistryObject<RecipeSerializerExt<T>> object_s = getOrCreate(ForgeRegistries.RECIPE_SERIALIZERS).register(id.getPath(), serializer);
		return new RecipeTypeEntry<>(id) {
			@Override
			public RecipeSerializerExt<T> getSerializer() {
				return object_s.get();
			}

			@Override
			public RecipeType<T> get() {
				return object.get();
			}
		};
	}

	@Override
	public <E extends Entity> EntityEntry<E> registerEntity(ResourceLocation id, Supplier<EntityType<E>> factory) {
		RegistryObject<EntityType<E>> object = getOrCreate(ForgeRegistries.ENTITY_TYPES).register(id.getPath(), factory);
		return new EntityEntry<>(object.getId()) {
			@Override
			public EntityType<E> get() {
				return object.get();
			}
		};
	}

	@Override
	public <F extends Fluid> FluidEntry<F> registerFluid(ResourceLocation id, Supplier<F> factory) {
		RegistryObject<F> object = getOrCreate(ForgeRegistries.FLUIDS).register(id.getPath(), factory);
		return new FluidEntry<>(id) {
			@Override
			public F get() {
				return object.get();
			}
		};
	}

	@Override
	public <T, R extends T> SimpleEntry<R> register(Registry<T> registry, ResourceLocation key, Supplier<R> value) {
		return new SimpleEntry<>(key,
				getOrCreate(registry.key()).register(key.getPath(), value));
	}

}
