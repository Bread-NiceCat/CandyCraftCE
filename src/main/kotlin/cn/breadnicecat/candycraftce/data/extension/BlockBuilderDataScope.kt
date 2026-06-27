package cn.breadnicecat.candycraftce.data.extension

import cn.breadnicecat.candycraftce.core.block.BlockBuilder
import cn.breadnicecat.candycraftce.core.tag.TagKeys
import cn.breadnicecat.candycraftce.data.CDataUtils.abstractTranslate
import cn.breadnicecat.candycraftce.data.CDataUtils.ifDatagen
import cn.breadnicecat.candycraftce.data.extension.ItemBuilderDataScope.Companion.data
import cn.breadnicecat.candycraftce.data.providers.CTagProviders
import cn.breadnicecat.candycraftce.data.providers.loot.CBlockSubLoot
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.minecraft.tags.BlockTags
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
    private val builder: BlockBuilder<B>,//这是模板builder，不要对其使用任何操作
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
    fun tag2(tag: TagKeys) = tag2(tag.item, tag.block)

    fun tag(vararg tag: TagKey<Block>) {
        builder.record("tag", overridable = false) {
            lateUsage { (id, _) ->
                tag.forEach { key ->
                    CTagProviders.putBlockOp(key) { add(id) }
                }
            }
        }
    }

    fun byAxe() = tag(BlockTags.MINEABLE_WITH_AXE)
    fun byHoe() = tag(BlockTags.MINEABLE_WITH_HOE)
    fun byPickaxe() = tag(BlockTags.MINEABLE_WITH_PICKAXE)
    fun byShovel() = tag(BlockTags.MINEABLE_WITH_SHOVEL)
    fun bySword() = tag(BlockTags.SWORD_EFFICIENT)
    fun needDiamond() = tag(BlockTags.NEEDS_DIAMOND_TOOL)
    fun needIron() = tag(BlockTags.NEEDS_IRON_TOOL)
    fun needStone() = tag(BlockTags.NEEDS_STONE_TOOL)

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


    fun loot(action: CBlockSubLoot.(B) -> Unit) {
        builder.record("loot") {
            lateUsage { entry ->
                CBlockSubLoot.loots.add { action(entry.block) }
            }
        }
    }

}