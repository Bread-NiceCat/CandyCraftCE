//package cn.breadnicecat.candycraftce.integration.jei;
//
//import cn.breadnicecat.candycraftce.utils.CLogUtils;
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.registration.IRecipeRegistration;
//import net.minecraft.resources.ResourceLocation;
//import org.jetbrains.annotations.NotNull;
//
//import static cn.breadnicecat.candycraftce.CandyCraftCE.prefix;
//
///**
// * Created in 2023/9/30 15:44
// * Project: candycraftce
// *
// * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
// */
//@JeiPlugin
//public class CJeiPlugin implements IModPlugin {
//	public static final ResourceLocation PLUGIN_ID = prefix("jei-plugin");
//
//	@Override
//	public @NotNull ResourceLocation getPluginUid() {
//		return PLUGIN_ID;
//	}
//
//	@Override
//	public void registerRecipes(IRecipeRegistration registration) {
//		CLogUtils.getModLogger().info("JEI LOADED");
//	}
//}
