package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;

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
	private ItemEntry<?> bucket;
	private BlockEntry<? extends LiquidBlock> sourceBlock;
	
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
	
	public Optional<ItemEntry<?>> getBucketEntry() {
		return Optional.ofNullable(bucket);
	}
	
	public Optional<BlockEntry<? extends LiquidBlock>> getBlockEntry() {
		return Optional.ofNullable(sourceBlock);
	}
	
	public Item getBucket() {
		return getBucketEntry().<Item>map(ItemEntry::get).orElse(Items.AIR);
	}
	
	public LiquidBlock getBlock() {
		return getBlockEntry().<LiquidBlock>map(BlockEntry::get).orElse((LiquidBlock) Blocks.WATER);
	}
	
	void lateinit(ItemEntry<?> bucket, BlockEntry<? extends LiquidBlock> sourceBlock) {
		this.bucket = bucket;
		this.sourceBlock = sourceBlock;
	}
}
