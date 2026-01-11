package cn.breadnicecat.candycraftce.data

import cn.breadnicecat.candycraftce.data.extension.level.ConfiguredFeatureDataScope
import cn.breadnicecat.candycraftce.data.providers.CDynamicRegistryProvider
import cn.breadnicecat.candycraftce.data.providers.CLanguageProviders
import cn.breadnicecat.candycraftce.data.providers.CModelProvider
import cn.breadnicecat.candycraftce.data.providers.CTagProviders
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
        pack.addProvider(::CDynamicRegistryProvider)
    }

    override fun buildRegistry(registryBuilder: RegistrySetBuilder) {
        registryBuilder.add(CONFIGURED_FEATURE, ConfiguredFeatureDataScope::bootstrap)
    }
}