package cn.breadnicecat.candycraftce.data.providers

import cn.breadnicecat.candycraftce.data.DataUtils.checkDataRunning
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.recipes.FinishedRecipe
import java.util.*
import java.util.function.Consumer

/**
 * Created by NiceCat on 2026/3/2.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class CRecipeProvider(output: FabricDataOutput) : FabricRecipeProvider(output) {
    companion object {
        init {
            checkDataRunning()
        }

        val recipes = LinkedList<CRecipeProvider.(Consumer<FinishedRecipe>) -> Unit>()
    }

    override fun buildRecipes(exporter: Consumer<FinishedRecipe>) {
        builtinRecipes(exporter)
        recipes.forEach { it(exporter) }
    }

    internal fun builtinRecipes(exporter: Consumer<FinishedRecipe>) {
    }
}