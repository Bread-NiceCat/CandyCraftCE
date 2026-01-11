package cn.breadnicecat.candycraftce.core.level.foliage_placer

import cn.breadnicecat.candycraftce.core.level.CFoliagePlacers
import com.mojang.serialization.Codec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer

/**
 * Created in 2024/7/29 上午9:51
 * Project: candycraftce
 *
 * @author Bread_NiceCat(https://github.com/Bread-Nicecat)
 *
 */
class FancyCaramelFoliagePlacer : FoliagePlacer(ConstantInt.of(3), ConstantInt.of(1)) {
    companion object {
        val CODEC: Codec<FancyCaramelFoliagePlacer> = Codec.unit { FancyCaramelFoliagePlacer() }
    }

    override fun type() = CFoliagePlacers.fancy_caramel

    override fun createFoliage(
        level: LevelSimulatedReader, setter: FoliageSetter, random: RandomSource, config: TreeConfiguration,
        maxFreeTreeHeight: Int, attachment: FoliageAttachment,
        foliageHeight: Int, foliageRadius: Int, offset: Int,
    ) {
        val pos = attachment.pos().mutable()
        setter.set(pos, config.foliageProvider.getState(random, pos))
        var last = -1
        var zeroed = false
        for (y in 0..<foliageHeight) {
            var r: Int
            do {
                r = if (y == 0) {
                    //第一行树叶最小半径2
                    random.nextInt(3) + 2 //[2,5)
                } else if (y > foliageHeight - 3 && y > 3) {
                    //最后两行树叶最大半径2
                    random.nextInt(3) //[0,3)
                } else {
                    random.nextInt(5) //[0,5)
                }
            } while (r == last || (r == 0 && zeroed)) //只允许出现一次0
            last = r
            if (r == 0) zeroed = true


            for (x in -r..<r + 1) {
                for (z in -r..<r + 1) {
                    if (x == 0 && z == 0) continue
                    val po: BlockPos = pos.offset(x, 0, z)
                    setter.set(po, config.foliageProvider.getState(random, po))
                }
            }
            pos.move(Direction.DOWN)
        }
    }

    override fun foliageHeight(random: RandomSource, height: Int, config: TreeConfiguration): Int {
        return height - 1
    }

    override fun shouldSkipLocation(
        random: RandomSource,
        localX: Int,
        localY: Int,
        localZ: Int,
        range: Int,
        large: Boolean,
    ): Boolean {
        return false
    }
}
