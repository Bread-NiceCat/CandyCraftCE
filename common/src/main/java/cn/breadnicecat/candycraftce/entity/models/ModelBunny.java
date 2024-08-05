package cn.breadnicecat.candycraftce.entity.models;
// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import cn.breadnicecat.candycraftce.entity.CEntities;
import cn.breadnicecat.candycraftce.entity.entities.mobs.Bunny;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ModelBunny extends EntityModel<Bunny> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation MAIN = new ModelLayerLocation(CEntities.BUNNY.getId(), "main");
	private final ModelPart root;
	private final ModelPart bb_main;
	
	public ModelBunny(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.root = root;
		this.bb_main = root.getChild("bb_main");
	}
	
	public ModelBunny(EntityRendererProvider.Context context) {
		this(context.bakeLayer(MAIN));
	}
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(12, 0).mirror().addBox(1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(12, 0).mirror().addBox(-3.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(1.0F, -2.0F, 1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(-3.0F, -2.0F, 1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 9).mirror().addBox(-3.0F, -8.0F, -4.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(20, 7).mirror().addBox(-2.0F, -7.0F, -8.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(20, 0).mirror().addBox(-2.0F, -7.0F, 4.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 6).mirror().addBox(-3.5F, -6.0F, -7.0F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(14, 4).mirror().addBox(-1.8F, -9.8F, -7.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(14, 4).mirror().addBox(0.8F, -9.8F, -7.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}
	
	@Override
	public void setupAnim(Bunny entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}