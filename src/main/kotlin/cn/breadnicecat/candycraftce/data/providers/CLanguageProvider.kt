package cn.breadnicecat.candycraftce.data.providers

import cn.breadnicecat.candycraftce.data.DataUtils.checkDataRunning
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import java.util.*
import java.util.concurrent.CompletableFuture

typealias TranslationBuilderUsages = LinkedList<TranslationBuilder.() -> Unit>

class CLanguageProviders(val output: FabricDataOutput) : DataProvider {

    companion object {
        init {
            checkDataRunning()
        }

        val zh: TranslationBuilderUsages = LinkedList<TranslationBuilder.() -> Unit>()
        val en: TranslationBuilderUsages = LinkedList<TranslationBuilder.() -> Unit>()

    }

    private val subs = mutableSetOf<SubLanguageProvider>()

    init {
        subs.add(SubLanguageProvider("zh_cn", zh))
        subs.add(SubLanguageProvider("en_us", en))
    }

    override fun run(writer: CachedOutput): CompletableFuture<*> {
        return CompletableFuture.allOf(*subs.map { it.run(writer) }.toTypedArray())
    }

    override fun getName(): String = "CLanguageProviders"

    private inner class SubLanguageProvider(code: String, val usage: TranslationBuilderUsages) :
        FabricLanguageProvider(output, code) {
        override fun generateTranslations(translationBuilder: TranslationBuilder) {
            usage.forEach { it(translationBuilder) }
        }
    }
}