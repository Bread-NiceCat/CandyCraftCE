package cn.breadnicecat.candycraftce.level;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/2/7 12:24
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * 仅列举部分使用到的
 */
public class CConfiguredFeatures {
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_TREE = bind("chocolate_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> CHOCOLATE_FANCY_TREE = bind("chocolate_fancy_tree");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHOCOLATE_TREE = bind("white_chocolate_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHOCOLATE_FANCY_TREE = bind("white_chocolate_fancy_tree");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> CARAMEL_TREE = bind("caramel_tree");
	public final static ResourceKey<ConfiguredFeature<?, ?>> CARAMEL_FANCY_TREE = bind("caramel_fancy_tree");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> MAGICAL_TREE = bind("magical_tree");
	
	public final static ResourceKey<ConfiguredFeature<?, ?>> CANDIED_CHERRY_TREE = bind("candied_cherry_tree");
	
	protected static ResourceKey<ConfiguredFeature<?, ?>> bind(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, prefix(name));
	}
}
