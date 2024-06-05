package cn.breadnicecat.candycraftce.block.blockentity;

import cn.breadnicecat.candycraftce.block.blockentity.entities.AdvancedFactoryBE;
import cn.breadnicecat.candycraftce.block.blockentity.entities.ChocolateFurnaceBE;
import cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE;
import cn.breadnicecat.candycraftce.block.blockentity.entities.SugarFactoryBE;
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
	public static final BlockEntityEntry<LicoriceFurnaceBE> LICORICE_FURNACE_BE = create(LICORICE_FURNACE.getName(), LicoriceFurnaceBE::new).setValidBlocks(LICORICE_FURNACE).save();
	public static final BlockEntityEntry<ChocolateFurnaceBE> CHOCOLATE_FURNACE_BE = create(CHOCOLATE_FURNACE.getName(), ChocolateFurnaceBE::new).setValidBlocks(CHOCOLATE_FURNACE, WHITE_CHOCOLATE_FURNACE/*, BLACK_CHOCOLATE_FURNACE*/).save();
	public static final BlockEntityEntry<SugarFactoryBE> SUGAR_FACTORY_BE = create(SUGAR_FACTORY.getName(), SugarFactoryBE::new).setValidBlocks(SUGAR_FACTORY).save();
	public static final BlockEntityEntry<AdvancedFactoryBE> ADVANCED_SUGAR_FACTORY_BE = create(ADVANCED_SUGAR_FACTORY.getName(), AdvancedFactoryBE::new).setValidBlocks(ADVANCED_SUGAR_FACTORY).save();
	
	private static final Logger LOGGER = CLogUtils.sign();
	
	public static void init() {
	}
	
	
}
