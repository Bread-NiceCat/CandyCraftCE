package cn.breadnicecat.candycraftce.data.providers

import cn.breadnicecat.candycraftce.data.providers.loot.CBlockSubLoot
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets

/**
 * Created by NiceCat on 2026/1/11.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class CLootProvider(output: FabricDataOutput) : LootTableProvider(
    output,
    setOf(),
    listOf(
        SubProviderEntry({ CBlockSubLoot(output) }, LootContextParamSets.BLOCK)
    )
)