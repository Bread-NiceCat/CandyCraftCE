package cn.breadnicecat.candycraftce.block.blockentity;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created in 2024/1/30 23:10
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class BlockEntityEntry<B extends BlockEntity> extends SimpleEntry<BlockEntityType<?>, BlockEntityType<B>> {
	public BlockEntityEntry(SimpleEntry<BlockEntityType<?>, BlockEntityType<B>> wrapper) {
		super(wrapper);
	}
	
	public B create(BlockPos pos, BlockState state) {
		return this.get().create(pos, state);
	}
	
}
