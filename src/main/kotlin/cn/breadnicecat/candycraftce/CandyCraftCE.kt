package cn.breadnicecat.candycraftce

import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.core.item.CItems
import cn.breadnicecat.candycraftce.core.level.CLevels
import cn.breadnicecat.candycraftce.core.particle.CParticles
import cn.breadnicecat.candycraftce.core.rule.CGameRules
import cn.breadnicecat.candycraftce.core.tab.CItemTabs
import cn.breadnicecat.candycraftce.integration.iconr.CCIconRCompat
import cn.breadnicecat.candycraftce.integration.jei.CJeiPlugin
import cn.breadnicecat.candycraftce.utils.CUtils.ifLoaded
import cn.breadnicecat.candycraftce.utils.CUtils.mainLog
import cn.breadnicecat.candycraftce.utils.Immediate
import net.fabricmc.api.ModInitializer
import kotlin.time.measureTime

object CandyCraftCE : ModInitializer {
    const val MOD_ID = "candycraftce"

    @Suppress("UnusedExpression")
    override fun onInitialize() {
        mainLog.info("CandyCraftCE loading...")
        measureTime {
            CItemTabs
            CItems
            CBlocks
            CLevels
            CGameRules
            CParticles
            ifLoaded("iconr") {
                CCIconRCompat
            }
            ifLoaded("jei") {
                CJeiPlugin
            }
        }.also {
            mainLog.info("CandyCraftCE loaded in $it")
        }
    }

    //from mixin
    //after Registry Frozen
    @JvmStatic
    fun onPostInitialize() {
        Immediate.invalidateAll()
    }

}