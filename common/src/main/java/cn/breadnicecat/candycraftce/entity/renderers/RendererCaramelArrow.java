package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.entity.entities.CaramelArrow;
import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/7/1 23:56
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RendererCaramelArrow extends ArrowRenderer<CaramelArrow> {
	public static ResourceLocation TEX = ResourceUtils.prefixEntityTex("caramel_arrow");
	
	public RendererCaramelArrow(EntityRendererProvider.Context context) {
		super(context);
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(CaramelArrow entity) {
		return TEX;
	}
}
