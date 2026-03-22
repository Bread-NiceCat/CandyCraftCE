package cn.breadnicecat.candycraftce.core.level

import cn.breadnicecat.candycraftce.core.level.foliage_placer.CandiedCherryFoliagePlacer
import cn.breadnicecat.candycraftce.core.level.foliage_placer.FancyCaramelFoliagePlacer
import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.CUtils.register
import com.mojang.serialization.Codec
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType

object CFoliagePlacers {
    init {
        CUtils.sign()
    }

    val candied_cherry = register("candied_cherry_foliage_placer", CandiedCherryFoliagePlacer.CODEC)
    val fancy_caramel = register("fancy_caramel_foliage_placer", FancyCaramelFoliagePlacer.CODEC)
    private fun <P : FoliagePlacer> register(id: String, codec: Codec<P>): FoliagePlacerType<P> {
        val key = id.modLoc()
        CUtils.logRegister("FoliagePlacer", key)

        val type = FoliagePlacerType(codec)
        BuiltInRegistries.FOLIAGE_PLACER_TYPE.register(id.modLoc(), type)
        return type
    }
}