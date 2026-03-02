package cn.breadnicecat.candycraftce.core.block.blocks.plant

import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import net.minecraft.Util

/**
 * Created by NiceCat on 2026/2/28.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class SweetGrassBlock(properties: Properties) : CandyPlantBlock(properties) {
    companion object {
        val descId: String = Util.makeDescriptionId("block", "sweet_grass".modLoc())
    }

    override fun getDescriptionId() = descId
}