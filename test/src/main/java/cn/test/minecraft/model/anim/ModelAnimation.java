package cn.test.minecraft.model.anim;


import cn.test.minecraft.utils.MthUtils;
import cn.test.minecraft.utils.UnmodifiableBNodes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created in 2023/8/25 13:33
 * Project: candycraftce
 *
 * @param animation key:bone_name
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */


public record ModelAnimation(LoopType type, float length,
                             HashMap<String, BoneState> animation) {
	public BiPartPose load(String boneName, float time) {
		BoneState state = animation.get(boneName);
		return state.load(this, time);
	}

	public enum LoopType {
		/**
		 * default
		 */
		ONCE {
			@Override
			float fix(float current, float animLength) {
				return current > animLength ? -1 : current;
			}
		},
		/**
		 * true
		 */
		LOOP {
			@Override
			float fix(float current, float animLength) {
				return current % animLength;
			}
		},
		/**
		 * hold_on_last_frame
		 */
		HOLD {
			@Override
			float fix(float current, float animLength) {
				return Math.min(current, animLength);
			}
		};

		public static LoopType defaultType() {
			return ONCE;
		}

		/**
		 * 修复时间
		 *
		 * @return 如果返回负值，则不会有任何操作
		 */
		abstract float fix(float current, float animLength);
	}

	public static class BoneState {
		public final UnmodifiableBNodes<Float, Vec3f> posStates;
		public final UnmodifiableBNodes<Float, Vec3f> rotStates;
		public final UnmodifiableBNodes<Float, Vec3f> scaleStates;
//		private static final BiPartPose NONE = new BiPartPose(0, 0, 0, 0, 0, 0, 0, 0, 0) {
//		};

		public BoneState(Map<Float, Vec3f> posStates, Map<Float, Vec3f> rotStates, Map<Float, Vec3f> scaleStates) {
			this.posStates = new UnmodifiableBNodes<>(posStates, Float::compare);
			this.rotStates = new UnmodifiableBNodes<>(rotStates, Float::compare);
			this.scaleStates = new UnmodifiableBNodes<>(scaleStates, Float::compare);
		}

		@SuppressWarnings("unchecked")
		public BiPartPose load(ModelAnimation anim, float time) {
			time = anim.type.fix(time, anim.length);
			if (time < 0) {
				return null;
			}
			Float ftime = time;
			UnmodifiableBNodes.Node<Float, Vec3f> pn = posStates.floor(ftime);
			UnmodifiableBNodes.Node<Float, Vec3f> rn = rotStates.floor(ftime);
			UnmodifiableBNodes.Node<Float, Vec3f> sn = scaleStates.floor(ftime);
			UnmodifiableBNodes.Node<Float, Vec3f>[] prs3n = new UnmodifiableBNodes.Node[]{pn, rn, sn};
			float x = 0, y = 0, z = 0, xr = 0, yr = 0, zr = 0, xs = 0, ys = 0, zs = 0;
			for (UnmodifiableBNodes.Node<Float, Vec3f> n : prs3n) {
				if (n == null) continue;
				Float pk = n.getKey();
				Vec3f pv = n.getValue();
				Vec3f v3f;
				if (ftime.equals(pk)) {
					v3f = pv;
				} else {
					UnmodifiableBNodes.Node<Float, Vec3f> pnn = n.getNext();
					Float npk = pnn.getKey();
					Vec3f npv = pnn.getValue();
					v3f = linearInterpolation3(time, pk, npk, pv, npv);
				}
				if (n == pn) {
					x = v3f.x();
					y = v3f.y();
					z = v3f.z();
				} else if (n == rn) {
					xr = v3f.x();
					yr = v3f.y();
					zr = v3f.z();

				} else {
					xs = v3f.x();
					ys = v3f.y();
					zs = v3f.z();
				}
			}
			return new BiPartPose(x, y, z, xr, yr, zr, xs, ys, zs);
		}

		private Vec3f linearInterpolation3(float p, float ap, float bp, Vec3f av, Vec3f bv) {
			return new Vec3f(MthUtils.linearInterpolation(p, ap, bp, av.x(), bv.x()),
					MthUtils.linearInterpolation(p, ap, bp, av.y(), bv.y()),
					MthUtils.linearInterpolation(p, ap, bp, av.z(), bv.z()));
		}
	}


}
