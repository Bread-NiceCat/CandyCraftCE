package cn.breadnicecat.candycraftce.core.recipe

import com.google.gson.JsonObject
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer

interface RecipeSerializerExt<R : Recipe<*>> : RecipeSerializer<R> {
    fun toJson(json: JsonObject, recipe: R)
}
