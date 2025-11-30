package cn.breadnicecat.candycraftce

import cn.breadnicecat.candycraftce.core.items.CItems
import cn.breadnicecat.candycraftce.core.tabs.CItemTabs
import cn.breadnicecat.candycraftce.utils.V
import net.fabricmc.api.ModInitializer

object CandyCraftCE : ModInitializer {
    const val MOD_ID = "candycraftce"

    @Suppress("UnusedExpression")
    override fun onInitialize() {
        CItems
        CItemTabs
    }

    @JvmStatic
    fun onPostInitialize() {
        V.invalidateAll()
    }

}