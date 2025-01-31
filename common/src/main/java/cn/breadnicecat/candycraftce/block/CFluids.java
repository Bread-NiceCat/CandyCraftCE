package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.fluids.CaramelFluid;
import cn.breadnicecat.candycraftce.block.fluids.GrenadineFluid;
import cn.breadnicecat.candycraftce.item.CItemBuilder;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid.Flowing;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid.Source;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.core.item.ArchitecturyBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/4/4 0:38
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CFluids {
	private static final Logger LOGGER = CLogUtils.sign();
	public static final Map<String, FlowingFluidEntry<?, ?>> FLUIDS = new HashMap<>();
	public static FlowingFluidEntry<Source, Flowing> CARAMEL_FLUID = register("caramel",
			CaramelFluid.Source::new, CaramelFluid.Flowing::new,
			(e, a) -> a
					.sourceTexture(prefix("block/caramel"))
					.flowingTexture(a.getSourceTexture())
					.density(3000)
					.viscosity(6000)
					.temperature(1300)
					.emptySound(SoundEvents.BUCKET_EMPTY_LAVA)
					.fillSound(SoundEvents.BUCKET_FILL_LAVA)
					.luminosity(15)
			, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA),
			new Item.Properties().stacksTo(1));
	
	public static FlowingFluidEntry<Source, Flowing> GRENADINE_FLUID = register("grenadine",
			GrenadineFluid.Source::new, GrenadineFluid.Flowing::new,
			(e, a) -> a
					.sourceTexture(prefix("block/grenadine_static"))
					.flowingTexture(prefix("block/grenadine_flowing"))
					.emptySound(SoundEvents.BUCKET_EMPTY)
					.fillSound(SoundEvents.BUCKET_FILL)
			, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WATER),
			new Item.Properties().stacksTo(1));
	
	private static <S extends Source, F extends Flowing> FlowingFluidEntry<S, F> register(
			String name,
			Function<ArchitecturyFluidAttributes, S> sourceFactory,
			Function<ArchitecturyFluidAttributes, F> flowingFactory,
			BiConsumer<FlowingFluidEntry<S, F>, SimpleArchitecturyFluidAttributes> attrFactory,
			@Nullable Supplier<BlockBehaviour.Properties> blockProp,
			@Nullable Item.Properties bucketProp
	) {
		var ref = new Object() {
			FlowingFluidEntry<S, F> entry;
			SimpleArchitecturyFluidAttributes attr = SimpleArchitecturyFluidAttributes.ofSupplier(() -> entry.getFlowing(), () -> entry.getSource());
		};
		ref.entry = register(name,
				() -> sourceFactory.apply(ref.attr),
				() -> flowingFactory.apply(ref.attr),
				ref.attr
		);
		BlockEntry<ArchitecturyLiquidBlock> be = blockProp == null ? null : CBlockBuilder.register(name, () -> new ArchitecturyLiquidBlock(ref.entry.getSource(), blockProp.get()));
		ItemEntry<ArchitecturyBucketItem> ie = bucketProp == null ? null : CItemBuilder.create(name + "_bucket", (prop) -> new ArchitecturyBucketItem(() -> ref.entry.getSource().get(), prop)).setProperties(bucketProp).save();
		CandyCraftCE.hookPostBootstrap(() -> {
					if (be != null) ref.attr.block(be::optional);
					if (bucketProp != null) ref.attr.bucketItem(() -> ie.optional().map($ -> $));
					attrFactory.accept(ref.entry, ref.attr);
				}
		);
		return ref.entry;
	}
	
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static <S extends FlowingFluid, F extends FlowingFluid> FlowingFluidEntry<S, F> register(String name, Supplier<S> source, Supplier<F> flowing, SimpleArchitecturyFluidAttributes attr) {
		ResourceLocation fId = prefix(name + "_flowing");
		ResourceLocation sId = prefix(name);
		SimpleEntry fe = CandyCraftCE.register(BuiltInRegistries.FLUID, fId, flowing);
		SimpleEntry se = CandyCraftCE.register(BuiltInRegistries.FLUID, sId, source);
		FlowingFluidEntry<S, F> entry = new FlowingFluidEntry<>(se, fe, attr);
		FLUIDS.put(name, entry);
		return entry;
	}
	
	public static void init() {
	}
}
