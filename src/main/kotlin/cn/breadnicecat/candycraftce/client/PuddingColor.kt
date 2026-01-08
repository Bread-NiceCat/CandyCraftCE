package cn.breadnicecat.candycraftce.client

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.synth.NormalNoise

/**
 * Created by NiceCat on 2025/12/9.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
@Environment(EnvType.CLIENT)
object PuddingColor {
    private val noise: NormalNoise = NormalNoise.create(RandomSource.create(8526L), -7, 1.0)

    /**
     * @return #b0ecff 淡蓝色 #b0b0ff 淡紫色 #a376da 深紫色
     */
    fun getEnchantColor(x: Double, z: Double): Int {
        val d0 = noise.getValue(x, 0.0, z)
        return if (d0 < -0.4) 0xb0ecff else (if (d0 < 0.4) 0xb0b0ff else 0xa376da)
    }

    /**
     * @return #dd99aa 粉色
     */
    fun getDefaultPuddingColor(): Int {
        return 0xdd99aa
    }

    fun getDefaultEnchantColor(): Int {
        return 0xb0ecff
    }

    fun getColor(biome: Holder<Biome>, pos: BlockPos): Int {
        return getDefaultPuddingColor()
    }
}