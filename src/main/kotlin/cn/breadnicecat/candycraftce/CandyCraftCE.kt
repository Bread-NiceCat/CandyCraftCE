package cn.breadnicecat.candycraftce

import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.core.items.CItems
import cn.breadnicecat.candycraftce.core.level.CLevels
import cn.breadnicecat.candycraftce.core.tab.CItemTabs
import cn.breadnicecat.candycraftce.utils.Timer.Companion.timing
import cn.breadnicecat.candycraftce.utils.V
import com.mojang.logging.LogUtils
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger

object CandyCraftCE : ModInitializer {
    const val MOD_ID = "candycraftce"
    val clog: Logger = LogUtils.getLogger()

    @Suppress("UnusedExpression")
    override fun onInitialize() {
        clog.info("CandyCraftCE loading...")
        timing {
            CItemTabs
            CItems
            CBlocks
            CLevels
        }.also {
            clog.info("CandyCraftCE loaded in $it ms")
        }
    }

    //from mixin
    @JvmStatic
    fun onPostInitialize() {
        V.invalidateAll()
    }

}