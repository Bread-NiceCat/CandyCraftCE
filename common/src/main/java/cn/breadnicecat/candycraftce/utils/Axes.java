package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.Direction;

import java.util.Arrays;

import static net.minecraft.core.Direction.Axis.*;

/**
 * Created in 2024/3/16 16:28
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class Axes {
	protected final boolean[] ax;

	public Axes(Direction.Axis... axes) {
		ax = new boolean[values().length];
		for (Direction.Axis axis : axes) {
			ax[axis.ordinal()] = true;
		}
	}

	protected Axes(boolean[] axes) {
		ax = Arrays.copyOf(axes, axes.length);
	}

	public Mutable mutable() {
		return new Mutable(ax);
	}

	public Axes immutable() {
		return new Axes(ax);
	}


	public boolean hasX() {
		return has(X);
	}

	public boolean hasY() {
		return has(Y);
	}

	public boolean hasZ() {
		return has(Z);
	}

	public boolean has(Direction.Axis axis) {
		return ax[axis.ordinal()];
	}

	public Axes setX(boolean v) {
		return set(X, v);
	}

	public Axes setY(boolean v) {
		return set(Y, v);
	}

	public Axes setZ(boolean v) {
		return set(Z, v);
	}

	public Axes setX() {
		return setX(true);
	}

	public Axes setY() {
		return setY(true);
	}

	public Axes setZ() {
		return setZ(true);
	}

	public Axes set(Direction.Axis axis) {
		return set(axis, true);
	}

	public Axes set(Direction.Axis axis, boolean v) {
		Axes newAxes = new Axes(ax);
		newAxes.ax[axis.ordinal()] = v;
		return newAxes;
	}

	public Axes or(Axes another) {
		Axes axes = new Axes();
		for (int i = 0; i < 4; i++) {
			axes.ax[i] = ax[i] | another.ax[i];
		}
		return axes;
	}


	public static class Mutable extends Axes {
		public Mutable(Direction.Axis... axes) {
			super(axes);
		}

		protected Mutable(boolean[] axes) {
			super(axes);
		}

		public Mutable withX(boolean v) {
			return with(X, v);
		}

		public Mutable withY(boolean v) {
			return with(Y, v);
		}

		public Mutable withZ(boolean v) {
			return with(Z, v);
		}

		public Mutable withX() {
			return withX(true);
		}

		public Mutable withY() {
			return withY(true);
		}

		public Mutable withZ() {
			return withZ(true);
		}

		public Mutable with(Direction.Axis axis) {
			return with(axis, true);
		}

		public Mutable with(Direction.Axis axis, boolean v) {
			ax[axis.ordinal()] = v;
			return this;
		}

		public Mutable withOr(Axes another) {
			for (int i = 0; i < 4; i++) {
				ax[i] |= another.ax[i];
			}
			return this;
		}
	}
}