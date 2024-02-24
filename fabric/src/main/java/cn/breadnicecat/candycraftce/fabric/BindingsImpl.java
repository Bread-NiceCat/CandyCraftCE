package cn.breadnicecat.candycraftce.fabric;

import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.block.blockentity.BlockEntityEntry;
import cn.breadnicecat.candycraftce.gui.block.MenuEntry;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.RecipeTypeEntry;
import cn.breadnicecat.candycraftce.sound.SoundEntry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
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
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created in 2024/2/24 14:16
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class BindingsImpl {

	public static ResourceKey<CreativeModeTab> registerTab(ResourceLocation id, Function<CreativeModeTab.Builder, CreativeModeTab> builder) {
		ResourceKey<CreativeModeTab> idk = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, idk, builder.apply(FabricItemGroup.builder()));
		return idk;
	}

	public static <I extends Item> ItemEntry<I> registerItem(ResourceLocation id, @NotNull Supplier<I> sup) {
		I i = Registry.register(BuiltInRegistries.ITEM, id, sup.get());
		return new ItemEntry<>(id) {
			@Override
			public I get() {
				return i;
			}
		};
	}

	public static <B extends Block> BlockEntry<B> registerBlock(ResourceLocation id, @NotNull Supplier<B> sup) {
		B b = Registry.register(BuiltInRegistries.BLOCK, id, sup.get());
		return new BlockEntry<>(id) {
			@Override
			public B get() {
				return b;
			}
		};
	}

	public static <B extends BlockEntity> BlockEntityEntry<B> registerBlockEntity(ResourceLocation id, @NotNull Supplier<BlockEntityType<B>> b) {
		BlockEntityType<B> type = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, b.get());
		return new BlockEntityEntry<>(id) {
			@Override
			public BlockEntityType<B> get() {
				return type;
			}
		};
	}

	public static <M extends AbstractContainerMenu> MenuEntry<M> registerMenu(ResourceLocation key, MenuType.MenuSupplier<M> factory) {
		MenuType<M> type = Registry.register(BuiltInRegistries.MENU, key, new MenuType<>(factory, FeatureFlagSet.of()));
		return new MenuEntry<>(key) {
			@Override
			public MenuType<M> get() {
				return type;
			}
		};
	}

	public static <T extends Recipe<?>> RecipeTypeEntry<T> registerRecipe(ResourceLocation id, @NotNull Supplier<RecipeType<T>> rt, @NotNull Supplier<RecipeSerializerExt<T>> serializer) {
		RecipeType<T> type = Registry.register(BuiltInRegistries.RECIPE_TYPE, id, rt.get());
		RecipeSerializerExt<T> serial = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializer.get());
		return new RecipeTypeEntry<>(id) {
			@Override
			public RecipeSerializerExt<T> getSerializer() {
				return serial;
			}

			@Override
			public RecipeType<T> get() {
				return type;
			}
		};
	}

	public static SoundEntry registerSoundEvent(ResourceLocation eventId, Function<ResourceLocation, SoundEvent> factory) {
		SoundEvent event = Registry.register(BuiltInRegistries.SOUND_EVENT, eventId, factory.apply(eventId));
		return new SoundEntry(eventId) {
			@Override
			public SoundEvent get() {
				return event;
			}
		};
	}
}
