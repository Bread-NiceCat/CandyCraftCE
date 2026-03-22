package cn.breadnicecat.candycraftce.core.recipe.recipes

import cn.breadnicecat.candycraftce.core.block.blockentities.LicoriceFurnaceBE
import cn.breadnicecat.candycraftce.core.recipe.CRecipeTypes
import cn.breadnicecat.candycraftce.core.recipe.CodecSerializerImpl
import cn.breadnicecat.candycraftce.utils.CUtils
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.Level

class SugarFurnaceRecipe(
    @JvmField val id: ResourceLocation,//与getId冲突
    val ingredient: Ingredient,
    val result: ItemStack,
    val exp: Float,
) : Recipe<LicoriceFurnaceBE> {
    companion object {
        val CODEC: Codec<SugarFurnaceRecipe> = RecordCodecBuilder.create { instance ->
            instance.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter { it.id },
                CUtils.ingredientCodec.fieldOf("ingredient").forGetter { it.ingredient },
                ItemStack.CODEC.fieldOf("result").forGetter { it.result },
                Codec.FLOAT.fieldOf("exp").forGetter { it.exp },
            ).apply(instance, ::SugarFurnaceRecipe)
        }
    }

    override fun matches(container: LicoriceFurnaceBE, level: Level): Boolean {
        return ingredient.test(container.getItem(LicoriceFurnaceBE.INPUT_SLOT))
    }

    override fun assemble(container: LicoriceFurnaceBE, registryAccess: RegistryAccess): ItemStack {
        return result.copy()
    }

    override fun getResultItem(registryAccess: RegistryAccess): ItemStack {
        return result.copy()
    }

    override fun getId(): ResourceLocation = id

    override fun canCraftInDimensions(width: Int, height: Int): Boolean = true

    override fun getSerializer() = CRecipeTypes.sugar_furnace_type.second

    override fun getType() = CRecipeTypes.sugar_furnace_type.first

    object Serializer : CodecSerializerImpl<SugarFurnaceRecipe>(CODEC)
}
