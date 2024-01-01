package cn.breadnicecat.candycraftce.registration.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/12/31 8:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockTags {
	public static final TagKey<Block> CARAMEL_PORTAL_FRAME = cbind("caramel_portal_frame");

	private static TagKey<Block> cbind(String name) {
		return TagKey.create(Registries.BLOCK, prefix(name));
	}
}
