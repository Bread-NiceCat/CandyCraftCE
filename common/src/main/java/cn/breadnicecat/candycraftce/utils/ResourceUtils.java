package cn.breadnicecat.candycraftce.utils;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;


public class ResourceUtils {
	
	/**
	 * @return MOD_ID:textures/gui/(name).png
	 */
	public static ResourceLocation guiTex(String name) {
		return prefix("textures/gui/" + name + ".png");
	}
	
	/**
	 * @return MOD_ID:textures/entity/(name).png
	 */
	public static ResourceLocation entityTex(String name) {
		return prefix("textures/entity/" + name + ".png");
	}
	
	/**
	 * 添加前缀
	 *
	 * @return MOD_ID:path
	 */
	public static ResourceLocation prefix(String path) {
		return ResourceLocation.fromNamespaceAndPath(CandyCraftCE.MOD_ID, path);
	}
	
	public static ResourceLocation prefixDefault(String path) {
		return path.contains(":") ? ResourceLocation.parse(path) : prefix(path);
	}
	
	public static ModelResourceLocation model(String location, String path) {
		return new ModelResourceLocation(prefix(location), path);
	}
	
	public static ModelLayerLocation layer(String location, String layer) {
		return new ModelLayerLocation(prefix(location), layer);
	}
	
	/**
	 * 同prefix
	 */
	public static String simplePrefix(String path) {
		return CandyCraftCE.MOD_ID + ":" + path;
	}
	
	
}
