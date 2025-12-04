package cn.breadnicecat.candycraftce.utils

open class Arguments(args: HashMap<String, Any>) {
    internal val args = HashMap(args)

    val size: Int get() = args.size

    fun isEmpty(): Boolean = args.isEmpty()

    operator fun contains(key: String): Boolean = key in args
    operator fun iterator() = args.iterator()
    fun forEach(action: (Map.Entry<String, Any>) -> Unit) = args.forEach(action)

    operator fun <T> get(key: String): T = getOptional(key) ?: error("`$key` is missing from the arguments")

    @Suppress("UNCHECKED_CAST")
    fun <T> getOptional(key: String): T? = args[key] as? T


    class Builder : Arguments(HashMap()) {
        fun remove(key: String): Any? = args.remove(key)
        fun putAll(from: Arguments) = args.putAll(from.args)
        fun clear() = args.clear()
        fun put(key: String, value: Any) = args.put(key, value)
        operator fun set(key: String, value: Any) = put(key, value)
        fun build() = Arguments(args)
    }
}