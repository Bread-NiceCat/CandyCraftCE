package cn.breadnicecat.candycraftce.utils.geom;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

/**
 * Created in 2023/8/25 23:16
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class BiPartPose extends PartPose {
	private final float xScale, yScale, zScale;

	public BiPartPose(float x, float y, float z, float xr, float yr, float zr, float xs, float ys, float zs) {
		super(x, y, z, xr, yr, zr);
		this.xScale = xs;
		this.yScale = ys;
		this.zScale = zs;
	}

	public void apply(ModelPart part) {
		part.loadPose(this);
		part.xScale = this.xScale;
		part.yScale = this.yScale;
		part.zScale = this.zScale;
	}

	public void reactiveApply(ModelPart part) {
		part.x += x;
		part.y += y;
		part.z += z;
		part.xRot += xRot;
		part.yRot += yRot;
		part.zRot += zRot;
		part.xScale += xScale;
		part.yScale += yScale;
		part.zScale += zScale;
	}
}
