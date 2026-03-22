package cn.breadnicecat.candycraftce.data

import cn.breadnicecat.candycraftce.data.extension.level.ConfiguredFeatureDataScope
import cn.breadnicecat.candycraftce.data.providers.*
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries.CONFIGURED_FEATURE

object CandyCraftCEDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
        val pack = generator.createPack()

        pack.addProvider(::CModelProvider)
        pack.addProvider(::CLanguageProviders)
        pack.addProvider(::CTagProviders)
        pack.addProvider(::CLootProvider)
        pack.addProvider(::CDynamicRegistryProvider)
        pack.addProvider(::CRecipeProvider)
    }

    override fun buildRegistry(registryBuilder: RegistrySetBuilder) {
        registryBuilder.add(CONFIGURED_FEATURE, ConfiguredFeatureDataScope::bootstrap)
    }
}