package cn.breadnicecat.candycraftce;

import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.block.blockentity.BlockEntityEntry;
import cn.breadnicecat.candycraftce.gui.block.MenuEntry;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.RecipeTypeEntry;
import cn.breadnicecat.candycraftce.sound.SoundEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created in 2024/2/24 14:16
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class Bindings {
	@ExpectPlatform
	public static <I extends Item> ItemEntry<I> registerItem(ResourceLocation id, Supplier<I> sup) {
		return impossibleCode();
	}

	@ExpectPlatform
	public static ResourceKey<CreativeModeTab> registerTab(ResourceLocation id, Function<CreativeModeTab.Builder, CreativeModeTab> builder) {
		return impossibleCode();
	}

	@ExpectPlatform
	public static <B extends Block> BlockEntry<B> registerBlock(ResourceLocation id, Supplier<B> sup) {
		return impossibleCode();
	}

	@ExpectPlatform
	public static <B extends BlockEntity> BlockEntityEntry<B> registerBlockEntity(ResourceLocation id, Supplier<BlockEntityType<B>> b) {
		return impossibleCode();
	}

	@ExpectPlatform
	public static <M extends AbstractContainerMenu> MenuEntry<M> registerMenu(ResourceLocation id, MenuType.MenuSupplier<M> factory) {
		return impossibleCode();
	}

	@ExpectPlatform
	public static <T extends Recipe<?>> RecipeTypeEntry<T> registerRecipe(ResourceLocation id, Supplier<RecipeType<T>> rt, Supplier<RecipeSerializerExt<T>> serializer) {
		return impossibleCode();
	}

	@ExpectPlatform
	public static SoundEntry registerSoundEvent(ResourceLocation eventId, Function<ResourceLocation, SoundEvent> factory) {
		return impossibleCode();
	}

	public static <T> T impossibleCode() {
		throw new AssertionError("Impossible code invoked. It's a bug, please report it to us");
	}

}
