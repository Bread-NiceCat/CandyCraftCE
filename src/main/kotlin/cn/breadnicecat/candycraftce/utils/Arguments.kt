package cn.breadnicecat.candycraftce.utils

open class Arguments(args: HashMap<String, Any>) {
    companion object {
        fun of(vararg pairs: Pair<String, Any>): Arguments = Builder().apply { putAll(*pairs) }.build()
        fun builder(bui: Builder.() -> Unit): Arguments {
            return Builder().apply(bui)
        }
    }

    fun interface Property {
        fun get(): Any
    }

    protected val args = HashMap(args)

    val size: Int get() = args.size

    fun isEmpty(): Boolean = args.isEmpty()

    operator fun contains(key: String): Boolean = key in args
    operator fun iterator() = args.iterator()
    fun forEach(action: (Map.Entry<String, Any>) -> Unit) = args.forEach(action)

    operator fun <T> get(key: String): T = getOptional(key) ?: error("`$key` is missing from the arguments")
    operator fun <T> get(key: String, defaultValue: T) = getOptional(key) ?: defaultValue

    @Suppress("UNCHECKED_CAST")
    fun <T> getOptional(key: String): T? {
        return when (val value = args[key]) {
            is Property -> value.get() as? T
            else -> value as? T
        }
    }

    class Builder(map: HashMap<String, Any>) : Arguments(map) {
        constructor() : this(HashMap())

        fun remove(key: String): Any? = args.remove(key)
        fun putAll(from: Arguments) = args.putAll(from.args)
        fun putAll(vararg pairs: Pair<String, Any>) = args.putAll(pairs)
        fun clear() = args.clear()
        fun put(key: String, value: Any) = args.put(key, value)
        operator fun set(key: String, value: Any) = put(key, value)
        infix fun String.to(value: Any) = set(this, value)
        fun add(key: String, value: Any): Builder {
            put(key, value)
            return this
        }

        fun build() = Arguments(args)
    }
}