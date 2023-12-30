package cn.breadnicecat.candycraftce.registration.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/12/30 20:07
 * Project: candycraftce
 * Forked from {@link cn.breadnicecat.candycraftce.registration.item.CItemBuilder}
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockBuilder<B extends Block> {
	private final String name;
	private Function<Properties, B> factory;
	public Properties properties = Properties.of();
	public boolean ctab = true;

	public static <B extends Block> CBlockBuilder<B> create(String name, Function<Properties, B> factory) {
		return new CBlockBuilder<>(name, factory);
	}

	public static CBlockBuilder<Block> create(String name) {
		return create(name, Block::new);
	}


	protected CBlockBuilder(String name, Function<Properties, B> factory) {
		this.name = name;
		this.factory = factory;
	}


	/**
	 * 新的Properties
	 * 注:默认直接是新的Properties
	 */
	public CBlockBuilder<B> setProperties(@NotNull Properties prop) {
		this.properties = Objects.requireNonNull(prop);
		return this;
	}

	public CBlockBuilder<B> setProperties(Block block) {
		this.properties = Properties.copy(block);
		return this;
	}


	public BlockEntry<B> save() {
		BlockEntry<B> entry = register(name, () -> factory.apply(properties));
		assertTrue(CBlocks.BLOCKS.put(entry.getID(), entry) == null, "Duplicate name: " + name);
		return entry;
	}


	public static <B extends Block> BlockEntry<B> register(String name, Supplier<B> sup) {
		return register(prefix(name), sup);
	}

	/**
	 * @deprecated 在forge环境中若name的命名空间非 {@link CandyCraftCE#MOD_ID} 则会报错
	 */
	@Deprecated
	@ExpectPlatform
	private static <B extends Block> BlockEntry<B> register(ResourceLocation name, Supplier<B> sup) {
		throw new AssertionError();
	}


}
