package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created in 2023/12/10 10:10
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public abstract class BlockEntry<B extends Block> extends RegistryEntry implements ItemLike, Supplier<B> {

	public BlockEntry(ResourceLocation id) {
		super(id);
	}

	public BlockState defaultBlockState() {
		return this.get().defaultBlockState();
	}

	public String getName() {
		return id.getPath();
	}

	@Override
	public @NotNull Item asItem() {
		return this.get().asItem();
	}
}
