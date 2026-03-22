package cn.breadnicecat.candycraftce.utils

abstract class MCTimeUnit {
    abstract val toTick: Int
    abstract val toSecond: Float
    open val toMillis: Long by lazy { (this.toSecond * 1000).toLong() }

    companion object {
        val Int.tick get() = Tick(this)
        val Float.second get() = Second(this)
        val Int.second get() = Second(this.toFloat())
        val Long.ms get() = Millis(this)
        val Int.ms get() = Millis(this.toLong())

        val tps = 1.second.toTick
        val spt = 1.tick.toSecond
    }

    class Tick(override val toTick: Int) : MCTimeUnit() {
        override val toSecond by lazy { this.toTick / 20f }
    }


    class Second(override val toSecond: Float) : MCTimeUnit() {
        override val toTick by lazy { (this.toSecond * 20).toInt() }
    }

    class Millis(override val toMillis: Long) : MCTimeUnit() {
        override val toTick by lazy { (this.toMillis / 50).toInt() }
        override val toSecond by lazy { toMillis / 1000f }

    }

}