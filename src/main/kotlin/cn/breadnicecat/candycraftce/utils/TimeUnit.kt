package cn.breadnicecat.candycraftce.utils

abstract class TimeUnit {
    abstract val tick: Int
    abstract val second: Float
    open val millis: Long by lazy { (this.second * 1000).toLong() }

    companion object {
        val Int.tick get() = Tick(this)
        val Float.second get() = Second(this)
        val Int.second get() = Second(this.toFloat())
        val Long.ms get() = Millis(this)
        val Int.ms get() = Millis(this.toLong())

        val tps = 1.second.tick
        val spt = 1.tick.second
    }

    class Tick(override val tick: Int) : TimeUnit() {
        override val second by lazy { this.tick / 20f }
    }


    class Second(override val second: Float) : TimeUnit() {
        override val tick by lazy { (this.second * 20).toInt() }
    }

    class Millis(override val millis: Long) : TimeUnit() {
        override val tick by lazy { (this.millis / 50).toInt() }
        override val second by lazy { millis / 1000f }

    }

}