package cn.breadnicecat.candycraftce.core.block.blocks.plant

import cn.breadnicecat.candycraftce.utils.ModUtils.modLoc
import net.minecraft.Util

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * <p>
 *
 */
class SweetGrassBlock(properties: Properties) : CandyPlantBlock(properties) {
    private val _descriptionId = Util.makeDescriptionId("block", "sweet_grass".modLoc())
    override fun getDescriptionId(): String = _descriptionId
}