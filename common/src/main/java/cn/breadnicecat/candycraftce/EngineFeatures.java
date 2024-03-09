package cn.breadnicecat.candycraftce;

import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.block.blockentity.BlockEntityEntry;
import cn.breadnicecat.candycraftce.entity.EntityEntry;
import cn.breadnicecat.candycraftce.gui.block.MenuEntry;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.RecipeTypeEntry;
import cn.breadnicecat.candycraftce.sound.SoundEntry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
 * <p>
 * Common提供的抽象api，由各个引擎的-Impl类实现
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public interface EngineFeatures {

	<I extends Item> ItemEntry<I> registerItem(ResourceLocation id, Supplier<I> sup);

	ResourceKey<CreativeModeTab> registerTab(ResourceLocation id, Function<CreativeModeTab.Builder, CreativeModeTab> builder);

	<B extends Block> BlockEntry<B> registerBlock(ResourceLocation id, Supplier<B> sup);

	<B extends BlockEntity> BlockEntityEntry<B> registerBlockEntity(ResourceLocation id, Supplier<BlockEntityType<B>> b);

	<M extends AbstractContainerMenu> MenuEntry<M> registerMenu(ResourceLocation id, MenuType.MenuSupplier<M> factory);

	<T extends Recipe<?>> RecipeTypeEntry<T> registerRecipe(ResourceLocation id, Supplier<RecipeType<T>> rt, Supplier<RecipeSerializerExt<T>> serializer);

	SoundEntry registerSoundEvent(ResourceLocation eventId, Function<ResourceLocation, SoundEvent> factory);

	<E extends Entity> EntityEntry<E> registerEntity(ResourceLocation id, Supplier<EntityType<E>> factory);

	static EngineFeatures get() {
		return CandyCraftCE.getFeatures();
	}
}
