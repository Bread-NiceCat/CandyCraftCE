package cn.breadnicecat.candycraftce.entity.renderers;


import cn.breadnicecat.candycraftce.entity.entities.GingerbreadMan;
import cn.breadnicecat.candycraftce.entity.models.ModelGingerbreadMan;
import cn.breadnicecat.candycraftce.entity.layers.LayerGingerbreadManJob;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefixEntityTex;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/27 9:56
 */
@Environment(EnvType.CLIENT)
public class RendererGingerbreadMan extends MobRenderer<GingerbreadMan, ModelGingerbreadMan> {
	public static final ResourceLocation MODEL = prefix("gingerbread_man");
	public static final ModelLayerLocation MAIN = new ModelLayerLocation(MODEL, "main");
	public static final ModelLayerLocation JOB = new ModelLayerLocation(MODEL, "job");
	
	private static final ResourceLocation MAIN_TEX = prefixEntityTex("gingerbread_man/main");
	
	public RendererGingerbreadMan(EntityRendererProvider.Context pContext) {
		super(pContext, new ModelGingerbreadMan(pContext.bakeLayer(MAIN)), 0.25f);
		addLayer(new LayerGingerbreadManJob(this));
	}
	
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull GingerbreadMan pEntity) {
		return MAIN_TEX;
	}
}
