package cn.breadnicecat.candycraftce.block.blockentity;

import cn.breadnicecat.candycraftce.Bindings;
import com.mojang.datafixers.types.Type;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/1/30 23:23
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockEntityBuilder<B extends BlockEntity> {
	public final String name;
	private final BlockEntitySupplier<B> factory;
	private Supplier<? extends Block>[] valid;
	@Nullable
	private Type<?> dsl;

	private CBlockEntityBuilder(String name, BlockEntitySupplier<B> factory) {
		this.name = name;
		this.factory = factory;
	}

	public static <B extends BlockEntity> CBlockEntityBuilder<B> create(String name, BlockEntitySupplier<B> factory) {
		return new CBlockEntityBuilder<>(name, factory);
	}

	@SafeVarargs
	public final CBlockEntityBuilder<B> setValidBlocks(Supplier<? extends Block>... blocks) {
		this.valid = blocks;
		return this;
	}

	public CBlockEntityBuilder<B> setDSL(Type<?> dsl) {
		this.dsl = dsl;
		return this;
	}

	public BlockEntityEntry<B> save() {
		Objects.requireNonNull(valid, "No valid blocks");
		return register(name, () -> Builder.of(factory,
				Arrays.stream(valid).map(Supplier::get).toArray(Block[]::new)
		).build(dsl));
	}

	public static <B extends BlockEntity> BlockEntityEntry<B> register(String name, Supplier<BlockEntityType<B>> b) {
		return Bindings.registerBlockEntity(prefix(name), b);
	}
}
