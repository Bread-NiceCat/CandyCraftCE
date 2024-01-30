package cn.breadnicecat.candycraftce.registration.block.entity;

import com.mojang.datafixers.types.Type;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/1/30 23:23
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockEntityBuilder<B extends BlockEntity> {
	public final String name;
	private final BlockEntitySupplier<B> factory;
	private Supplier<Block[]> vaild;
	@Nullable
	private Type<?> dsl;

	public CBlockEntityBuilder(String name, BlockEntitySupplier<B> factory) {
		this.name = name;
		this.factory = factory;
	}

	public CBlockEntityBuilder<B> setValidBlocks(Supplier<Block[]> blocks) {
		this.vaild = blocks;
		return this;
	}

	public CBlockEntityBuilder<B> setDSL(Type<?> dsl) {
		this.dsl = dsl;
		return this;
	}

	@SuppressWarnings("DataFlowIssue")
	public CBlockEntityEntry<B> save() {
		return register(name, () -> Builder.of(factory, vaild.get()).build(dsl));
	}

	private static <B extends BlockEntity> CBlockEntityEntry<B> register(String name, Supplier<BlockEntityType<B>> b) {
		return _register(prefix(name), b);
	}

	@ExpectPlatform
	private static <B extends BlockEntity> CBlockEntityEntry<B> _register(ResourceLocation name, Supplier<BlockEntityType<B>> b) {
		throw new AssertionError();
	}
}
