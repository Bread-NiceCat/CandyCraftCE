package cn.breadnicecat.candycraftce.utils;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;


public class ResourceUtils {
	
	/**
	 * @return MOD_ID:textures/gui/(name).png
	 */
	public static ResourceLocation prefixGUITex(String name) {
		return prefix("textures/gui/" + name + ".png");
	}
	
	/**
	 * @return MOD_ID:textures/entity/(name).png
	 */
	public static ResourceLocation prefixEntityTex(String name) {
		return prefix("textures/entity/" + name + ".png");
	}
	
	/**
	 * 添加前缀
	 *
	 * @return MOD_ID:path
	 */
	public static ResourceLocation prefix(String path) {
		return new ResourceLocation(CandyCraftCE.MOD_ID, path);
	}
	
	public static ModelResourceLocation modelPrefix(String location, String path) {
		return new ModelResourceLocation(CandyCraftCE.MOD_ID, location, path);
	}
	
	/**
	 * 同prefix
	 */
	public static String simplePrefix(String path) {
		return CandyCraftCE.MOD_ID + ":" + path;
	}
	
	
}
