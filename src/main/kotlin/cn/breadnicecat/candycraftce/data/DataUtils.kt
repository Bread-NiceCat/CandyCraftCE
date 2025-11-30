package cn.breadnicecat.candycraftce.data

import cn.breadnicecat.candycraftce.core.items.CItems
import cn.breadnicecat.candycraftce.data.providers.CItemModelProvider
import cn.breadnicecat.candycraftce.data.providers.CLanguageProviders
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item

object DataUtils {
    @Suppress("UnstableApiUsage")
    val isRunning get() = FabricDataGenHelper.ENABLED

    fun checkingRunning() {
        if (!isRunning) error("Data generation is not running")
    }

    inline fun ifDatagen(
        block: () -> Unit,
    ) {
        if (isRunning) block()
    }

    /*=========================
            Item Models
     *=========================*/
    fun <I : Item> CItems.ItemBuilder<I>.model(modelAction: ItemModelGenerators.(CItems.Entry<I>) -> Unit): CItems.ItemBuilder<I> {
        ifDatagen {
            record { model(modelAction) }
            lateUsage { e ->
                CItemModelProvider.items.add { modelAction(this, e) }
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

    fun ResourceKey<CreativeModeTab>.translate(en: String, zh: String? = null) {
        abstractTranslate(TranslationBuilder::add, this, en, zh)
    }

    fun <I : Item> CItems.ItemBuilder<I>.translate(en: String, zh: String? = null): CItems.ItemBuilder<I> {
        ifDatagen {
            record { translate(en, zh) }
            lateUsage { (_, i) ->
                abstractTranslate(TranslationBuilder::add, i, en, zh)
            }
        }
        return this
    }
}