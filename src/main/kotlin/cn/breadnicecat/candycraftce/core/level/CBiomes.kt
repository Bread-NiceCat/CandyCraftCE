package cn.breadnicecat.candycraftce.core.level

import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome

/**
 * Created in 2024/8/2 下午6:33
 * Project: candycraftce
 * 
 * @author [Bread_NiceCat](https://github.com/Bread-Nicecat)
 * 

 */
object CBiomes {
    val caramel_forest = create("caramel_forest")
    val chocolate_forest = create("chocolate_forest")
    val deep_sugar_ocean = create("deep_sugar_ocean")
    val enchanted_forest = create("enchanted_forest")
    val ice_cream_forest = create("ice_cream_forest")
    val ice_cream_plains = create("ice_cream_plains")
    val pudding_plains = create("pudding_plains")
    val sugar_ocean = create("sugar_ocean")
    val sugar_river = create("sugar_river")
    val lukewarm_sugar_ocean = create("lukewarm_sugar_ocean")
    val warm_sugar_ocean = create("warm_sugar_ocean")
    val sugar_beach = create("sugar_beach")

    val dungeons = create("dungeons")
    private fun create(loc: String): ResourceKey<Biome> {
        return ResourceKey.create(Registries.BIOME, loc.modLoc())
    }
}
