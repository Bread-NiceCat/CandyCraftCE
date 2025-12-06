import cn.breadnicecat.candycraftce.utils.BitMap
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import org.junit.Test
import kotlin.random.Random

class BitmapTest {
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

        for (i in 0 until 1000) {
            assert(bm[i] == set.contains(i)) { "Bitmap[$i] is ${bm[i]}" }
        }
        println(bm)

    }
}