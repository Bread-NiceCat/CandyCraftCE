package cn.breadnicecat.candycraftce.core.recipe

import cn.breadnicecat.candycraftce.core.recipe.recipes.SugarFurnaceRecipe
import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.CUtils.register
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType


object CRecipeTypes {
    init {
        CUtils.sign()
    }

    val sugar_furnace_type = CRecipeTypes.register(
        "sugar_furnace",
        SugarFurnaceRecipe.Serializer
    )
//    val SUGAR_FACTORY_TYPE: RecipeTypeEntry<SugarFactoryRecipe> = CRecipeTypes.register<Recipe<*>>(
//        "sugar_factory",
//        java.util.function.Supplier { SugarFactoryRecipe.Serializer() })


    fun <T : Recipe<*>> register(
        name: String,
        serializer: RecipeSerializerExt<T>,
    ): Pair<RecipeType<T>, RecipeSerializerExt<T>> {
        val id = name.modLoc()
        val type = BuiltInRegistries.RECIPE_TYPE.register(
            id,
            object : RecipeType<T> {
                override fun toString(): String = id.toString()
            })

        val serializer = BuiltInRegistries.RECIPE_SERIALIZER.register(id, serializer)
        return type to serializer
    }

    fun init() {
    }
}
