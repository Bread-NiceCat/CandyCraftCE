package cn.breadnicecat.candycraftce.utils.geom;

import cn.breadnicecat.candycraftce.utils.MthUtils;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.geom.ModelPart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created in 2023/8/25 13:33
 * Project: candycraftce
 *
 * @param animation key:bone_name
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */


public record ModelAnimation(cn.breadnicecat.candycraftce.utils.geom.ModelAnimation.LoopType type, float length,
                             HashMap<String, BoneState> animation) {
	public static class ModelMappings {

		private final ImmutableMap<String, ModelPart> parts;

		private ModelMappings(ImmutableMap.Builder<String, ModelPart> parts) {
			this.parts = parts.build();
		}

		public ModelPart get(String s) {
			return parts.get(s);
		}

		public boolean contains(String s) {
			return parts.containsKey(s);
		}

		public static ModelMappings makeMappings(ModelPart root) {
			ImmutableMap.Builder<String, ModelPart> builder = ImmutableMap.builder();
			putAll(root, builder);
			return new ModelMappings(builder);
		}

		private static void putAll(ModelPart root, ImmutableMap.Builder<String, ModelPart> parts) {
			for (Map.Entry<String, ModelPart> entry : root.children.entrySet()) {
				ModelPart child = entry.getValue();
				parts.put(entry.getKey(), child);
				if (!child.children.isEmpty()) {
					putAll(child, parts);
				}
			}
		}

		public void resetAllPose() {
			parts.values().forEach(ModelPart::resetPose);
		}
	}

	public void pushAnim(ModelMappings mappings, float ageInTicks) {
		float animTime = type.fix(ageInTicks * TickUtils.TICK_TO_SEC, length);
		animation.keySet().forEach(k -> {
			ModelPart part = mappings.get(k);
			BiPartPose reactive = load(k, animTime);
			part.xScale = 0;
			part.yScale = 0;
			part.zScale = 0;
			reactive.reactiveApply(part);
		});
	}

	public BiPartPose load(String boneName, float animTime) {
		BoneState state = animation.get(boneName);
		return state.load(animTime);
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

		public BoneState(Map<Float, Vec3f> posStates, Map<Float, Vec3f> rotStates, Map<Float, Vec3f> scaleStates) {
			this.posStates = new UnmodifiableBNodes<>(posStates, Float::compare);
			this.rotStates = new UnmodifiableBNodes<>(rotStates, Float::compare);
			this.scaleStates = new UnmodifiableBNodes<>(scaleStates, Float::compare);
		}

		@SuppressWarnings("unchecked")
		public BiPartPose load(float animTime) {
			if (animTime < 0) {
				return null;
			}
			UnmodifiableBNodes.Node<Float, Vec3f> pn = posStates.floor(animTime);
			UnmodifiableBNodes.Node<Float, Vec3f> rn = rotStates.floor(animTime);
			UnmodifiableBNodes.Node<Float, Vec3f> sn = scaleStates.floor(animTime);
			UnmodifiableBNodes.Node<Float, Vec3f>[] prs3n = new UnmodifiableBNodes.Node[]{pn, rn, sn};
			float x = 0, y = 0, z = 0, xr = 0, yr = 0, zr = 0, xs = 1, ys = 1, zs = 1;
			for (UnmodifiableBNodes.Node<Float, Vec3f> n : prs3n) {
				if (n == null) continue;
				Float pk = n.getKey();
				Vec3f pv = n.getValue();
				Vec3f v3f;
				if (pk.equals(animTime)) {
					v3f = pv;
				} else {
					UnmodifiableBNodes.Node<Float, Vec3f> pnn = n.getNext();
					if (pnn == null) {
						v3f = pv;
					} else {
						Float npk = pnn.getKey();
						Vec3f npv = pnn.getValue();
						v3f = linearInterpolation3(animTime, pk, npk, pv, npv);
					}
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
