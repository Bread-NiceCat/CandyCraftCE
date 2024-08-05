package cn.breadnicecat.candycraftce.entity.renderers;

import cn.breadnicecat.candycraftce.entity.layers.LayerWaffleSheepFur;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.entityTex;

/**
 * Created in 2024/8/4 下午10:26
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RendererWaffleSheep extends SheepRenderer {
	private static final ResourceLocation TEX = entityTex("waffle_sheep/sheep");
	
	public RendererWaffleSheep(EntityRendererProvider.Context context) {
		super(context);
		layers.set(layers.size() - 1, new LayerWaffleSheepFur(this, context.getModelSet()));//we use simple one
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(Sheep entity) {
		return TEX;
	}
}
