package cn.breadnicecat.candycraftce.entity.models;
// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings

import cn.breadnicecat.candycraftce.entity.entities.GingerbreadMan;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ModelGingerbreadMan extends EntityModel<GingerbreadMan> {
	
	private final ModelPart Head;
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;
	private final ModelPart root;
	
	public ModelGingerbreadMan(ModelPart root) {
		this.root = root;
		this.Head = root.getChild("Head");
		this.RightLeg = root.getChild("RightLeg");
		this.LeftLeg = root.getChild("LeftLeg");
	}
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 12.0F, 0.0F));
		PartDefinition HatBrim = Head.addOrReplaceChild("HatBrim", CubeListBuilder.create().texOffs(14, 23).addBox(-4.0F, -4.0F, -0.5F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, -1.5708F, 0.0F, 0.0F));
		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(8, 8).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(18, 16).addBox(-2.0F, 1.0F, 0.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));
		PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(20, 8).addBox(-1.5F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 13.0F, 0.0F));
		PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(20, 8).mirror().addBox(-0.5F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, 13.0F, 0.0F));
		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 8).addBox(-0.95F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.95F, 18.0F, 0.0F));
		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 8).mirror().addBox(-1.05F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.95F, 18.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}
	
	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public void setupAnim(@NotNull GingerbreadMan pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.Head.yRot = pNetHeadYaw * Mth.DEG_TO_RAD;
		this.Head.xRot = pHeadPitch * Mth.DEG_TO_RAD;
		this.RightLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount * 0.5F;
		this.LeftLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + Mth.PI) * 1.4F * pLimbSwingAmount * 0.5F;
		this.RightLeg.yRot = 0.0F;
		this.LeftLeg.yRot = 0.0F;
	}
}