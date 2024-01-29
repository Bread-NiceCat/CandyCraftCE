package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.registration.block.BlockEntry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

import static cn.breadnicecat.candycraftce.registration.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;
import static net.minecraft.world.item.Items.SUGAR;

/**
 * Created in 2023/12/30 21:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockLootSubProvider extends BlockLootSubProvider {
	private static final Set<Item> EXPLOSION_RESISTANT = Set.of();
//		Stream.of(
//	).map(BlockEntry::asItem).collect(Collectors.toSet());

	public CBlockLootSubProvider() {
		super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void generate() {
		accept(m -> dropSelf(m.getBlock()),
				CARAMEL_BLOCK, CHOCOLATE_COBBLESTONE, PUDDING,
				CANDY_CANE_BLOCK, CANDY_CANE_WALL, CANDY_CANE_FENCE, CANDY_CANE_SLAB, CANDY_CANE_STAIRS);
		accept(m -> add(m, noDrop()),
				CARAMEL_PORTAL);

		add(SUGAR_BLOCK, createSingleItemTableWithSilkTouch(SUGAR_BLOCK.getBlock(), SUGAR, ConstantValue.exactly(4f)));
		otherWhenSilkTouch(CHOCOLATE_STONE.getBlock(), CHOCOLATE_COBBLESTONE.getBlock());
		otherWhenSilkTouch(CUSTARD_PUDDING.getBlock(), PUDDING.getBlock());
		dropOther(PUDDING_FARMLAND.getBlock(), PUDDING.getBlock());
	}

	private void add(BlockEntry<?> blo, LootTable.Builder b) {
		add(blo.getBlock(), b);
	}

	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return BLOCKS.values().stream().map(BlockEntry::getBlock).collect(Collectors.toSet());
	}
}
