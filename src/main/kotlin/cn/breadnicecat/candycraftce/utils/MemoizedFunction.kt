package cn.breadnicecat.candycraftce.utils

import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @param compute 原始计算逻辑
 * @param threadSafe 是否线程安全（默认 true，用 ConcurrentHashMap）
 */
class MemoizedFunction<K, V>(
    private val compute: (K) -> V,
    private val threadSafe: Boolean = true,
) : ReadOnlyProperty<Any?, (K) -> V> {

    // 缓存容器：线程安全用 ConcurrentHashMap，非线程安全用 HashMap
    private val cache: MutableMap<K, V> = if (threadSafe) {
        ConcurrentHashMap()
    } else {
        mutableMapOf()
    }

    // 包装后的缓存函数
    private val memoizedFunc: (K) -> V = { key ->
        cache.getOrPut(key) { compute(key) }
    }

    // 委托的核心方法：属性被访问时返回包装后的函数
    override fun getValue(thisRef: Any?, property: KProperty<*>): (K) -> V {
        return memoizedFunc
    }
}


fun <K, V> memoized(
    compute: (K) -> V,
    threadSafe: Boolean = true,
): ReadOnlyProperty<Any?, (K) -> V> {
    return MemoizedFunction(compute, threadSafe)
}
