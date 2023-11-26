package cn.test.minecraft.model.anim;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;


public class CatModelLoader {
	@SuppressWarnings("unchecked")
	public static HashMap<String, ModelAnimation> loadAnimation(JsonObject obj) {
		//检查版本
		if (!obj.has("format_version") || !obj.get("format_version").getAsString().equals("1.8.0")) {
			System.out.println("Encountered an unfriendly format_version: " + obj.get("format_version").getAsString() + ", expected: 1.8.0");
		}
		//逐一获取动画
		HashMap<String, ModelAnimation> animMaps = new HashMap<>();
		JsonObject animations = obj.get("animations").getAsJsonObject();
		for (Map.Entry<String, JsonElement> e1 : animations.entrySet()) {

			String animName = e1.getKey();
			JsonObject animObj = e1.getValue().getAsJsonObject();
			ModelAnimation.LoopType loopType;
			float animLength = animObj.get("animation_length").getAsFloat();
			//获取循环类型
			if (animObj.has("loop")) {
				loopType = switch (animObj.get("loop").getAsString()) {
					case "true" -> ModelAnimation.LoopType.LOOP;
					case "hold_on_last_frame" -> ModelAnimation.LoopType.HOLD;
					default ->
							throw new IllegalStateException("unknown loop type: " + animObj.get("loop").getAsString());
				};
			} else {
				loopType = ModelAnimation.LoopType.defaultType();
			}
			//获取各个骨骼帧
			HashMap<String, ModelAnimation.BoneState> stateMap = new HashMap<>();
			JsonObject boneObj = animObj.get("bones").getAsJsonObject();
			for (Map.Entry<String, JsonElement> e2 : boneObj.entrySet()) {
				String boneName = e2.getKey();
				JsonObject ts = e2.getValue().getAsJsonObject();

				HashMap<Float, Vec3f>[] buf = new HashMap[]{new HashMap<>(), new HashMap<>(), new HashMap<>()};
				final String[] types = {"position", "rotation", "scale"};
				for (int i = 0; i < types.length; i++) {
					//防止 "scale": 1
					JsonElement aEle = ts.get(types[i]);
					if (aEle == null) {
						continue;
					} else if (aEle.isJsonPrimitive()) {
						float con = aEle.getAsFloat();
						Vec3f con3f = new Vec3f(con, con, con);
						buf[i].put(0f, con3f);
						buf[i].put(animLength, con3f);
					} else if (aEle.isJsonObject()) {
						JsonObject o = aEle.getAsJsonObject();
						for (Map.Entry<String, JsonElement> e3 : o.entrySet()) {
							Float k = Float.valueOf(e3.getKey());
							JsonArray v3d = as3DArray(e3.getValue());
							float[] f3d = a3eToa3f(v3d);
							Vec3f v3f = new Vec3f(f3d[0], f3d[1], f3d[2]);
							buf[i].put(k, v3f);
						}
					}
				}
				stateMap.put(boneName, new ModelAnimation.BoneState(buf[0], buf[1], buf[2]));
			}


			animMaps.put(animName, new ModelAnimation(loopType, animLength, stateMap));
		}
		return animMaps;
	}

	/**
	 * a3f: array-3-float
	 */
	private static float[] a3eToa3f(JsonArray element) {
		return new float[]{element.get(0).getAsFloat(), element.get(1).getAsFloat(), element.get(2).getAsFloat()};
	}

	private static JsonArray as3DArray(JsonElement element) {
		if (element.isJsonArray()) {
			return element.getAsJsonArray();
		} else if (element.isJsonPrimitive()) {
			JsonArray array = new JsonArray(3);
			array.add(element);
			array.add(element);
			array.add(element);
			return array;
		}
		throw new IllegalStateException("Unable to parse to a 3-D array: " + element);
	}
}
