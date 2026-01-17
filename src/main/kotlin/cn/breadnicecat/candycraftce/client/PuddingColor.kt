package cn.breadnicecat.candycraftce.client

import cn.breadnicecat.candycraftce.utils.CUtils.rgb
import cn.breadnicecat.candycraftce.utils.LinearGradient
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.core.Holder
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.synth.NormalNoise
import net.minecraft.world.phys.Vec3
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by NiceCat on 2025/12/9.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
@Environment(EnvType.CLIENT)
object PuddingColor {
    private val noise: NormalNoise = NormalNoise.create(RandomSource.create(8526L), -7, 1.0)
    private val enchant_color = LinearGradient {
        0xb0ecff.rgb % 0f
        0xa376da.rgb % .5f
        0xb0b0ff.rgb % 1f
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

    val x = ConcurrentHashMap<String, Int>()

    /**
     * @return #b0ecff 淡蓝色 #b0b0ff 淡紫色 #a376da 深紫色
     */
    fun getEnchantColor(pos: Vec3): Int {
        val d0 = noise.getValue(pos.x, pos.y, pos.z)

        val d0s = "%.2f".format(d0)
        if (!x.containsKey(d0s)) {
            x[d0s] = 1
        } else {
            x.computeIfPresent(d0s) { _, v -> v + 1 }
        }

        val r = d0 + 0.5 / (0.5 + 0.5)
        return enchant_color.getColor(r.toFloat()).rgb
    }

    fun getPuddingColor(biome: Holder<Biome>, pos: Vec3): Int {
        return getDefaultPuddingColor()
    }
}