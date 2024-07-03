package cn.breadnicecat.candycraftce.entity.layers;

import cn.breadnicecat.candycraftce.entity.entities.CaramelArrow;
import cn.breadnicecat.candycraftce.mixin_ref.$LivingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Arrow;

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
		Arrow arrow = new CaramelArrow(entity.level(), entity.getX(), entity.getY(), entity.getZ());
		arrow.setYRot((float) (Math.atan2(x, z) * 57.2957763671875));
		arrow.setXRot((float) (Math.atan2(y, f) * 57.2957763671875));
		arrow.yRotO = arrow.getYRot();
		arrow.xRotO = arrow.getXRot();
		this.dispatcher.render(arrow, 0.0, 0.0, 0.0, 0.0f, partialTick, poseStack, buffer, packedLight);
	}
}
