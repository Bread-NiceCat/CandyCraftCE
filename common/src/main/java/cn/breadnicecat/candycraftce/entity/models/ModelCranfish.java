package cn.breadnicecat.candycraftce.entity.models;
// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import cn.breadnicecat.candycraftce.entity.entities.mobs.Cranfish;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.item.CItems.CRANFISH;

public class ModelCranfish extends HierarchicalModel<Cranfish> {
	public static final ModelLayerLocation MAIN = new ModelLayerLocation(CRANFISH.getId(), "main");
	private final ModelPart root;
	private final ModelPart tail;
	
	public ModelCranfish(ModelPart root) {
		root.getChild("body");
		this.tail = root.getChild("tail");
		this.root = root;
	}
	
	public ModelCranfish(EntityRendererProvider.Context context) {
		this(context.bakeLayer(MAIN));
	}
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -8.0F, -4.0F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 20).mirror().addBox(-1.0F, -6.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(22, 0).mirror().addBox(-0.5F, -9.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 14).mirror().addBox(-1.1F, -6.3F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));
		
		partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(8, 14).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(9, 15).mirror().addBox(-0.5F, -2.0F, 1.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 19.0F, 4.0F));
		
		return LayerDefinition.create(meshdefinition, 64, 32);
	}
	
	@Override
	public void setupAnim(@NotNull Cranfish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = 1.0f;
		if (!entity.isInWater()) {
			f = 1.5f;
		}
		this.tail.yRot = -f * 0.45f * Mth.sin(0.6f * ageInTicks);
	}
	
	@Override
	public @NotNull ModelPart root() {
		return root;
	}
}