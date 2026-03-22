package cn.breadnicecat.candycraftce.integration.jei

import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin

@JeiPlugin
class CJeiPlugin : IModPlugin {
    companion object {
        init {
            CUtils.sign()
        }
    }

    override fun getPluginUid() = "jei_plugin".modLoc()
}