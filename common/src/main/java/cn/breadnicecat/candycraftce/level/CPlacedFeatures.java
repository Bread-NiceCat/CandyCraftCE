package cn.breadnicecat.candycraftce.level;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/5/1 下午2:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * 仅列举部分使用到的
 */
public class CPlacedFeatures {
	public final static ResourceKey<PlacedFeature> CHOCOLATE_TREE_CHECKED = bind("chocolate_tree_checked");
	public final static ResourceKey<PlacedFeature> CHOCOLATE_FANCY_TREE_CHECKED = bind("chocolate_fancy_tree_checked");
	
	public final static ResourceKey<PlacedFeature> WHITE_CHOCOLATE_TREE_CHECKED = bind("white_chocolate_tree_checked");
	public final static ResourceKey<PlacedFeature> WHITE_CHOCOLATE_FANCY_TREE_CHECKED = bind("white_chocolate_fancy_tree_checked");
	
	public final static ResourceKey<PlacedFeature> CARAMEL_TREE_CHECKED = bind("caramel_tree_checked");
	public final static ResourceKey<PlacedFeature> CARAMEL_FANCY_TREE_CHECKED = bind("caramel_fancy_tree_checked");
	
	public final static ResourceKey<PlacedFeature> CANDIED_CHERRY_TREE_CHECKED = bind("candied_cherry_tree_checked");
	
	protected static ResourceKey<PlacedFeature> bind(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, prefix(name));
	}
}
