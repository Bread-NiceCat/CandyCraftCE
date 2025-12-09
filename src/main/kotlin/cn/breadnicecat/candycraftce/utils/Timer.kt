package cn.breadnicecat.candycraftce.utils

import net.minecraft.Util
import java.util.concurrent.TimeUnit

/**
 * Created by NiceCat on 2025/12/8.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class Timer(start: Boolean = true) {
    companion object {
        private val now get() = Util.timeSource.asLong

        inline fun timing(unit: TimeUnit = TimeUnit.MILLISECONDS, crossinline action: () -> Unit): Long {
            val t = Timer(start = false)
            t.timing { action() }
            return t.getTime(unit)
        }
    }

    private var accumulated = 0L

    private var start = -1L
    private val nano get() = if (paused) accumulated else accumulated + (now - start)
    val paused get() = start == -1L

    init {
        if (start) start()
    }

    inline fun <R> timing(crossinline action: () -> R): R {
        if (paused) start()
        val r = action()
        stop()
        return r
    }

    fun startIfPaused() {
        if (paused) {
            start()
        }
    }

    fun start() {
        require(paused) { "Timer is not paused" }
        start = now
    }

    fun stop() {
        require(!paused) { "Timer is already paused" }
        accumulated += now - start
        start = -1L
    }

    fun getTime(unit: TimeUnit = TimeUnit.MILLISECONDS) = unit.convert(nano, TimeUnit.NANOSECONDS)
}