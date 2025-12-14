package cn.breadnicecat.candycraftce.utils

abstract class TimeUnit {
    abstract val tick: Int
    abstract val second: Float
    open val millis: Long by lazy { (this.second * 1000).toLong() }

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