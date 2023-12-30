package cn.breadnicecat.candycraftce.utils.geom;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created in 2023/8/24 20:29
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
@Environment(EnvType.CLIENT)
public class CatModelLoader {
	private static final Gson GSON = new Gson();
	private static final Logger LOGGER = CLogUtils.sign();

	private CatModelLoader() {
	}

	public static LayerDefinition fromCat(ResourceLocation loc) {
		try {
			return fromCat(Minecraft.getInstance().getResourceManager().open(loc));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static LayerDefinition fromCat(InputStream input) {
		JsonObject obj;
		try (Reader reader = new InputStreamReader(new GZIPInputStream(input))) {
			obj = GSON.fromJson(reader, JsonObject.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return fromCat(obj);
	}

	public static LayerDefinition fromCat(JsonObject obj) {
		if (!obj.has("format_version") || !obj.get("format_version").getAsString().equals("cat_model:1.0")) {
			LOGGER.warn("Encountered an unfriendly format_version: " + obj.get("format_version").getAsString() + ", expected: cat_model:1.0");
			CLogUtils.printStackTrace();
		}

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		HashMap<String, PartDefinition> ref = new HashMap<>();
		JsonArray children = obj.get("children").getAsJsonArray();
		for (JsonElement j1 : children) {
			JsonObject child = j1.getAsJsonObject();
			String name = child.get("name").getAsString();
			PartDefinition parent = child.has("parent") ?
					ref.get(child.get("parent").getAsString()) : partdefinition;

			CubeListBuilder builder = CubeListBuilder.create();
			for (JsonElement j2 : child.get("cubes").getAsJsonArray()) {
				JsonObject cube = j2.getAsJsonObject();
				builder.texOffs(cube.get("texCoordU").getAsInt(), cube.get("texCoordV").getAsInt())
						.mirror(cube.get("isMirror").getAsBoolean())
						.addBox(cube.get("originX").getAsFloat(), cube.get("originY").getAsFloat(), cube.get("originZ").getAsFloat(),
								cube.get("dimensionX").getAsFloat(), cube.get("dimensionY").getAsFloat(), cube.get("dimensionZ").getAsFloat(),
								new CubeDeformation(cube.get("growX").getAsFloat(), cube.get("growY").getAsFloat(), cube.get("growZ").getAsFloat()),
								cube.get("texScaleU").getAsFloat(), cube.get("texScaleV").getAsFloat());
			}
			JsonObject pose = child.get("partPose").getAsJsonObject();
			PartDefinition bone = parent.addOrReplaceChild(name,
					builder,
					PartPose.offsetAndRotation(pose.get("x").getAsFloat(), pose.get("y").getAsFloat(), pose.get("z").getAsFloat(),
							pose.get("xRot").getAsFloat(), pose.get("yRot").getAsFloat(), pose.get("zRot").getAsFloat()));
			ref.put(name, bone);
		}

		return LayerDefinition.create(meshdefinition, obj.get("texU").getAsInt(), obj.get("texV").getAsInt());
	}


	public static HashMap<String, ModelAnimation> loadAnimation(ResourceLocation loc) {
		try {
			return loadAnimation(Minecraft.getInstance().getResourceManager().open(loc));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static HashMap<String, ModelAnimation> loadAnimation(InputStream input) {
		JsonObject obj;
		try (Reader reader = new InputStreamReader(new GZIPInputStream(input))) {
			obj = GSON.fromJson(reader, JsonObject.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return loadAnimation(obj);
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, ModelAnimation> loadAnimation(JsonObject obj) {
		//检查版本
		if (!obj.has("format_version") || !obj.get("format_version").getAsString().equals("1.8.0")) {
			LOGGER.warn("Encountered an unfriendly format_version: " + obj.get("format_version").getAsString() + ", expected: 1.8.0");
			CLogUtils.printStackTrace();
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
							JsonArray v3 = as3(e3.getValue());
							float[] f3 = a3toF3(v3);
							//角度转弧度
							if (i == 1) {
								deg2rad(f3);
							}
							Vec3f v3f = new Vec3f(f3[0], f3[1], f3[2]);
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

	private static void deg2rad(float[] f3d) {
		for (int i = 0; i < f3d.length; i++) {
			f3d[i] = f3d[i] * Mth.DEG_TO_RAD;
		}
	}

	/**
	 * a3f: array-3-float
	 */
	private static float[] a3toF3(JsonArray element) {
		return new float[]{element.get(0).getAsFloat(), element.get(1).getAsFloat(), element.get(2).getAsFloat()};
	}

	private static JsonArray as3(JsonElement element) {
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
