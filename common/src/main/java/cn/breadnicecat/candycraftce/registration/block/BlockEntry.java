package cn.breadnicecat.candycraftce.registration.block;

import cn.breadnicecat.candycraftce.registration.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created in 2023/12/10 10:10
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public abstract class BlockEntry<I extends Block> extends RegistryEntry {

	public BlockEntry(ResourceLocation id) {
		super(id);
	}


	public abstract I getBlock();

	public BlockState defaultBlockState() {
		return getBlock().defaultBlockState();
	}


}
