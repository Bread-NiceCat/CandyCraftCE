package cn.breadnicecat.candycraftce.utils

import net.minecraft.core.Direction.Axis
import java.util.*

open class AxisSet private constructor(protected var ax: Int = 0b000) : Set<Axis> {

    companion object {
        protected fun set(ax: Int, ord: Int, v: Boolean): Int {
            val nax = if (v) {
                ax or (1 shl ord)
            } else {
                ax and (1 shl ord).inv()
            }
            return nax
        }

    }

    constructor(vararg axes: Axis) : this(axes.let {
        var ax = 0
        for (it in axes) {
            ax = set(ax, it.ordinal, true)
        }
        ax
    })


    fun mutable(): MutableAxisSet {
        return MutableAxisSet(ax)
    }

    fun immutable(): AxisSet {
        return this
    }

    fun hasX(): Boolean = has(Axis.X)

    fun hasY(): Boolean = has(Axis.Y)

    fun hasZ(): Boolean = has(Axis.Z)

    fun has(axis: Axis): Boolean = ax and (1 shl axis.ordinal) != 0
    fun setX(v: Boolean = true): AxisSet = set(Axis.X, v)

    fun setY(v: Boolean = true): AxisSet = set(Axis.Y, v)

    fun setZ(v: Boolean = true): AxisSet = set(Axis.Z, v)
    fun set(axis: Axis, v: Boolean = true): AxisSet {
        val ord = axis.ordinal
        val nax = set(ax, ord, v)
        return AxisSet(nax)
    }

    fun or(another: AxisSet): AxisSet {
        val nax = ax or another.ax
        return AxisSet(nax)
    }

    fun not(): AxisSet {
        val nax = ax.inv() and 0b111
        return AxisSet(nax)
    }

    override fun toString(): String {
        return joinToString(", ", "[", "]")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AxisSet) return false
        if (ax != other.ax) return false

        return true
    }

    override fun hashCode(): Int {
        return ax
    }

    override val size: Int
        get() = Integer.bitCount(ax)

    override fun isEmpty(): Boolean = ax == 0


    override fun contains(element: Axis): Boolean = has(element)

    override fun iterator(): Iterator<Axis> =
        Arrays.stream(Axis.VALUES).filter { it in this }.iterator()

    override fun containsAll(elements: Collection<Axis>): Boolean {
        for (axis in elements) {
            if (!contains(axis)) return false
        }
        return true
    }


    class MutableAxisSet : AxisSet {
        constructor(vararg axes: Axis) : super(*axes)
        internal constructor(v: Int) : super(v)

        fun withX(v: Boolean = true): MutableAxisSet = with(Axis.X, v)

        fun withY(v: Boolean = true): MutableAxisSet = with(Axis.Y, v)

        fun withZ(v: Boolean = true): MutableAxisSet = with(Axis.Z, v)

        fun with(axis: Axis, v: Boolean = true): MutableAxisSet {
            ax = set(ax, axis.ordinal, v)
            return this
        }

        fun withOr(another: AxisSet): MutableAxisSet {
            ax = ax or another.ax
            return this
        }

        fun withAnd(another: AxisSet): MutableAxisSet {
            ax = ax and another.ax
            return this
        }

        fun withNot(): MutableAxisSet {
            ax = ax.inv() and 0b111
            return this
        }

    }

}