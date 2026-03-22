package cn.breadnicecat.candycraftce.core.level

import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.CUtils.toKey
import net.minecraft.core.registries.Registries

/**
 * Created by NiceCat on 2026/1/10.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */

object CLevels {
    init {
        CUtils.sign()
    }

    const val LAND_HEIGHT: Int = 384
    const val LAND_MIN_Y: Int = -64
    const val LAND_MAX_Y: Int = LAND_MIN_Y + LAND_HEIGHT

    const val DUNGEONS_HEIGHT: Int = 256
    const val DUNGEONS_MIN_Y: Int = 0
    const val DUNGEONS_MAX_Y: Int = DUNGEONS_MIN_Y + DUNGEONS_HEIGHT


    val dungeons_location = "dungeons".modLoc()
    val candyland_location = "candyland".modLoc()

    val dungeons = dungeons_location.toKey(Registries.DIMENSION)
    val candyland = candyland_location.toKey(Registries.DIMENSION)
}