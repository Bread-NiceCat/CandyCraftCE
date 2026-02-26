import cn.breadnicecat.candycraftce.utils.CUtils.rgb
import cn.breadnicecat.candycraftce.utils.LinearGradient
import java.awt.Graphics
import java.util.concurrent.Executors
import javax.swing.JFrame

/**
 * Created by NiceCat on 2026/1/18.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object LinearColor {
    @JvmStatic
    fun main(args: Array<String>) {
        linear()
    }

    val color = LinearGradient {
        0xb0ecff.rgb % 0f
        0xa376da.rgb % .5f
        0xb0b0ff.rgb % 1f
    }

    fun linear() {
        val exec = Executors.newFixedThreadPool(1)
        object : JFrame() {
            init {
                setSize(500, 500)
                defaultCloseOperation = EXIT_ON_CLOSE
            }

            override fun paint(g: Graphics) {
                super.paint(g)
                val w = size.width
                val h = size.height
                for (y in 0..w) {
                    for (x in 0..h) {
//                        val d0 = random.nextGaussian().toFloat() - 0.5f
//                        val ratio = d0 + 0.4f / (0.4f + 0.4f)

                        val ratio = (x.toFloat() / w + y.toFloat() / h) / 2f
                        val c = color.getColor(ratio)

                        g.color = c
                        g.drawLine(x, y, x, y)
                        exec.execute {
                            val progress = (100f * (x * h + y) / (w * h))
                            print("($x,$y)=${c.red},${c.green},${c.blue} (ratio=$ratio) $progress%\r")
                        }
                    }
                }
                exec.execute {
                    println()
                }
                repaint()
            }
        }.apply {
            isVisible = true
        }
    }
}
