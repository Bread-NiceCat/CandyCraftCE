package cn.breadnicecat.candycraftce.data.extension

import cn.breadnicecat.candycraftce.core.block.BlockBuilder
import cn.breadnicecat.candycraftce.core.tag.TagKeys
import cn.breadnicecat.candycraftce.data.DataUtils.abstractTranslate
import cn.breadnicecat.candycraftce.data.DataUtils.ifDatagen
import cn.breadnicecat.candycraftce.data.extension.ItemBuilderDataScope.Companion.data
import cn.breadnicecat.candycraftce.data.providers.CTagProviders
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

/**
 * Created by NiceCat on 2025/12/8.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class BlockBuilderDataScope<B : Block> private constructor(
    private val builder: BlockBuilder<B>,
) {
    companion object {
        fun <B : Block> BlockBuilder<B>.data(
            action: BlockBuilderDataScope<B>.() -> Unit,
        ): BlockBuilder<B> {
            ifDatagen {
                action(BlockBuilderDataScope(this))
            }
            return this
        }
    }

    fun tag2(item: TagKey<Item>, block: TagKey<Block>) {
        tag(block)
        builder.modifyBlockItem {
            it.data { tag(item) }
        }
    }

    //同时给方块和物品添加标签
    fun tag2(tag: TagKeys) = tag2(tag.first, tag.second)

    fun tag(vararg tag: TagKey<Block>) {
        builder.record("tag", overridable = false) {
            lateUsage { (id, _) ->
                tag.forEach { key ->
                    CTagProviders.putBlockOp(key) { add(id) }
                }
            }
        }
    }

    fun translate(en: String, zh: String? = null) {
        builder.record("translate", private = true) {
            lateUsage { (_, b) ->
                abstractTranslate(TranslationBuilder::add, b, en, zh)
            }
        }
    }

    fun model(action: BlockModelScope<B>.() -> Unit) {
        builder.record("model") {
            BlockModelScope(this).apply(action)
        }
    }
}