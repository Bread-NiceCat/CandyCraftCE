package cn.breadnicecat.candycraftce.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
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
	
	/**
	 * Created in 2024/8/4 下午11:17
	 * Project: candycraftce
	 *
	 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
	 * <p>
	 *
	 * <p>
	 **/
	public static class LayerWaffleSheepFur extends RenderLayer<Sheep, SheepModel<Sheep>> {
		private static final ResourceLocation TEX = entityTex("waffle_sheep/fur");
		private final SheepFurModel<Sheep> model;
		
		public LayerWaffleSheepFur(RenderLayerParent<Sheep, SheepModel<Sheep>> renderer, EntityModelSet modelSet) {
			super(renderer);
			this.model = new SheepFurModel<>(modelSet.bakeLayer(ModelLayers.SHEEP_FUR));
		}
		
		@Override
		public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sheep entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			SheepModel<Sheep> parent = getParentModel();
			if (!entity.isInvisible()) {
				parent.copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEX));
				model.renderToBuffer(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f));
			}
		}
	}
}
