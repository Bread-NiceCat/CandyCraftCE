package cn.breadnicecat.candycraftce.core.level.foliage_placer

import cn.breadnicecat.candycraftce.core.level.CFoliagePlacers
import com.mojang.serialization.Codec
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer

/**
 * Created in 2024/3/24 9:36
 * Project: candycraftce
 *
 * @author Bread_NiceCat(https://github.com/BreadNiceCat)
 *
 *
 */
class CandiedCherryFoliagePlacer : FoliagePlacer(ConstantInt.of(2), ConstantInt.of(1)) {
    companion object {
        val CODEC: Codec<CandiedCherryFoliagePlacer> = Codec.unit { CandiedCherryFoliagePlacer() }
    }

    override fun type() = CFoliagePlacers.candied_cherry

    override fun createFoliage(
        level: LevelSimulatedReader,
        setter: FoliageSetter,
        random: RandomSource,
        config: TreeConfiguration,
        maxFreeTreeHeight: Int,
        attachment: FoliageAttachment,
        foliageHeight: Int,
        foliageRadius: Int,
        offset: Int,
    ) {
        val pos = attachment.pos().mutable()
        setter.set(pos, config.foliageProvider.getState(random, pos))
        //从上往下走
        for (y in 0..<foliageHeight) {
            pos.move(Direction.DOWN)
            //是否跳角
            //最上面一行肯定跳
            val skipCorner = y % 2 == 0
            for (x in -1..1) {
                for (z in -1..1) {
                    //最中间忽略
                    if (x == 0 && z == 0) continue
                    var st = 0
                    if (x == -1 || x == 1) st++
                    if (z == -1 || z == 1) st++
                    //不是角 或者 是角且不跳角
                    if (st != 2 || !skipCorner) {
                        val setPos = pos.offset(x, 0, z)
                        setter.set(setPos, config.foliageProvider.getState(random, setPos))
                    }
                }
            }
        }
    }

    override fun foliageHeight(random: RandomSource, height: Int, config: TreeConfiguration): Int {
        return height - 1
    }

    override fun shouldSkipLocation(
        random: RandomSource, localX: Int, localY: Int, localZ: Int,
        range: Int, large: Boolean,
    ): Boolean {
        return false
    }
}