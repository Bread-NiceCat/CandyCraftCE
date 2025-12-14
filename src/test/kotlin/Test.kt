import cn.breadnicecat.candycraftce.utils.AxisSet
import cn.breadnicecat.candycraftce.utils.BitMap
import cn.breadnicecat.candycraftce.utils.ModUtils
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import net.minecraft.core.BlockPos
import org.junit.Test
import kotlin.random.Random

class Test {
    @Test
    fun neibTest() {
        ModUtils.getNeighbourPos(BlockPos(0, 0, 0))
            .forEach { (d, p) ->
                println("$d: $p")
            }
    }

    @Test
    fun bitmap() {
        val bm = BitMap(1000)
        val cnt = 100
        val set = IntOpenHashSet()
        repeat(cnt) {
            val t = Random.nextInt(1000)
            set.add(t)
            bm[t] = true
        }

        val rbm = bm.reverseCopy()
        for (i in 0 until 1000) {
            assert(bm[i] == set.contains(i)) { "Bitmap[$i] is ${bm[i]}" }
            assert(rbm[i] != set.contains(i)) { "reversed Bitmap[$i] is ${rbm[i]}" }
        }

        println(bm)
        println(rbm)

    }

    @Test
    fun axes() {
        val ax = AxisSet()
        val nax = ax.setX()
        println(nax)
    }
}