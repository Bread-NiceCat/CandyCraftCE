package cn.breadnicecat.candycraftce.data

import cn.breadnicecat.candycraftce.data.providers.CLanguageProviders
import cn.breadnicecat.candycraftce.data.providers.CModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object CandyCraftCEDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
        val pack = generator.createPack()

        pack.addProvider(::CModelProvider)
        pack.addProvider(::CLanguageProviders)
    }
}