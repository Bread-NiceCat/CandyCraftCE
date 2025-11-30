package cn.breadnicecat.candycraftce.data.providers

import cn.breadnicecat.candycraftce.data.DataUtils.checkingRunning
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import java.util.*


class CItemModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    companion object {
        init {
            checkingRunning()
        }

        val items = LinkedList<ItemModelGenerators.() -> Unit>()
        val blocks = LinkedList<BlockModelGenerators. () -> Unit>()
    }

    override fun generateBlockStateModels(generator: BlockModelGenerators) {
        blocks.forEach { it(generator) }
    }

    override fun generateItemModels(generator: ItemModelGenerators) {
        items.forEach { it(generator) }
    }
}