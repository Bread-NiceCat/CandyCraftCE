package cn.breadnicecat.candycraftce.block.blockentity;

import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.block.blockentity.entities.*;
import cn.breadnicecat.candycraftce.block.blocks.DungeonTeleporterBlock;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import org.slf4j.Logger;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.block.blockentity.CBlockEntityBuilder.create;

/**
 * Created in 2024/1/30 23:08
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockEntities {
	private static final Logger LOGGER = CLogUtils.sign();
	
	public static final BlockEntityEntry<LicoriceFurnaceBE> LICORICE_FURNACE_BE = create(LICORICE_FURNACE.getName(), LicoriceFurnaceBE::new).setValidBlocks(LICORICE_FURNACE).save();
	public static final BlockEntityEntry<ChocolateFurnaceBE> CHOCOLATE_FURNACE_BE = create(CHOCOLATE_FURNACE.getName(), ChocolateFurnaceBE::new).setValidBlocks(CHOCOLATE_FURNACE, WHITE_CHOCOLATE_FURNACE/*, BLACK_CHOCOLATE_FURNACE*/).save();
	public static final BlockEntityEntry<SugarFactoryBE> SUGAR_FACTORY_BE = create(SUGAR_FACTORY.getName(), SugarFactoryBE::new).setValidBlocks(SUGAR_FACTORY).save();
	public static final BlockEntityEntry<AdvancedFactoryBE> ADVANCED_SUGAR_FACTORY_BE = create(ADVANCED_SUGAR_FACTORY.getName(), AdvancedFactoryBE::new).setValidBlocks(ADVANCED_SUGAR_FACTORY).save();
	public static final BlockEntityEntry<DungeonTeleporterBE> DUNGEON_TELEPORTER_BE = create(JELLY_DUNGEON_TELEPORTER.getName(), DungeonTeleporterBE::new)
			.setValidBlocks(()->BLOCKS.stream()
					.map(BlockEntry::get)
					.filter(b->b instanceof DungeonTeleporterBlock)
					.toArray(DungeonTeleporterBlock[]::new))
			.save();

	
	public static void init() {
	}
	
	
}
