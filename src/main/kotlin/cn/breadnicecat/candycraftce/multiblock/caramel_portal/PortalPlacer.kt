package cn.breadnicecat.candycraftce.multiblock.caramel_portal

import cn.breadnicecat.candycraftce.utils.AxisSet
import net.minecraft.world.level.block.state.BlockState

/**
 * Created by NiceCat on 2026/2/26.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
fun interface PortalPlacer {
    fun place(axes: AxisSet, original: BlockState): BlockState
}