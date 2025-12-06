package cn.breadnicecat.candycraftce.utils

class BitMap private constructor(
    private val bits: ByteArray,
    private val size: Int,
) {
    //(总位数 + 7) / 8（向上取整）
    constructor(size: Int) : this(ByteArray((size + 7) / 8), size)
    constructor(other: BitMap) : this(other.bits.copyOf(), other.size)

    init {
        require(size > 0)
    }

    fun set(index: Int) {
        checkIndex(index)
        val byteIndex = index / 8  // 计算对应的字节下标
        val bitPosition = index % 8  // 计算字节内的位位置（0-7）
        // 设置该位为1
        bits[byteIndex] = (bits[byteIndex].toInt() or (1 shl bitPosition)).toByte()
    }

    operator fun set(index: Int, v: Boolean) {
        if (v) set(index) else clear(index)
    }

    /**
     * 将指定为设为0
     */
    fun clear(index: Int) {
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
        return (bits[byteIndex].toInt() and (1 shl bitPosition)) != 0
    }

    /**
     * 统计已设置为 1 的位的总数
     */
    fun countSetBits(): Int {
        var count = 0
        for (byte in bits) {
            // 将字节转为无符号值（避免负数影响），再统计二进制中 1 的个数
            count += Integer.bitCount(byte.toInt() and 0xFF)
        }
        return count
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
}