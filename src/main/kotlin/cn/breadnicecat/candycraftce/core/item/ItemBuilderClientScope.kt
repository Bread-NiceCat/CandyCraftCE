package cn.breadnicecat.candycraftce.core.item

import cn.breadnicecat.candycraftce.utils.CUtils.ifClient
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.item.Item

/**
 * Created by NiceCat on 2025/12/9.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class ItemBuilderClientScope<I : Item>(val builder: ItemBuilder<I>) {
    companion object {
        inline fun <I : Item> ItemBuilder<I>.client(action: ItemBuilderClientScope<I>.() -> Unit): ItemBuilder<I> {
            ifClient {
                action(ItemBuilderClientScope(this))
            }
            return this
        }
    }

    fun tint(itemColor: ItemColor) {
        builder.record("tint") {
            lateUsage { (_, item) ->
                ColorProviderRegistry.ITEM.register(itemColor, item)
            }
        }
    }
}