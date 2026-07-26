package cn.breadnicecat.candycraftce.client

import cn.breadnicecat.candycraftce.core.level.CBiomes.caramel_forest
import cn.breadnicecat.candycraftce.core.level.CBiomes.chocolate_forest
import cn.breadnicecat.candycraftce.core.level.CBiomes.deep_sugar_ocean
import cn.breadnicecat.candycraftce.core.level.CBiomes.dungeons
import cn.breadnicecat.candycraftce.core.level.CBiomes.enchanted_forest
import cn.breadnicecat.candycraftce.core.level.CBiomes.ice_cream_forest
import cn.breadnicecat.candycraftce.core.level.CBiomes.ice_cream_plains
import cn.breadnicecat.candycraftce.core.level.CBiomes.lukewarm_sugar_ocean
import cn.breadnicecat.candycraftce.core.level.CBiomes.pudding_plains
import cn.breadnicecat.candycraftce.core.level.CBiomes.sugar_beach
import cn.breadnicecat.candycraftce.core.level.CBiomes.sugar_ocean
import cn.breadnicecat.candycraftce.core.level.CBiomes.sugar_river
import cn.breadnicecat.candycraftce.core.level.CBiomes.warm_sugar_ocean
import cn.breadnicecat.candycraftce.utils.CUtils.rgb
import cn.breadnicecat.candycraftce.utils.LinearGradient
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.core.Holder
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.synth.NormalNoise
import net.minecraft.world.phys.Vec3
import kotlin.jvm.optionals.getOrNull


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

    /**
     * @return #b0ecff 淡蓝色 #b0b0ff 淡紫色 #a376da 深紫色
     */
    fun getEnchantColor(pos: Vec3): Int {
        val d0 = noise.getValue(pos.x, pos.y, pos.z)

        val r = d0 + 0.5 / (0.5 + 0.5)
        return enchant_color.getColor(r.toFloat()).rgb
    }

    fun getPuddingColor(biome: Holder<Biome>, pos: Vec3): Int {
        return when (biome.unwrapKey().getOrNull()) {
            caramel_forest -> 11557928
            chocolate_forest -> 15641275
            enchanted_forest -> getEnchantColor(pos)
            ice_cream_forest -> 16768494
            ice_cream_plains -> 16768494
            pudding_plains -> 15641275
            sugar_beach -> 16030419
            deep_sugar_ocean -> 12623044
            sugar_ocean -> 16030419
            lukewarm_sugar_ocean -> 16360666
            warm_sugar_ocean -> 16360666
            sugar_river -> 16030419
            dungeons -> 0x808080//灰色
            else -> getDefaultPuddingColor()
        }
    }
}