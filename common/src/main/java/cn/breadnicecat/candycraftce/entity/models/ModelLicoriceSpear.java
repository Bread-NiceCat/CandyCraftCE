package cn.breadnicecat.candycraftce.entity.models;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import cn.breadnicecat.candycraftce.entity.entities.entity.LicoriceSpear;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

import static cn.breadnicecat.candycraftce.entity.CEntities.LICORICE_SPEAR;

public class ModelLicoriceSpear extends EntityModel<LicoriceSpear> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation MAIN = new ModelLayerLocation(LICORICE_SPEAR.getId(), "main");
	
	private final ModelPart bb_main;
	
	
	public ModelLicoriceSpear(ModelPart root) {
		super(RenderType::entityCutout);
		this.bb_main = root.getChild("bb_main");
	}
	
	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -31.0F, -1.0F, 1.0F, 31.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 10).addBox(-1.0F, -25.5F, -2.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(4, 14).addBox(-0.5F, -16.5F, -1.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		
		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(5, 1).addBox(-3.5F, -4.0F, 0.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -28.0F, -0.5F, 0.0F, 0.7854F, 0.0F));
		
		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(5, 1).addBox(-3.5F, -4.0F, 0.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -28.0F, -0.5F, 0.0F, -0.7854F, 0.0F));
		
		return LayerDefinition.create(meshdefinition, 32, 32);
	}
	
	@Override
	public void setupAnim(LicoriceSpear entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
}