package cn.breadnicecat.candycraftce.core.tab

import cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID
import cn.breadnicecat.candycraftce.core.items.CItems
import cn.breadnicecat.candycraftce.core.items.ItemBuilder
import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.Utils
import cn.breadnicecat.candycraftce.utils.Utils.createKey
import cn.breadnicecat.candycraftce.utils.Utils.modLoc
import cn.breadnicecat.candycraftce.utils.Utils.register
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.registries.BuiltInRegistries.CREATIVE_MODE_TAB
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import java.util.*
import java.util.function.Supplier


object CItemTabs {
    init {
        Utils.sign()
    }

    private val modTabContents = HashMap<ResourceKey<CreativeModeTab>, LinkedList<ItemStack>>()
    private val unlocalTabContents = HashMap<ResourceKey<CreativeModeTab>, LinkedList<ItemStack>>()

    val CANDYCRAFT: ResourceKey<CreativeModeTab> = register(
        "main",
        icon = { CItems.marshmallow_stick.item.defaultInstance }
    )
        .translate("CandyCraft Community Edition", "糖果世界社区版")

    fun <I : Item> ItemBuilder<I>.tab(tab: ResourceKey<CreativeModeTab>): ItemBuilder<I> {
        record("tab", overridable = false) {
            lateUsage { (_, item) ->
                tab.add(item)
            }
        }
        return this
    }

    fun ResourceKey<CreativeModeTab>.add(item: ItemStack) {
        if (this in modTabContents) {
            modTabContents[this]!!.add(item)
        } else {
            unlocalTabContents.computeIfAbsent(this) {
                //生成注册了事件的容器
                val items = LinkedList<ItemStack>()
                ItemGroupEvents.modifyEntriesEvent(this).register { it.acceptAll(items) }
                items
            }.add(item)
        }
    }

    fun ResourceKey<CreativeModeTab>.add(item: ItemLike) = add(item.asItem().defaultInstance)

    private fun register(
        id: String,
        icon: Supplier<ItemStack>? = null,
        generator: CreativeModeTab.DisplayItemsGenerator? = null,
    ): ResourceKey<CreativeModeTab> {
        val key = CREATIVE_MODE_TAB.createKey(id.modLoc())
        modTabContents[key] = LinkedList()
        val tab = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.$MOD_ID.$id"))
            .apply {
                if (icon != null) icon(icon)
            }
            .displayItems(generator ?: defaultGenerator(key))
            .build()
        CREATIVE_MODE_TAB.register(key, tab)
        return key
    }

    private fun defaultGenerator(tab: ResourceKey<CreativeModeTab>): CreativeModeTab.DisplayItemsGenerator {
        return CreativeModeTab.DisplayItemsGenerator { _, output ->
            modTabContents[tab]?.forEach { output.accept(it) }
        }
    }
}