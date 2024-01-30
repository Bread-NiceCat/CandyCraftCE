package cn.breadnicecat.candycraftce.registration.block.entity.fabric;

import cn.breadnicecat.candycraftce.registration.block.entity.CBlockEntityEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

/**
 * Created in 2024/1/31 0:00
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockEntityBuilderImpl {
	public static <B extends BlockEntity> CBlockEntityEntry<B> _register(ResourceLocation id, Supplier<BlockEntityType<B>> b) {
		BlockEntityType<B> type = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, b.get());
		return new CBlockEntityEntry<>(id) {
			@Override
			public BlockEntityType<B> get() {
				return type;
			}
		};
	}
}
