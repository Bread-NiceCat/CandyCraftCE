package cn.breadnicecat.candycraftce.data.extension

import cn.breadnicecat.candycraftce.core.item.ItemBuilder
import cn.breadnicecat.candycraftce.core.item.ItemBuilder.ItemEntry
import cn.breadnicecat.candycraftce.data.DataUtils.abstractTranslate
import cn.breadnicecat.candycraftce.data.DataUtils.ifDatagen
import cn.breadnicecat.candycraftce.data.providers.CModelProvider
import cn.breadnicecat.candycraftce.data.providers.CTagProviders
import com.mojang.datafixers.util.Either
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelLocationUtils.getModelLocation
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureMapping.layer0
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

/**
 * Created by NiceCat on 2025/12/8.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class ItemBuilderDataScope<I : Item> private constructor(
    private val builder: ItemBuilder<I>,
) {
    companion object {
        fun <I : Item> ItemBuilder<I>.data(action: ItemBuilderDataScope<I>.() -> Unit): ItemBuilder<I> {
            ifDatagen {
                action(ItemBuilderDataScope(this))
            }
            return this
        }
    }

    fun tag(vararg tag: TagKey<Item>) {
        builder.record("tag", overridable = false) {
            lateUsage { (id, _) ->
                tag.forEach { key ->
                    CTagProviders.putItemOp(key) { add(id) }
                }
            }
        }
    }


    fun translate(en: String, zh: String? = null) {
        builder.record("translate", private = true) {
            lateUsage { (_, i) ->
                abstractTranslate(TranslationBuilder::add, i, en, zh)
            }
        }
    }

    fun model(modelAction: ItemModelGenerators.(ItemEntry<I>) -> Unit) {
        builder.record("model") {
            lateUsage { e ->
                CModelProvider.items.add { modelAction(this, e) }
            }
        }
    }

    fun modelFlat(
        layer0: ResourceLocation? = null,
        itemContext: Item? = null,
    ) {
        model { (_, itemCur) ->
            val item = itemContext ?: itemCur
            val tex = if (layer0 != null) layer0(layer0) else layer0(item)
            ModelTemplates.FLAT_ITEM.create(getModelLocation(item), tex, this.output)
        }
    }

    fun modelFlat(
        layer0: Either<ResourceLocation, Item>? = null,
    ) {
        model { (_, item) ->
            val tex: TextureMapping = if (layer0 != null) {
                layer0.map(::layer0, ::layer0)
            } else layer0(item)
            ModelTemplates.FLAT_ITEM.create(getModelLocation(item), tex, this.output)
        }
    }


    fun modelHandheld() {
        model {
            generateFlatItem(it.item, ModelTemplates.FLAT_HANDHELD_ITEM)
        }
    }

    fun modelBlockSimple(block: Block) {
        builder.record("model") {
            lateUsage { (_, item) ->
                CModelProvider.blocks.add {
                    delegateItemModel(item, getModelLocation(block))
                }
            }
        }
    }

    fun modelBlockDirect(block: Block) {
        builder.record("model") {
            CModelProvider.blocks.add {
                createSimpleFlatItemModel(block)
            }
        }
    }

}