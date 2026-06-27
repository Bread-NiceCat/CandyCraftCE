package cn.breadnicecat.candycraftce.utils

import cn.breadnicecat.candycraftce.utils.CLogUtils.clog
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 便于垃圾回收,要求将值**唯一**储存于该类中,方便gc
 * */

class ImmediateScope {
    private var all: HashMap<Immediate<*>, Any>? = HashMap()
    fun isScopeValid() = all != null
    fun invalidateScope() {
        if (all == null) return
        clog.info("Invalidating ${all!!.size} Immediate(s)")
        all = null
    }

    fun <E : Any> createImmediate(e: E) = Immediate(e)


    inner class Immediate<E : Any> internal constructor(
        e: E,
    ) : ReadOnlyProperty<Any, E?> {
        init {
            if (isScopeValid()) {
                all!![this] = e
            }
        }

        fun isValid() = getOrNull() != null
        fun get(): E = getOrNull() ?: error("invalidated value!")

        @Suppress("UNCHECKED_CAST")
        fun getOrNull(): E? = all?.get(this) as? E
        fun invalidate() {
            all?.remove(this)
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): E? = getOrNull()

    }
}
