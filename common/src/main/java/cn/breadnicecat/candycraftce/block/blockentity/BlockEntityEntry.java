package cn.breadnicecat.candycraftce.block.blockentity;

import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

/**
 * Created in 2024/1/30 23:10
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public abstract class BlockEntityEntry<B extends BlockEntity> extends RegistryEntry implements Supplier<BlockEntityType<B>> {
	public BlockEntityEntry(ResourceLocation id) {
		super(id);
	}

	@Override
	public abstract BlockEntityType<B> get();

	public B create(BlockPos pos, BlockState state) {
		return this.get().create(pos, state);
	}

	public String getName() {
		return id.getPath();
	}
}
