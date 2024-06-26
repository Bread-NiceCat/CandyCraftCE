package cn.breadnicecat.candycraftce.utils;

import cn.breadnicecat.candycraftce.CandyCraftCE;
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

    /**
     * 同prefix
     */
    public static String simplePrefix(String path) {
        return CandyCraftCE.MOD_ID + ":" + path;
    }

    public static ResourceLocation extend(ResourceLocation raw, String prefix, String postfix) {
        return new ResourceLocation(raw.getNamespace(), prefix + raw.getPath() + postfix);
    }

    public static ResourceLocation prefix(ResourceLocation raw, String prefix) {
        return new ResourceLocation(raw.getNamespace(), prefix + raw.getPath());
    }

    public static ResourceLocation postfix(ResourceLocation raw, String postfix) {
        return new ResourceLocation(raw.getNamespace(), raw.getPath() + postfix);
    }


}
