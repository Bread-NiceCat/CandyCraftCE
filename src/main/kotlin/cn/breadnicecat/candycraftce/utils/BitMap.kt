package cn.breadnicecat.candycraftce.utils

class BitMap private constructor(
    private val bits: ByteArray,
    private val size: Int,
    private var reversed: Boolean = false,
) {
    //(总位数 + 7) / 8（向上取整）
    constructor(size: Int) : this(ByteArray((size + 7) / 8), size)
    private constructor(other: BitMap, reverse: Boolean = false) : this(other.bits.copyOf(), other.size, reverse)

    init {
        require(size > 0)
    }

    operator fun set(index: Int, v: Boolean) {
        if (v) set(index) else clear(index)
    }

    fun set(index: Int) {
        if (reversed) {
            clear(index)
            return
        }

        checkIndex(index)
        val byteIndex = index / 8  // 计算对应的字节下标
        val bitPosition = index % 8  // 计算字节内的位位置（0-7）
        // 设置该位为1
        bits[byteIndex] = (bits[byteIndex].toInt() or (1 shl bitPosition)).toByte()
    }

    /**
     * 将指定为设为0
     */
    fun clear(index: Int) {
        if (reversed) {
            set(index)
            return
        }

        checkIndex(index)
        val byteIndex = index / 8
        val bitPosition = index % 8
        // 用 and 操作清除该位（掩码：取反的 1<<bitPosition，确保其他位不变）s
        bits[byteIndex] = (bits[byteIndex].toInt() and (1 shl bitPosition).inv()).toByte()
    }

    operator fun get(index: Int): Boolean {
        checkIndex(index)
        val byteIndex = index / 8
        val bitPosition = index % 8
        val bool = (bits[byteIndex].toInt() and (1 shl bitPosition)) != 0
        return if (reversed) !bool else bool
    }

    private fun checkIndex(index: Int) {
        if (index !in 0..<size) {
            throw IndexOutOfBoundsException("Index $index out of range [0, ${size - 1}]")
        }
    }

    override fun toString(): String {
        return bits.joinToString { byte ->
            // 将每个字节转为 8 位二进制字符串（补前导 0）
            String.format("%8s", Integer.toBinaryString(byte.toInt() and 0xFF)).replace(' ', '0')
        }.reversed() // 反转是为了让低位在前（与索引顺序一致）
    }

    fun copy(): BitMap = BitMap(this)
    fun reverseCopy(): BitMap = BitMap(this, true)
    fun reverse() {
        reversed = !reversed
    }
}