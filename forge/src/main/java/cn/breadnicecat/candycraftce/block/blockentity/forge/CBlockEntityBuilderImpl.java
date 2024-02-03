package cn.breadnicecat.candycraftce.block.blockentity.forge;

import cn.breadnicecat.candycraftce.block.blockentity.BlockEntityEntry;
import cn.breadnicecat.candycraftce.forge.CandyCraftCEImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;

/**
 * Created in 2024/1/31 0:00
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockEntityBuilderImpl {
	public static final DeferredRegister<BlockEntityType<?>> REGISTER = CandyCraftCEImpl.createRegister(ForgeRegistries.BLOCK_ENTITY_TYPES);

	public static <B extends BlockEntity> BlockEntityEntry<B> _register(ResourceLocation id, Supplier<BlockEntityType<B>> b) {
		RegistryObject<BlockEntityType<B>> object = REGISTER.register(id.getPath(), b);
		assertTrue(id.equals(object.getId()), () -> "Unmatched ResourceLocation: %s and %s".formatted(id, object.getId()));
		return new BlockEntityEntry<>(id) {
			@Override
			public BlockEntityType<B> get() {
				return object.get();
			}
		};
	}
}
