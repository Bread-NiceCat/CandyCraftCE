package cn.breadnicecat.candycraftce.utils

import java.util.function.Consumer
import java.util.function.Supplier

/**
 * @author [Bread_NiceCat](https://gitee.com/Bread_NiceCat)
 * @date 2023/1/22 13:21
 *
 * Wisdom Util ;)
 */
sealed interface Accessor<T> : Consumer<T>, Supplier<T> {
    @Deprecated("Use `set` instead", ReplaceWith("set"))
    override fun accept(t: T) = set(t)

    fun set(t: T)
    fun frozen(): Accessor<T> = Frozen(this)
    fun synchronized(): Accessor<T> = SynchronizedAccessor(this)

    companion object {
        fun <T> access(getter: Supplier<T>, setter: Consumer<T>): Accessor<T> {
            return LambdaAccessor(getter, setter)
        }

        fun <T> of(value: T): Accessor<T> = AccessorImpl(value)
    }
}

private class AccessorImpl<T>(private var value: T) : Accessor<T> {

    override fun get(): T = value

    override fun set(t: T) {
        value = t
    }
}

private class Frozen<T>(private val su: Accessor<T>) : Accessor<T> {

    override fun get(): T = su.get()

    override fun set(t: T) {
        throw UnsupportedOperationException("frozen")
    }

    override fun frozen(): Accessor<T> = this
}

private class LambdaAccessor<T>(
    private val getter: Supplier<T>,
    private val setter: Consumer<T>,
) : Accessor<T> {
    override fun get() = getter.get()

    override fun set(t: T) = setter.accept(t)
}

private class SynchronizedAccessor<T>(val su: Accessor<T>) : Accessor<T> {
    override fun set(t: T) {
        synchronized(this) {
            su.set(t)
        }
    }

    override fun get(): T {
        synchronized(this) {
            return su.get()
        }
    }

    override fun synchronized(): Accessor<T> = this
}
