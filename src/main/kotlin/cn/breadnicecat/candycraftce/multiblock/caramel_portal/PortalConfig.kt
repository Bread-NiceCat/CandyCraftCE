package cn.breadnicecat.candycraftce.multiblock.caramel_portal

import cn.breadnicecat.candycraftce.core.tag.CTags
import net.minecraft.world.level.block.state.BlockState

/**
 * Created by NiceCat on 2025/12/9.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
data class PortalConfig(
    val minWidth: Int,
    val maxWidth: Int,
    val minHeight: Int,
    val maxHeight: Int,
    val enableHorizontal: Boolean,
    val enableCompound: Boolean,
    val isEmpty: (BlockState) -> Boolean,
    val isFrame: (BlockState) -> Boolean,
) {
    val limWidth = minWidth..maxWidth
    val limHeight = minHeight..maxHeight

    companion object {
        val DEFAULT = PortalConfig(
            minWidth = 2,
            maxWidth = 21,
            minHeight = 3,
            maxHeight = 21,
            enableHorizontal = true,
            enableCompound = true,
            isEmpty = { it.isAir },
            isFrame = { it.`is`(CTags.CBlockTags.caramel_portal_frame) }
        )
    }

    val searcher by lazy { CaramelPortalSearcher(this) }
}
