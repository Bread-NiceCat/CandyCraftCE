package cn.breadnicecat.candycraftce.utils

import java.awt.Color
import java.util.*

/**
 * Created by NiceCat on 2026/1/18.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * example
 * <pre>
 *  LinearGradient {
 *      0xb0ecff.rgb % 0f
 *      0xa376da.rgb % .5f  //50%时是该颜色
 *      0xb0b0ff.rgb % 1f
 *  }
 * </pre>
 */
class LinearGradient(vararg colors: LinearGradientColor) {

    constructor(action: GradientScope.() -> Unit) : this(*GradientScope().apply(action).get())

    private val colors = colors.associateBy { it.ratio }.toSortedMap()

    init {
        require(this.colors.size >= 2) { "At least two colors are required." }
        for (entry in this.colors.values) {
            require(entry.ratio in 0f..1f) { "Ratio must be between 0 and 1." }
        }
    }

    fun getColor(ratio: Float): Color {
        if (ratio in colors) {
            return colors[ratio]!!.color
        } else if (ratio < colors.firstKey()) {
            return colors.firstEntry().value.color
        } else if (ratio > colors.lastKey()) {
            return colors.lastEntry().value.color
        } else {
            //前前一个
            var prev1 = colors.firstEntry()
            //前一个
            var prev2 = colors.firstEntry()
            for (entry in colors.entries) {
                prev1 = prev2
                prev2 = entry
                if (entry.key > ratio) {
                    break
                }
            }
            val r = (ratio - prev1.key) / (prev2.key - prev1.key)
            val c1 = prev1.value.color
            val c2 = prev2.value.color
            return Color(
                (c1.red + (c2.red - c1.red) * r).toInt(),
                (c1.green + (c2.green - c1.green) * r).toInt(),
                (c1.blue + (c2.blue - c1.blue) * r).toInt(),
            )
        }
    }
}

class GradientScope internal constructor() {
    private val colors = LinkedList<LinearGradientColor>()
    operator fun Color.rem(ratio: Number) {
        colors.add(LinearGradientColor(this, ratio.toFloat()))
    }

    operator fun String.rem(ratio: Number) {
        require(this.startsWith("#") || this.startsWith("0x"))
        Color.decode(this) % ratio
    }

    fun get(): Array<LinearGradientColor> = colors.toTypedArray()
}

data class LinearGradientColor(val color: Color, val ratio: Float)
