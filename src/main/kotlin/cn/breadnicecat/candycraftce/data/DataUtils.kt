package cn.breadnicecat.candycraftce.data

import cn.breadnicecat.candycraftce.core.blocks.CBlocks
import cn.breadnicecat.candycraftce.core.items.CItems
import cn.breadnicecat.candycraftce.core.tags.TagKeys
import cn.breadnicecat.candycraftce.data.providers.CLanguageProviders
import cn.breadnicecat.candycraftce.data.providers.CModelProvider
import cn.breadnicecat.candycraftce.data.providers.CTagProviders
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.BlockModelGenerators.createSimpleBlock
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelLocationUtils.getModelLocation
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object DataUtils {
    @Suppress("UnstableApiUsage")
    val isRunning get() = FabricDataGenHelper.ENABLED

    fun checkDataRunning() {
        if (!isRunning) error("Data generation is not running")
    }

    inline fun ifDatagen(
        block: () -> Unit,
    ) {
        if (isRunning) block()
    }

    /*=========================
               Tags
    *=========================*/
    fun tagCopy(tagKeys: TagKeys) {
        ifDatagen {
            CTagProviders.putCopies(tagKeys)
        }
    }

    fun <I : Item> CItems.ItemBuilder<I>.tag(vararg tag: TagKey<Item>): CItems.ItemBuilder<I> {
        ifDatagen {
            record("tag", overridable = false) {
                lateUsage { (id, _) ->
                    tag.forEach { key ->
                        CTagProviders.putItemOp(key) { add(id) }
                    }
                }
            }

        }
        return this
    }

    fun <B : Block> CBlocks.BlockBuilder<B>.tag(vararg tag: TagKey<Block>): CBlocks.BlockBuilder<B> {
        ifDatagen {
            record("tag") {
                lateUsage { (id, _) ->
                    tag.forEach { key ->
                        CTagProviders.putBlockOp(key) { add(id) }
                    }
                }
            }

        }
        return this
    }

    /*=========================
            Item Models
     *=========================*/
    fun <I : Item> CItems.ItemBuilder<I>.model(modelAction: ItemModelGenerators.(CItems.Entry<I>) -> Unit): CItems.ItemBuilder<I> {
        ifDatagen {
            record("model") {
                lateUsage { e ->
                    CModelProvider.items.add { modelAction(this, e) }
                }
            }
        }
        return this
    }


    fun <I : Item> CItems.ItemBuilder<I>.modelFlat(): CItems.ItemBuilder<I> {
        ifDatagen {
            model {
                generateFlatItem(it.item, ModelTemplates.FLAT_ITEM)
            }
        }
        return this
    }

    fun <I : Item> CItems.ItemBuilder<I>.modelHandheld(): CItems.ItemBuilder<I> {
        ifDatagen {
            model {
                generateFlatItem(it.item, ModelTemplates.FLAT_HANDHELD_ITEM)
            }
        }
        return this
    }

    fun <I : Item> CItems.ItemBuilder<I>.modelBlockSimple(block: CBlocks.Entry<*>): CItems.ItemBuilder<I> {
        ifDatagen {
            record("model") {
                lateUsage { (_, item) ->
                    CModelProvider.blocks.add {
                        delegateItemModel(item, getModelLocation(block.block))
                    }
                }
            }
        }
        return this
    }

    fun <B : Block> CBlocks.BlockBuilder<B>.model(modelAction: BlockModelGenerators.(CBlocks.Entry<B>) -> Unit): CBlocks.BlockBuilder<B> {
        ifDatagen {
            record("model") {
                lateUsage { e ->
                    CModelProvider.blocks.add { modelAction(this, e) }
                }
            }
        }
        return this
    }

    fun <B : Block> CBlocks.BlockBuilder<B>.modelCubeAll(): CBlocks.BlockBuilder<B> {
        ifDatagen {
            model { (_, block) ->
                val tex = TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(block))
                this.blockStateOutput.accept(
                    createSimpleBlock(
                        block, ModelTemplates.CUBE_ALL.create(block, tex, this.modelOutput)
                    )
                )
            }
        }
        return this
    }


    /*=========================
             Translate
     *=========================*/

    private inline fun <I> abstractTranslate(
        crossinline func: TranslationBuilder.(I, String) -> Unit,
        key: I,
        en: String,
        zh: String?,
    ) {
        ifDatagen {
            CLanguageProviders.en.add {
                func(this, key, en)
            }
            if (zh != null) {
                CLanguageProviders.zh.add {
                    func(this, key, zh)
                }
            }
        }
    }

    fun translate(key: String, en: String, zh: String? = null) {
        abstractTranslate(TranslationBuilder::add, key, en, zh)
    }

    fun ResourceLocation.translate(en: String, zh: String? = null) {
        abstractTranslate(TranslationBuilder::add, this, en, zh)
    }

    fun ResourceKey<CreativeModeTab>.translate(en: String, zh: String? = null): ResourceKey<CreativeModeTab> {
        abstractTranslate(TranslationBuilder::add, this, en, zh)
        return this
    }

    fun <B : Block> CBlocks.BlockBuilder<B>.translate(en: String, zh: String? = null): CBlocks.BlockBuilder<B> {
        ifDatagen {
            record("translate", private = true) {
                lateUsage { (_, b) ->
                    abstractTranslate(TranslationBuilder::add, b, en, zh)
                }
            }
        }
        return this
    }

    fun <I : Item> CItems.ItemBuilder<I>.translate(en: String, zh: String? = null): CItems.ItemBuilder<I> {
        ifDatagen {
            record("translate", private = true) {
                lateUsage { (_, i) ->
                    abstractTranslate(TranslationBuilder::add, i, en, zh)
                }
            }
        }
        return this
    }
}