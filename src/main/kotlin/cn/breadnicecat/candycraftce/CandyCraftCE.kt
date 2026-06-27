package cn.breadnicecat.candycraftce

import cn.breadnicecat.candycraftce.core.block.CBlockEntities
import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.core.gui.block.CBlockMenus
import cn.breadnicecat.candycraftce.core.item.CItems
import cn.breadnicecat.candycraftce.core.level.CFeatures
import cn.breadnicecat.candycraftce.core.level.CFoliagePlacers
import cn.breadnicecat.candycraftce.core.level.CLevels
import cn.breadnicecat.candycraftce.core.particle.CParticles
import cn.breadnicecat.candycraftce.core.recipe.CRecipeTypes
import cn.breadnicecat.candycraftce.core.rule.CGameRules
import cn.breadnicecat.candycraftce.core.tab.CItemTabs
import cn.breadnicecat.candycraftce.integration.iconr.CCIconRCompat
import cn.breadnicecat.candycraftce.integration.jei.CJeiPlugin
import cn.breadnicecat.candycraftce.utils.CLogUtils.debugLog
import cn.breadnicecat.candycraftce.utils.CLogUtils.mainLog
import cn.breadnicecat.candycraftce.utils.CLogUtils.markLateForSign
import cn.breadnicecat.candycraftce.utils.ImmediateScope
import cn.breadnicecat.candycraftce.utils.ifClient
import cn.breadnicecat.candycraftce.utils.ifDev
import cn.breadnicecat.candycraftce.utils.ifLoaded
import cn.breadnicecat.candycraftce.utils.ifServer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import kotlin.time.measureTime

object CandyCraftCE : ModInitializer {
    const val MOD_ID = "candycraftce"
    val immediateScope = ImmediateScope()

    @Suppress("UnusedExpression")
    override fun onInitialize() {
        mainLog.info("CandyCraftCE loading...")
        measureTime {
            CItemTabs
            CItems
            CBlocks
            CBlockEntities
            CBlockMenus
            CLevels
            CFoliagePlacers
            CFeatures
            CRecipeTypes
            CGameRules
            CParticles
            ifLoaded("iconr") { CCIconRCompat }
            ifLoaded("jei") { CJeiPlugin }
        }.also {
            markLateForSign()
            mainLog.info("CandyCraftCE loaded in $it")
        }
    }

    init {
        ifClient {
            ClientLifecycleEvents.CLIENT_STARTED.register { onPostInitialize }
        }
        ifServer {
            ServerLifecycleEvents.SERVER_STARTED.register { onPostInitialize }
        }
    }

    private val onPostInitialize = lazy {
        measureTime {
            val mem = ifDev {
                System.gc()
                Runtime.getRuntime().freeMemory()
            }
            immediateScope.invalidateScope()
            ifDev {
                System.gc()
                debugLog.info("`Immediate` freed ${Runtime.getRuntime().freeMemory() - mem!!} bytes")
            }
        }.also {
            mainLog.info("CandyCraftCE post-loaded in $it")
        }
    }

}