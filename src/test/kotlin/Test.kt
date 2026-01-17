import cn.breadnicecat.candycraftce.utils.AxisSet
import cn.breadnicecat.candycraftce.utils.BitMap
import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.descartes
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import net.minecraft.core.BlockPos
import org.junit.Test
import kotlin.random.Random

class Test {
    class A<B : SuperB> {
        @Suppress("UNCHECKED_CAST")
        fun <NB : SuperB> copyTo(factory: () -> NB): A<NB> {
            return this as A<NB>
        }
    }

    open class SuperB
    class SubB : SuperB()

    @Test
    fun ttest() {
        val a = A<SuperB>()
        a.copyTo { SuperB() }
        a.copyTo { SubB() }
    }

    @Test
    fun compose() {
        val l = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val e = listOf<Int>()
        val s = listOf(1)
        l.descartes().forEach { (a, b) -> println("list: $a -> $b") }
        s.descartes().forEach { (a, b) -> println("single: $a -> $b") }
        e.descartes().forEach { (a, b) -> println("empty: $a -> $b") }

    }

    @Test
    fun neibTest() {
        CUtils.getNeighbourPos(BlockPos(0, 0, 0))
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