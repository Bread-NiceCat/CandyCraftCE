package cn.breadnicecat.candycraftce.utils

import net.minecraft.core.Direction

open class Axes {
    protected val ax: BitMap

    constructor(vararg axes: Direction.Axis) {
        ax = BitMap(Direction.Axis.entries.size)
        for (axis in axes) {
            ax[axis.ordinal] = true
        }
    }

    protected constructor(axes: BitMap) {
        ax = axes.copy()
    }

    fun mutable(): Mutable {
        return Mutable(ax.copy())
    }

    fun immutable(): Axes {
        return this
    }


    fun hasX(): Boolean {
        return has(Direction.Axis.X)
    }

    fun hasY(): Boolean {
        return has(Direction.Axis.Y)
    }

    fun hasZ(): Boolean {
        return has(Direction.Axis.Z)
    }

    fun has(axis: Direction.Axis): Boolean {
        return ax[axis.ordinal]
    }

    fun setX(v: Boolean): Axes {
        return set(Direction.Axis.X, v)
    }

    fun setY(v: Boolean): Axes {
        return set(Direction.Axis.Y, v)
    }

    fun setZ(v: Boolean): Axes {
        return set(Direction.Axis.Z, v)
    }

    fun setX(): Axes {
        return setX(true)
    }

    fun setY(): Axes {
        return setY(true)
    }

    fun setZ(): Axes {
        return setZ(true)
    }

    fun set(axis: Direction.Axis, v: Boolean = true): Axes {
        val newAxes = Axes(ax)
        newAxes.ax[axis.ordinal] = v
        return newAxes
    }

    fun or(another: Axes): Axes {
        val axes = Axes()
        for (i in 0..3) {
            axes.ax[i] = ax[i] or another.ax[i]
        }
        return axes
    }

    class Mutable : Axes {
        constructor(vararg axes: Direction.Axis) : super(*axes)

        constructor(axes: BitMap) : super(axes)

        fun withX(v: Boolean = true): Mutable = with(Direction.Axis.X, v)

        fun withY(v: Boolean = true): Mutable = with(Direction.Axis.Y, v)

        fun withZ(v: Boolean = true): Mutable = with(Direction.Axis.Z, v)

        fun with(axis: Direction.Axis, v: Boolean = true): Mutable {
            ax[axis.ordinal] = v
            return this
        }

        fun withOr(another: Axes): Mutable {
            for (i in 0..3) {
                ax[i] = ax[i] or another.ax[i]
            }
            return this
        }
    }
}