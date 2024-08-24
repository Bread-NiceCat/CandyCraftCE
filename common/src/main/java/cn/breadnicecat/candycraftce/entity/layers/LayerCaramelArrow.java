package cn.breadnicecat.candycraftce.entity.layers;

import cn.breadnicecat.candycraftce.entity.entities.entity.CaramelArrow;
import cn.breadnicecat.candycraftce.misc.mixin_ref.$LivingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;

/**
 * Created in 2024/7/3 上午12:31
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class LayerCaramelArrow extends ArrowLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
	
	private final EntityRenderDispatcher dispatcher;
	
	public LayerCaramelArrow(EntityRendererProvider.Context context, LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
		super(context, renderer);
		this.dispatcher = context.getEntityRenderDispatcher();
	}
	
	@Override
	protected int numStuck(AbstractClientPlayer entity) {
		return entity.getEntityData().get($LivingEntity.LivingEntity$DATA_CARAMEL_ARROW_COUNT_ID);
	}
	
	/**
	 * [Vanilla Copy]
	 */
	@Override
	protected void renderStuckItem(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Entity entity, float x, float y, float z, float partialTick) {
		float f = Mth.sqrt(x * x + z * z);
		CaramelArrow arrow = new CaramelArrow(entity.getX(), entity.getY(), entity.getZ(), entity.level());
		arrow.setYRot((float) (Math.atan2(x, z) * 57.2957763671875));
		arrow.setXRot((float) (Math.atan2(y, f) * 57.2957763671875));
		arrow.yRotO = arrow.getYRot();
		arrow.xRotO = arrow.getXRot();
		this.dispatcher.render(arrow, 0.0, 0.0, 0.0, 0.0f, partialTick, poseStack, buffer, packedLight);
	}
	
	/**
	 * [Vanilla Copy]
	 */
	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		int i = this.numStuck(livingEntity);
		RandomSource randomSource = RandomSource.create(livingEntity.getId() + MOD_ID.hashCode());
		if (i <= 0) {
			return;
		}
		for (int j = 0; j < i; ++j) {
			poseStack.pushPose();
			ModelPart modelPart = this.getParentModel().getRandomModelPart(randomSource);
			ModelPart.Cube cube = modelPart.getRandomCube(randomSource);
			modelPart.translateAndRotate(poseStack);
			float f = randomSource.nextFloat();
			float g = randomSource.nextFloat();
			float h = randomSource.nextFloat();
			float k = Mth.lerp(f, cube.minX, cube.maxX) / 16.0f;
			float l = Mth.lerp(g, cube.minY, cube.maxY) / 16.0f;
			float m = Mth.lerp(h, cube.minZ, cube.maxZ) / 16.0f;
			poseStack.translate(k, l, m);
			f = -1.0f * (f * 2.0f - 1.0f);
			g = -1.0f * (g * 2.0f - 1.0f);
			h = -1.0f * (h * 2.0f - 1.0f);
			this.renderStuckItem(poseStack, buffer, packedLight, livingEntity, f, g, h, partialTicks);
			poseStack.popPose();
		}
	}
}
