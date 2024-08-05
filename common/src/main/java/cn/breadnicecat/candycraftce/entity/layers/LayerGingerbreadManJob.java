package cn.breadnicecat.candycraftce.entity.layers;

import cn.breadnicecat.candycraftce.entity.entities.mobs.GingerbreadMan;
import cn.breadnicecat.candycraftce.entity.models.ModelGingerbreadMan;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.entityTex;
import static net.fabricmc.api.EnvType.CLIENT;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/27 20:25
 */
@Environment(CLIENT)
public class LayerGingerbreadManJob extends RenderLayer<GingerbreadMan, ModelGingerbreadMan> {
	private static final ResourceLocation BLACK_SMITH = entityTex("gingerbread_man/blacksmith");
	private static final ResourceLocation FARMER = entityTex("gingerbread_man/farmer");
	private static final ResourceLocation TRAVELLER = entityTex("gingerbread_man/traveller");
	private static final List<ResourceLocation> TEXTURES = List.of(BLACK_SMITH, FARMER, TRAVELLER);
	
	public LayerGingerbreadManJob(RenderLayerParent<GingerbreadMan, ModelGingerbreadMan> pRenderer) {
		super(pRenderer);
	}
	
	
	@Override
	public void render(@NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, @NotNull GingerbreadMan entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		GingerbreadMan.GingerbreadJob job = entity.getJob();
		if (job != GingerbreadMan.GingerbreadJob.NONE) {
			ModelGingerbreadMan m = getParentModel();
			m.setupAnim(entity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
			m.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutout(TEXTURES.get(job.ordinal() - 1))), pPackedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1f, 1f, 1f, 1f);
		}
	}
}
