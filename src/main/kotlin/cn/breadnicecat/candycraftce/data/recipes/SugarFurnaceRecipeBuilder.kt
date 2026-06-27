package cn.breadnicecat.candycraftce.data.recipes

import cn.breadnicecat.candycraftce.core.recipe.recipes.SugarFurnaceRecipe
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

/**
 * Created by NiceCat on 2026/3/23.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class SugarFurnaceRecipeBuilder {
    companion object {
        inline fun builder(action: SugarFurnaceRecipeBuilder.() -> Unit): SugarFurnaceRecipeBuilder {
            return SugarFurnaceRecipeBuilder().apply(action)
        }

        fun direct(
            id: ResourceLocation,
            ingredient: Ingredient,
            result: ItemStack,
            exp: Float = 0f,
        ): SugarFurnaceRecipeBuilder {
            return builder {
                this.id = id
                this.ingredient = ingredient
                this.result = result
                this.exp = exp
            }
        }
    }

    lateinit var id: ResourceLocation
    lateinit var ingredient: Ingredient
    lateinit var result: ItemStack
    var exp: Float = 0f

    fun build(): Result {
        return Result(id, SugarFurnaceRecipe(id, ingredient, result, exp))
    }

    class Result(
        id: ResourceLocation,
        recipe: SugarFurnaceRecipe,
    ) : CodecFinishedRecipe<SugarFurnaceRecipe>(id, recipe) {
        override val codec = SugarFurnaceRecipe.CODEC
        override fun getType() = SugarFurnaceRecipe.Serializer
    }
}