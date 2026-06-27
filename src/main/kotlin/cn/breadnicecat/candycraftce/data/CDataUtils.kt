package cn.breadnicecat.candycraftce.data

import cn.breadnicecat.candycraftce.core.tag.TagKeys
import cn.breadnicecat.candycraftce.data.providers.CLanguageProviders
import cn.breadnicecat.candycraftce.data.providers.CTagProviders
import cn.breadnicecat.candycraftce.utils.CUtils.trying
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab

object CDataUtils {
    //信雅互联没有data相关方法
    @Suppress("UnstableApiUsage")
    val isRunning get() = trying { FabricDataGenHelper.ENABLED } ?: false

    /**
     * A function used to check weather data generation is running.
     * @throws IllegalStateException if data generation is not running
     * */
    fun checkDataRunning() {
        if (!isRunning) error("Any generation is not running")
    }

    inline fun ifDatagen(
        block: () -> Unit,
    ) {
        if (isRunning) block()
    }

    //Tag
    fun tagCopy(tagKeys: TagKeys) {
        ifDatagen {
            CTagProviders.putCopies(tagKeys)
        }
    }


    //Translate
    //Duplicate part of other translate functions
    internal inline fun <I> abstractTranslate(
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

    fun Component.translate(en: String, zh: String? = null): Component {
        abstractTranslate({ key, v ->
            val trans = key.contents as? TranslatableContents ?: error("Unsupported component type")
            add(trans.key, v)
        }, this, en, zh)
        return this
    }

}