package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.item.CItemBuilder;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.apply;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/12/30 20:07
 * Project: candycraftce
 * Forked from {@link cn.breadnicecat.candycraftce.item.CItemBuilder}
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockBuilder<B extends Block> {
	private final String name;
	private Function<Properties, B> factory;
	private Supplier<Properties> properties;
	private Function<BlockEntry<B>, ItemEntry<? extends BlockItem>> item;

	public static <B extends Block> CBlockBuilder<B> create(String name, Function<Properties, B> factory) {
		return new CBlockBuilder<>(name, factory);
	}

	public static CBlockBuilder<Block> create(String name) {
		return create(name, Block::new);
	}


	protected CBlockBuilder(String name, Function<Properties, B> factory) {
		this.name = name;
		this.factory = factory;
		this.simpleBlockItem(null);
	}


	public CBlockBuilder<B> setProperties(@NotNull Supplier<Properties> supplier) {
		this.properties = supplier;
		return this;
	}

	public CBlockBuilder<B> setProperties(@NotNull Properties prop) {
		setProperties(() -> prop);
		return this;
	}

	public CBlockBuilder<B> setProperties(Supplier<? extends Block> blockSupplier, @Nullable Consumer<Properties> modifier) {
		setProperties(() -> {
			Properties prop = Properties.copy(blockSupplier.get());
			return (modifier == null ? prop : apply(prop, modifier));
		});
		return this;
	}

	public CBlockBuilder<B> setProperties(Block block, @Nullable Consumer<Properties> modifier) {
		setProperties(() -> block, modifier);
		return this;
	}

	public CBlockBuilder<B> noBlockItem() {
		this.item = null;
		return this;
	}

	public CBlockBuilder<B> simpleBlockItem(@Nullable Consumer<CItemBuilder<? extends BlockItem>> modifier) {
		blockItem((t) -> {
			CItemBuilder<BlockItem> builder = CItemBuilder.block(t);
			if (modifier != null) modifier.accept(builder);
			return builder.save();
		});
		return this;
	}

	public CBlockBuilder<B> blockItem(Function<BlockEntry<B>, ItemEntry<? extends BlockItem>> item) {
		this.item = item;
		return this;
	}

	public BlockEntry<B> save() {
		BlockEntry<B> entry = register(name, () -> factory.apply(properties == null ? Properties.of() : properties.get()));
		if (item != null) item.apply(entry);
		assertTrue(CBlocks.BLOCKS.put(entry.getID(), entry) == null, "Duplicate name: " + name);
		return entry;
	}


	public static <B extends Block> BlockEntry<B> register(String name, Supplier<B> sup) {
		return _register(prefix(name), sup);
	}

	/**
	 * @deprecated 在forge环境中若name的命名空间非 {@link CandyCraftCE#MOD_ID} 则会报错
	 */
	@Deprecated
	@ExpectPlatform
	private static <B extends Block> BlockEntry<B> _register(ResourceLocation name, Supplier<B> sup) {
		return CommonUtils.impossible();
	}


}
