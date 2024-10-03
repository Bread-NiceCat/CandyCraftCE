package cn.breadnicecat.candycraftce.entity.models;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import cn.breadnicecat.candycraftce.entity.entities.monsters.CookieCreeper;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.entity.models.animations.AnimationCookieCreeper.idle;
import static cn.breadnicecat.candycraftce.entity.models.animations.AnimationCookieCreeper.walk;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.layer;

public class ModelCookieCreeper extends HierarchicalModel<CookieCreeper> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation MAIN = layer("cookie_creeper", "main");
	private final ModelPart root;
	
	public ModelCookieCreeper(ModelPart root) {
		this.root = root.getChild("all");
	}
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -1.0F));
		all.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, -6.0F, -1.5F, -0.1745F, 0.0F, -0.1745F));
		all.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -6.0F, -1.5F, -0.1745F, 0.0F, 0.1745F));
		all.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -6.0F, 3.5F, 0.1745F, 0.0F, 0.1745F));
		all.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, -6.0F, 3.5F, 0.1745F, 0.0F, -0.1745F));
		PartDefinition all_body = all.addOrReplaceChild("all_body", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 1.0F));
		PartDefinition head = all_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -10.0F, 0.0F));
		PartDefinition bone8 = head.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));
		bone8.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body = all_body.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -17.0F, -1.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -1.0F));
		PartDefinition bone7 = body.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 1.0F));
		bone7.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		return LayerDefinition.create(meshdefinition, 64, 32);
	}
	
	@Override
	public @NotNull ModelPart root() {
		return root;
	}
	
	@Override
	public void setupAnim(CookieCreeper entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root.getAllParts().forEach(ModelPart::resetPose);
		animate(entity.animationState, entity.walkAnimation.isMoving() ? walk : idle, ageInTicks);
	}
}