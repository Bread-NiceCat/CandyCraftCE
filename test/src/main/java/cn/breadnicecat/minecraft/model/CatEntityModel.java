package cn.breadnicecat.minecraft.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

import static cn.breadnicecat.minecraft.utils.Utils.assertTrue;

@SuppressWarnings("unused")
class CatEntityModel {
	private String format_version = "cat_model:1.0";
	//	private String name;
	private int texU;
	private int texV;
	private ArrayList<ModelPart> children = new ArrayList<>();

//	public String getName() {
//		return this.name;
//	}

//	public void setName(String name) {
//		reqNull(this.name, "name");
//		this.name = name;
//	}

	void validate() {
		assertTrue(texU * texV > 0, "illegal tex size: " + texU + "*" + texV);
	}

	void setUV(int texU, int texV) {
		this.texU = texU;
		this.texV = texV;
	}

	private transient HashSet<String> boneNames = new HashSet<>();

	void addOrReplaceChild(String parent, String name, CubeListBuilder builder, PartPose partPose) {
		Objects.requireNonNull(name);
		if (parent != null) {
			assertTrue(!name.equals(parent), "Illegal quote: parent->this.name");
			assertTrue(boneNames.contains(parent), "Unknown parent: " + parent + " of " + name);
		}
		assertTrue(boneNames.add(name), "Duplicate bone name: " + name);
		children.add(new ModelPart(parent, name, builder, partPose));
	}

	static class CubeListBuilder extends LinkedList<CubeDefinition> {

		private transient int[] texOff = new int[2];
		private transient boolean mirror;


		//		private ArrayList<CubeDefinition> cubes = new ArrayList<>();
		private CubeListBuilder() {
		}

		public static CubeListBuilder create() {
			return new CubeListBuilder();
		}

		public void texOffs(int offX, int offY) {
			this.texOff[0] = offX;
			this.texOff[1] = offY;
		}

		public void addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, CubeDeformation pCubeDeformation) {
			this.add(new CubeDefinition(texOff[0], texOff[1],
					pOriginX, pOriginY, pOriginZ,
					pDimensionX, pDimensionY, pDimensionZ,
					pCubeDeformation.growX, pCubeDeformation.growY, pCubeDeformation.growZ,
					this.mirror,
					1f, 1f)
			);
		}

		public void mirror() {
			mirror(true);
		}

		public void mirror(boolean isMirror) {
			this.mirror = isMirror;
		}

	}

	record CubeDeformation(float growX, float growY,
	                       float growZ) {

		public CubeDeformation(float grow) {
			this(grow, grow, grow);
		}
	}

	record PartPose(float x, float y, float z,
	                float xRot, float yRot,
	                float zRot) {

		static PartPose offset(float pX, float pY, float pZ) {
			return offsetAndRotation(pX, pY, pZ, 0.0F, 0.0F, 0.0F);
		}

		static PartPose rotation(float pXRot, float pYRot, float pZRot) {
			return offsetAndRotation(0.0F, 0.0F, 0.0F, pXRot, pYRot, pZRot);
		}

		static PartPose offsetAndRotation(float pX, float pY, float pZ, float pXRot, float pYRot, float pZRot) {
			return new PartPose(pX, pY, pZ, pXRot, pYRot, pZRot);
		}

	}

	record CubeDefinition(int texCoordU, int texCoordV,
	                      float originX, float originY,
	                      float originZ, float dimensionX,
	                      float dimensionY, float dimensionZ,
	                      float growX, float growY, float growZ, boolean isMirror,
	                      float texScaleU, float texScaleV) {
	}

	record ModelPart(String parent, String name, CubeListBuilder cubes, PartPose partPose) {
	}
}
