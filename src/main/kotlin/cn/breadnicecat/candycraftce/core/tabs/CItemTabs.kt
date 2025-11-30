package cn.breadnicecat.candycraftce.core.tabs

import cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID
import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.Utils.createKey
import cn.breadnicecat.candycraftce.utils.Utils.modLoc
import cn.breadnicecat.candycraftce.utils.Utils.register
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.BuiltInRegistries.CREATIVE_MODE_TAB
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab


object CItemTabs {
    val MOD = register("main") { param, output ->
        val entrySet = BuiltInRegistries.ITEM.entrySet()
            .filter { (k, v) ->
                k.location().namespace == MOD_ID
            }
            .forEach { (k, v) ->
                output.accept(v.defaultInstance)
            }
    }.apply { translate("CandyCraft Community Edition", "糖果世界社区版") }

    private fun register(id: String, generator: CreativeModeTab.DisplayItemsGenerator): ResourceKey<CreativeModeTab> {
        val tab = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.$MOD_ID.$id"))
            .displayItems(generator)
            .build()
        val key = CREATIVE_MODE_TAB.createKey(id.modLoc())
        CREATIVE_MODE_TAB.register(key, tab)
        return key
    }

}