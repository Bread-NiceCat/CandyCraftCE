package cn.breadnicecat.candycraftce.registration.block.entity;

import cn.breadnicecat.candycraftce.registration.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

/**
 * Created in 2024/1/30 23:10
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public abstract class CBlockEntityEntry<B extends BlockEntity> extends RegistryEntry implements Supplier<BlockEntityType<B>> {
	public CBlockEntityEntry(ResourceLocation id) {
		super(id);
	}

	@Override
	@Deprecated
	public abstract BlockEntityType<B> get();

	public BlockEntityType<B> getType() {
		return get();
	}
}
