package cn.breadnicecat.candycraftce.core.block

import cn.breadnicecat.candycraftce.core.block.CBlockEntityBuilder.Companion.create
import cn.breadnicecat.candycraftce.core.block.CBlocks.licorice_furnace
import cn.breadnicecat.candycraftce.core.block.blockentities.LicoriceFurnaceBE
import cn.breadnicecat.candycraftce.utils.CLogUtils

/**
 * Created in 2024/1/30 23:08
 * Project: candycraftce
 * 
 * @author [Bread_NiceCat](https://github.com/Bread-Nicecat)
 * 
 * 
 */
object CBlockEntities {
    init {
        CLogUtils.sign()
    }

    val licorice_furnace_be = create(licorice_furnace.id.path, ::LicoriceFurnaceBE)
        .setValidBlocks(licorice_furnace.block)
        .save()
//	public static final BlockEntityEntry<ChocolateFurnaceBE> CHOCOLATE_FURNACE_BE = create(CHOCOLATE_FURNACE.getName(), ChocolateFurnaceBE::new).setValidBlocks(CHOCOLATE_FURNACE, WHITE_CHOCOLATE_FURNACE/*, BLACK_CHOCOLATE_FURNACE*/).save();
    //	public static final BlockEntityEntry<SugarFactoryBE> SUGAR_FACTORY_BE = create(SUGAR_FACTORY.getName(), SugarFactoryBE::new).setValidBlocks(SUGAR_FACTORY).save();
    //	public static final BlockEntityEntry<AdvancedFactoryBE> ADVANCED_SUGAR_FACTORY_BE = create(ADVANCED_SUGAR_FACTORY.getName(), AdvancedFactoryBE::new).setValidBlocks(ADVANCED_SUGAR_FACTORY).save();
}
