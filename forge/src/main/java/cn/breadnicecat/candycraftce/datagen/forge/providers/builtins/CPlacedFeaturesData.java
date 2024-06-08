package cn.breadnicecat.candycraftce.datagen.forge.providers.builtins;

import cn.breadnicecat.candycraftce.level.CPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static cn.breadnicecat.candycraftce.block.CBlockTags.BT_CANDY_PLANT_SUITABLE;
import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.level.CConfiguredFeatures.*;

/**
 * Created in 2024/5/1 下午2:12
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CPlacedFeaturesData extends CPlacedFeatures {
	
	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		context.register(CHOCOLATE_TREE_CHECKED, checkSurvive(kite(CHOCOLATE_TREE), CHOCOLATE_SAPLING.defaultBlockState()));
		context.register(CHOCOLATE_FANCY_TREE_CHECKED, checkSurvive(kite(CHOCOLATE_FANCY_TREE), CHOCOLATE_SAPLING.defaultBlockState()));
		
		context.register(WHITE_CHOCOLATE_TREE_CHECKED, checkSurvive(kite(WHITE_CHOCOLATE_TREE), WHITE_CHOCOLATE_SAPLING.defaultBlockState()));
		context.register(WHITE_CHOCOLATE_FANCY_TREE_CHECKED, checkSurvive(kite(WHITE_CHOCOLATE_FANCY_TREE), WHITE_CHOCOLATE_SAPLING.defaultBlockState()));
		context.register(CARAMEL_TREE_CHECKED, checkSurvive(kite(CARAMEL_TREE), CARAMEL_SAPLING.defaultBlockState()));
		context.register(CARAMEL_FANCY_TREE_CHECKED, checkSurvive(kite(CARAMEL_FANCY_TREE), CARAMEL_SAPLING.defaultBlockState()));
		context.register(MAGIC_FANCY_TREE_CHECKED, checkBelow(kite(MAGIC_FANCY_TREE), BT_CANDY_PLANT_SUITABLE));
		context.register(CANDIED_CHERRY_TREE_CHECKED, checkBelow(kite(CANDIED_CHERRY_TREE), BT_CANDY_PLANT_SUITABLE));
		
		
	}
	
	static PlacedFeature predicates(Holder<ConfiguredFeature<?, ?>> fe, BlockPredicate... pred) {
		return new PlacedFeature(fe,
				Arrays.stream(pred).
								<PlacementModifier>map(BlockPredicateFilter::forPredicate)
						.toList());
	}
	
	static PlacedFeature checkSurvive(Holder<ConfiguredFeature<?, ?>> cf, BlockState bs) {
		return predicates(cf, BlockPredicate.wouldSurvive(bs, Vec3i.ZERO));
	}
	
	static PlacedFeature checkHere(Holder<ConfiguredFeature<?, ?>> cf, Block... bs) {
		return predicates(cf, BlockPredicate.matchesBlocks(Vec3i.ZERO, bs));
	}
	
	static PlacedFeature checkBelow(Holder<ConfiguredFeature<?, ?>> cf, TagKey<Block> bs) {
		return predicates(cf, BlockPredicate.matchesTag(new Vec3i(0, -1, 0), bs));
	}
	
	static final HolderOwner<?> KITE_OWNER = new HolderOwner<>() {
		@Override
		public boolean canSerializeIn(@NotNull HolderOwner<Object> owner) {
			return true;
		}
	};
	
	/**
	 * 获取空头的Reference
	 */
	@SuppressWarnings("unchecked")
	protected static <V> Holder.Reference<V> kite(ResourceKey<V> key) {
		return Holder.Reference.createStandAlone((HolderOwner<V>) KITE_OWNER, key);
	}
}
