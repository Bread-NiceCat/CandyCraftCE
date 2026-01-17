package cn.breadnicecat.candycraftce.integration.jei

import cn.breadnicecat.candycraftce.utils.CUtils
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import net.minecraft.resources.ResourceLocation

@JeiPlugin
class CJeiPlugin : IModPlugin {
    companion object {
        init {
            CUtils.sign()
        }
    }

    override fun getPluginUid(): ResourceLocation? = null
}