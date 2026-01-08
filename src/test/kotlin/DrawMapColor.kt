import net.minecraft.world.level.material.MapColor
import java.awt.Color
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JLabel
import kotlin.reflect.full.staticProperties
import kotlin.reflect.jvm.isAccessible


/**
 * Created by NiceCat on 2025/12/18.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object DrawMapColor {
    fun Color.inverse(): Color {
        return Color(255 - red, 255 - green, 255 - blue)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val colors = MapColor::class.staticProperties
            .asSequence()
            .map { it.isAccessible = true; it }
            .filter { it.get() is MapColor }
            .map {
                val color = it.get() as MapColor
                "${color.id}.${it.name}" to Color(color.col)
            }
            .associate { it }

        println(colors)
        val frame = JFrame().apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setLocationRelativeTo(null)
            layout = GridLayout(colors.size / 4, 0, 2, 2)
            colors.forEach { (name, color) ->
                add(JLabel(name, JLabel.CENTER).apply {
                    foreground = color.inverse()
                    background = color
                    isOpaque = true
                    border = BorderFactory.createLineBorder(color.inverse())
                    pack()
                })
            }
            pack()
            isVisible = true
        }
    }
}