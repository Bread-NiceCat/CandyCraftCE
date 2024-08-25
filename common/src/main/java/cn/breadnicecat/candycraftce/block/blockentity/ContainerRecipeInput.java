package cn.breadnicecat.candycraftce.block.blockentity;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/8/25 12:52
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public record ContainerRecipeInput<T extends BlockEntity & Container>(T container) implements RecipeInput {
	
	@Override
	public @NotNull ItemStack getItem(int index) {
		return container.getItem(index);
	}
	
	@Override
	public int size() {
		return container.getContainerSize();
	}
	
	@Override
	public boolean isEmpty() {
		return container.isEmpty();
	}
	
	
}
