package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import cn.breadnicecat.candycraftce.misc.TierImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
import static net.minecraft.tags.BlockTags.*;

/**
 * Created in 2023/11/26 8:04
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public abstract class CTiers {
	public static  final Tier MARSHMALLOW=of(INCORRECT_FOR_WOODEN_TOOL,  59, 2.0F, 0.0F, 15,() -> Ingredient.of(CBlockTags.BT_MARSHMALLOW_PLANKS.it()));
	public static  final Tier LICORICE=of(INCORRECT_FOR_STONE_TOOL,  131, 4.0F, 1.0F, 5,() -> Ingredient.of(CItems.LICORICE));
	public static  final Tier HONEYCOMB=of(INCORRECT_FOR_IRON_TOOL,  250, 6.0F, 2.0F, 14,() -> Ingredient.of(CItems.HONEYCOMB));
	public static  final Tier PEZ=of(INCORRECT_FOR_DIAMOND_TOOL,  1561, 8.0F, 3.0F, 10,() -> Ingredient.of(CItems.PEZ));
	
	public static @NotNull Tier of(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
		return new TierImpl(incorrectBlocksForDrops, uses, speed, damage, enchantmentValue, repairIngredient);
	}
	
}
