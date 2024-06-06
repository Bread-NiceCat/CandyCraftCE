package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
public class BlockEntry<B extends Block> extends SimpleEntry<Block, B> implements ItemLike {
	
	public BlockEntry(ResourceKey<Block> key, Supplier<B> factory) {
		super(key, factory);
	}
	
	public BlockEntry(Pair<ResourceKey<Block>, Supplier<B>> wrapper) {
		super(wrapper);
	}
	
	public BlockState defaultBlockState() {
		return this.get().defaultBlockState();
	}
	
	@Override
	public @NotNull Item asItem() {
		return this.get().asItem();
	}
	
	public ItemStack getDefaultInstance() {
		return asItem().getDefaultInstance();
	}
}
