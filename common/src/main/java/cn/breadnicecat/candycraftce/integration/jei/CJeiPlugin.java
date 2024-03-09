package cn.breadnicecat.candycraftce.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/9/30 15:44
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
@JeiPlugin
public class CJeiPlugin implements IModPlugin {
	public static final ResourceLocation UID = prefix("jei-plugin");

	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return UID;
	}

//	@Override
//	public void registerRecipes(IRecipeRegistration registration) {
//		RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level.getRecipeManager());
//	}
}
