package cn.breadnicecat.candycraftce.core.recipe

import com.google.gson.JsonObject
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe

/**
 * Created by NiceCat on 2026/3/22.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
interface CodecSerializer<R : Recipe<*>> : RecipeSerializerExt<R> {
    val codec: Codec<R>
    override fun toJson(json: JsonObject, recipe: R) {
        codec.encode(recipe, JsonOps.INSTANCE, json)
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
        val tag = buffer.readNbt()
        return codec.decode(NbtOps.INSTANCE, tag).result().orElseThrow().first
    }

    override fun toNetwork(buffer: FriendlyByteBuf, recipe: R) {
        val tag = codec.encodeStart(NbtOps.INSTANCE, recipe).result().orElseThrow()
        buffer.writeNbt(tag as CompoundTag)
    }
}

open class CodecSerializerImpl<R : Recipe<*>>(override val codec: Codec<R>) : CodecSerializer<R>