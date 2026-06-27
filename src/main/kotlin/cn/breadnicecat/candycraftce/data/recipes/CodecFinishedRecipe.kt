package cn.breadnicecat.candycraftce.data.recipes

import cn.breadnicecat.candycraftce.utils.CUtils.merge
import com.google.gson.JsonObject
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe

/**
 * Created by NiceCat on 2026/5/1.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
abstract class CodecFinishedRecipe<R : Recipe<*>>(
    @JvmField val id: ResourceLocation,
    val recipe: R,
) : FinishedRecipe {

    abstract val codec: Codec<R>
    open fun serializeExtraData(json: JsonObject) {}

    final override fun serializeRecipeData(json: JsonObject) {
        val data = codec.encodeStart(JsonOps.INSTANCE, recipe).result().orElseThrow().asJsonObject
        json.merge(data)
        serializeExtraData(json)
    }

    final override fun getId(): ResourceLocation = id
    override fun getAdvancementId(): ResourceLocation? = null
    override fun serializeAdvancement(): JsonObject? = null

}