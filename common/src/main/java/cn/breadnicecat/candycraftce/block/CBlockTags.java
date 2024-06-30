package cn.breadnicecat.candycraftce.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashSet;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/12/31 8:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockTags {
	/**
	 * 用于{@link net.minecraft.data.tags.ItemTagsProvider#copy(net.minecraft.tags.TagKey, net.minecraft.tags.TagKey)}
	 */
	public static HashSet<BiTagKey> _pairs_ = new HashSet<>();
	//仅标记,不存在item
	public static final TagKey<Block> BT_CARAMEL_PORTAL_FRAME = create("caramel_portal_frame");
	public static final TagKey<Block> BT_CANDY_PLANT_SUITABLE = create("candy_plant_suitable");
	public static final TagKey<Block> BT_CARVER_OVERRIDEABLE = create("carver_overrideable");
	public static final TagKey<Block> BT_ORES = create("ores");
	//类似矿辞
	public static final BiTagKey BT_MARSHMALLOW_LOGS = bind("marshmallow_logs");
	public static final BiTagKey BT_MARSHMALLOW_PLANKS = bind("marshmallow_planks");
	public static final BiTagKey BT_JELLY = bind("jelly");
	public static final BiTagKey BT_ICE_CREAMS = bind("ice_cream");
	public static final BiTagKey BT_CHOCOLATES = bind("chocolate");
	public static final BiTagKey BT_SUGARY = bind("sugary");
	
	static {
		_pairs_.remove(BT_SUGARY);
	}
	
	private static BiTagKey bind(String name) {
		ResourceLocation location = prefix(name);
		TagKey<Block> k = TagKey.create(Registries.BLOCK, location);
		TagKey<Item> v = TagKey.create(Registries.ITEM, location);
		BiTagKey pair = new BiTagKey(k, v);
		_pairs_.add(pair);
		return pair;
	}
	
	private static TagKey<Block> create(String name) {
		return TagKey.create(Registries.BLOCK, prefix(name));
	}
	
	public record BiTagKey(TagKey<Block> b, TagKey<Item> i) {
	}
}
