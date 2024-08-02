package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/8/1 上午9:02
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RendererCandyCanePig extends PigRenderer {
	
	private final ResourceLocation TEX = ResourceUtils.entityTex("candy_cane_pig");
	
	public RendererCandyCanePig(EntityRendererProvider.Context context) {
		super(context);
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(Pig entity) {
		return TEX;
	}
}
