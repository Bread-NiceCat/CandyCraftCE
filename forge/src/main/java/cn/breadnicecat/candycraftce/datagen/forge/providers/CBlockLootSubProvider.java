package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.item.CItems.*;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static net.minecraft.world.item.Items.SUGAR;

/**
 * Created in 2023/12/30 21:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockLootSubProvider extends BlockLootSubProvider {
	private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
	private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
	private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f};
	private static final Set<Item> EXPLOSION_RESISTANT = Set.of();

	//		Stream.of(
//	).map(BlockEntry::asItem).collect(Collectors.toSet());
	public CBlockLootSubProvider() {
		super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void generate() {
		accept(m -> dropSelf(m.get()),
				CARAMEL_BLOCK, CHOCOLATE_COBBLESTONE, PUDDING,
				CANDY_CANE_BLOCK, CANDY_CANE_WALL, CANDY_CANE_FENCE, CANDY_CANE_SLAB, CANDY_CANE_STAIRS,
				MARSHMALLOW_CRAFTING_TABLE, LICORICE_FURNACE, CHOCOLATE_FURNACE, SUGAR_FACTORY, ADVANCED_SUGAR_FACTORY,
				ALCHEMY_MIXER, MARSHMALLOW_LOG, DARK_MARSHMALLOW_LOG, LIGHT_MARSHMALLOW_LOG,
				MARSHMALLOW_PLANKS, LIGHT_MARSHMALLOW_PLANKS, DARK_MARSHMALLOW_PLANKS, HONEYCOMB_TORCH,
				JELLY_ORE, LICORICE_ORE, PEZ_ORE, CHOCOLATE_SAPLING, WHITE_CHOCOLATE_SAPLING, CARAMEL_SAPLING, CANDIED_CHERRY_SAPLING,
				LICORICE_BLOCK, LICORICE_BRICK, LICORICE_WALL, LICORICE_BRICK_WALL, LICORICE_SLAB, LICORICE_BRICK_SLAB, LICORICE_STAIRS, LICORICE_BRICK_STAIRS,
				MARSHMALLOW_FENCE, LIGHT_MARSHMALLOW_FENCE, DARK_MARSHMALLOW_FENCE, MARSHMALLOW_SLAB, LIGHT_MARSHMALLOW_SLAB,
				DARK_MARSHMALLOW_SLAB, MARSHMALLOW_STAIRS, LIGHT_MARSHMALLOW_STAIRS, DARK_MARSHMALLOW_STAIRS,
				NOUGAT_BLOCK, NOUGAT_HEAD, HONEYCOMB_BLOCK, HONEYCOMB_LAMP, PEZ_BLOCK, CHEWING_GUM_PUDDLE, MARSHMALLOW_LADDER,
				MARSHMALLOW_FENCE_GATE, LIGHT_MARSHMALLOW_FENCE_GATE, DARK_MARSHMALLOW_FENCE_GATE,
				TRAMPOJELLY, RED_TRAMPOJELLY, SOFT_TRAMPOJELLY, JELLY_SHOCK_ABSORBER, SENSITIVE_JELLY, SUGAR_SPIKES, CRANBERRY_SPIKES,
				CARAMEL_GLASS, ROUND_CARAMEL_GLASS, DIAMOND_CARAMEL_GLASS,
				CARAMEL_GLASS_PANE, ROUND_CARAMEL_GLASS_PANE, DIAMOND_CARAMEL_GLASS_PANE
		);
		accept(m -> add(m, noDrop()), CARAMEL_PORTAL);
		accept(m -> add(m, createDoorTable(m.get())), MARSHMALLOW_DOOR, LIGHT_MARSHMALLOW_DOOR, DARK_MARSHMALLOW_DOOR);
		add(COTTON_CANDY_WEB, createSilkTouchOrShearsDispatchTable(COTTON_CANDY_WEB.get(), LootItem.lootTableItem(COTTON_CANDY).when(LootItemRandomChanceCondition.randomChance(1 / 6f))));
		add(MAGIC_LEAVES, createCandyLeavesDrops(MAGIC_LEAVES.get(), SUGAR, NORMAL_LEAVES_SAPLING_CHANCES));
		add(CHOCOLATE_LEAVES, createCandyLeavesDrops(CHOCOLATE_LEAVES.get(), CHOCOLATE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(WHITE_CHOCOLATE_LEAVES, createCandyLeavesDrops(WHITE_CHOCOLATE_LEAVES.get(), WHITE_CHOCOLATE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(CARAMEL_LEAVES, createCandyLeavesDrops(CARAMEL_LEAVES.get(), CARAMEL_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(CANDIED_CHERRY_LEAVES, createCandyLeavesDrops(CANDIED_CHERRY_LEAVES.get(), CANDIED_CHERRY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

		add(NOUGAT_ORE, createSingleItemTable(NOUGAT_POWDER, UniformGenerator.between(2, 5)));
		add(HONEYCOMB_ORE, createSingleItemTable(HONEYCOMB_SHARD, UniformGenerator.between(2, 5)));
		add(SUGAR_BLOCK, createSingleItemTableWithSilkTouch(SUGAR_BLOCK.get(), SUGAR, ConstantValue.exactly(4)));
		otherWhenSilkTouch(CHOCOLATE_STONE.get(), CHOCOLATE_COBBLESTONE.get());
		otherWhenSilkTouch(CUSTARD_PUDDING.get(), PUDDING.get());
		dropOther(PUDDING_FARMLAND.get(), PUDDING.get());
	}

	private void add(BlockEntry<?> blo, LootTable.Builder b) {
		add(blo.get(), b);
	}

	@Override
	protected void add(Block block, LootTable.@NotNull Builder builder) {
		assertTrue(this.map.put(block.getLootTable(), builder) == null, () -> "Duplicate LootTable for " + block);
	}

	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return BLOCKS.values().stream().map(BlockEntry::get).collect(Collectors.toSet());
	}

	protected LootTable.@NotNull Builder createCandyLeavesDrops(@NotNull Block leavesBlock, @NotNull ItemLike sapling, float @NotNull ... chances) {
		return createLeavesDrops(leavesBlock, sapling, CItems.MARSHMALLOW_STICK, chances);
	}


	protected LootTable.@NotNull Builder createLeavesDrops(@NotNull Block leavesBlock, @NotNull ItemLike sapling, ItemLike stick, float @NotNull ... chances) {
		return createSilkTouchOrShearsDispatchTable(leavesBlock,
				applyExplosionCondition(leavesBlock, LootItem.lootTableItem(sapling))
						.when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances))
		).withPool(LootPool.lootPool()
				.setRolls(ConstantValue.exactly(1.0f))
				.when(HAS_NO_SHEARS_OR_SILK_TOUCH)
				.add(applyExplosionDecay(leavesBlock, LootItem.lootTableItem(stick)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
								.when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES))
						)
				)
		);
	}
}
