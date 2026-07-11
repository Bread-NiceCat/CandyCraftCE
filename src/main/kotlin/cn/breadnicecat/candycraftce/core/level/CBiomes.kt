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
    var CARAMEL_FOREST = create("caramel_forest")
    var CHOCOLATE_FOREST = create("chocolate_forest")
    var DEEP_SUGAR_OCEAN = create("deep_sugar_ocean")
    var ENCHANTED_FOREST = create("enchanted_forest")
    var ICE_CREAM_FOREST = create("ice_cream_forest")
    var ICE_CREAM_PLAINS = create("ice_cream_plains")
    var PUDDING_PLAINS = create("pudding_plains")
    var SUGAR_OCEAN = create("sugar_ocean")
    var SUGAR_RIVER = create("sugar_river")
    private fun create(loc: String): ResourceKey<Biome> {
        return ResourceKey.create(Registries.BIOME, loc.modLoc())
    }
}
