package cn.breadnicecat.candycraftce

import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.core.items.CItems
import cn.breadnicecat.candycraftce.core.level.CLevels
import cn.breadnicecat.candycraftce.core.tab.CItemTabs
import cn.breadnicecat.candycraftce.utils.CUtils.mainLog
import cn.breadnicecat.candycraftce.utils.Timer.Companion.timing
import cn.breadnicecat.candycraftce.utils.V
import net.fabricmc.api.ModInitializer

object CandyCraftCE : ModInitializer {
    const val MOD_ID = "candycraftce"

    @Suppress("UnusedExpression")
    override fun onInitialize() {
        mainLog.info("CandyCraftCE loading...")
        timing {
            CItemTabs
            CItems
            CBlocks
            CLevels
        }.also {
            mainLog.info("CandyCraftCE loaded in $it ms")
        }
    }

    //from mixin
    @JvmStatic
    fun onPostInitialize() {
        V.invalidateAll()
    }

}