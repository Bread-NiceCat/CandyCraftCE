package cn.breadnicecat.candycraftce.data.providers


import cn.breadnicecat.candycraftce.core.item.CItems
import cn.breadnicecat.candycraftce.core.tag.CTags
import cn.breadnicecat.candycraftce.data.CDataUtils.checkDataRunning
import cn.breadnicecat.candycraftce.data.recipes.SugarFurnaceRecipeBuilder
import cn.breadnicecat.candycraftce.utils.CUtils.key
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
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
    }

    override fun buildRecipes(exporter: Consumer<FinishedRecipe>) {
        builtinRecipes(exporter)
    }

    internal fun builtinRecipes(exporter: Consumer<FinishedRecipe>) {
        val helper = Helper(exporter)

        helper.use(CItems.pez) {
            SugarFurnaceRecipeBuilder.direct(
                id(),
                Ingredient.of(CTags.ore_pez.item),
                defaultInstance,
                0.5f
            ).build()
        }
    }

}

fun FinishedRecipe.save(exporter: Consumer<FinishedRecipe>) = exporter.accept(this)

//用来自动生成recipe id等
internal class Helper(val exporter: Consumer<FinishedRecipe>) {
    //当前路径
    private var current: ResourceLocation? = null

    private var postfix: Int = 0

    private var owner0: ItemLike? = null

    //记录所有生成过的路径
    private var paths = mutableSetOf<ResourceLocation>()

    //用来记忆上次该路径的postfix
    private val memory: HashMap<ResourceLocation, Int> = HashMap()

    val defaultInstance: ItemStack get() = owner.defaultInstance
    val owner: Item get() = owner0?.asItem() ?: error("No current item")

    //item==null时置空
    fun update(item: ItemLike?) {
        if (current != null) memory[current!!] = postfix

        owner0 = item
        current = item?.asItem()?.key
        postfix = memory.getOrDefault(current, 0)
    }

    fun <I : ItemLike> use(owner: I, scope: Helper.(I) -> FinishedRecipe) {
        update(owner)
        val recipe = scope(owner)
        update(null)
        recipe.save(exporter)
    }

    fun id(prefix: String = ""): ResourceLocation {
        var cur = current?.withPrefix(prefix) ?: error("No current item")
        while (cur in paths) {
            cur = cur.withSuffix("_${++postfix}")
        }
        paths.add(cur)
        return cur
    }

    fun has(): InventoryChangeTrigger.TriggerInstance = RecipeProvider.has(owner)
}