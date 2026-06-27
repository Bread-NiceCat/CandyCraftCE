package cn.breadnicecat.candycraftce.core.recipe

import cn.breadnicecat.candycraftce.utils.CUtils.merge
import cn.breadnicecat.candycraftce.utils.CodecUtils.decodeFromNetwork
import cn.breadnicecat.candycraftce.utils.CodecUtils.encodeToNetwork
import com.google.gson.JsonObject
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe

/**
 * Created by NiceCat on 2026/3/22.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
abstract class CodecSerializer<R : Recipe<*>> : RecipeSerializerExt<R> {
    abstract val codec: Codec<R>
    override fun toJson(json: JsonObject, recipe: R) {
        json.merge(codec.encodeStart(JsonOps.INSTANCE, recipe).result().orElseThrow().asJsonObject)
    }

    override fun fromJson(
        recipeId: ResourceLocation,
        serializedRecipe: JsonObject,
    ): R {
        return codec.decode(JsonOps.INSTANCE, serializedRecipe).result().orElseThrow().first
    }

    override fun fromNetwork(
        recipeId: ResourceLocation,
        buffer: FriendlyByteBuf,
    ): R {
        return codec.decodeFromNetwork(buffer).result().orElseThrow()
    }

    override fun toNetwork(buffer: FriendlyByteBuf, recipe: R) {
        codec.encodeToNetwork(recipe, buffer)
    }
}

open class CodecSerializerImpl<R : Recipe<*>>(override val codec: Codec<R>) : CodecSerializer<R>()