package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

/**
 * Created in 2024/4/4 0:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class FlowingFluidEntry<S extends FlowingFluid, F extends FlowingFluid> extends SimpleEntry<FlowingFluid, S> {
	private final SimpleEntry<FlowingFluid, F> flowing;
	private final SimpleArchitecturyFluidAttributes attributes;
	
	public FlowingFluidEntry(SimpleEntry<FlowingFluid, S> source, SimpleEntry<FlowingFluid, F> flowing, SimpleArchitecturyFluidAttributes attr) {
		super(source);
		this.flowing = flowing;
		this.attributes = attr;
	}
	
	public SimpleArchitecturyFluidAttributes getAttributes() {
		return attributes;
	}
	
	public SimpleEntry<FlowingFluid, S> getSource() {
		return this;
	}
	
	public SimpleEntry<FlowingFluid, F> getFlowing() {
		return flowing;
	}
	
	public boolean isSource(Fluid fluid) {
		return get().isSame(fluid);
	}
	
	public boolean isFlowing(Fluid fluid) {
		return get().isSame(fluid);
	}
	
	public boolean isSimilar(Fluid fluid) {
		return isFlowing(fluid) || isSource(fluid);
	}
	
	public Item getBucket() {
		return attributes.getBucketItem();
	}
	
	public LiquidBlock getBlock() {
		return attributes.getBlock();
	}
	
}
