package cn.breadnicecat.candycraftce.utils

import cn.breadnicecat.candycraftce.utils.CUtils.clog
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 便于垃圾回收,要求将值**唯一**储存于该类中
 * */
class V<E : Any>(e: E) : ReadOnlyProperty<Any, E> {
    companion object {
        private var all: HashSet<V<*>>? = HashSet<V<*>>()
        fun invalidateAll() {
            clog.info("Invalidating all V")
            all!!.forEach { it.invalidate() }
            all = null
        }

        fun <E : Any> E.v() = V(this)
    }

    init {
        all!!.add(this)
    }

    private var e: E? = e

    fun isValid() = e != null
    fun get(): E = e ?: error("invalidated value!")
    fun getOrNull(): E? = e
    fun invalidate() {
        e = null
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): E = get()

}