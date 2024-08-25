package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.entity.entities.mobs.Bunny;
import cn.breadnicecat.candycraftce.entity.layers.LayerBunnyBody;
import cn.breadnicecat.candycraftce.entity.models.ModelBunny;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.entityTex;

/**
 * Created in 2024/8/5 上午8:29
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RendererBunny extends MobRenderer<Bunny, ModelBunny> {
	private static final ResourceLocation TEX = entityTex("bunny/eye");
	
	public RendererBunny(EntityRendererProvider.Context context) {
		super(context, new ModelBunny(context), 0.3f);
		addLayer(new LayerBunnyBody(this));
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(Bunny entity) {
		return TEX;
	}
	
}
