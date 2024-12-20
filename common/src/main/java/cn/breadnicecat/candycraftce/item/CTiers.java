package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import cn.breadnicecat.candycraftce.utils.tools.LazySupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static net.minecraft.tags.BlockTags.*;

/**
 * Created in 2023/11/26 8:04
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public enum CTiers implements Tier {
	MARSHMALLOW(INCORRECT_FOR_WOODEN_TOOL, 0, 59, 2.0F, 0.0F, 15,
			() -> Ingredient.of(CBlockTags.BT_MARSHMALLOW_PLANKS.it())),
	LICORICE(INCORRECT_FOR_STONE_TOOL, 1, 131, 4.0F, 1.0F, 5,
			() -> Ingredient.of(CItems.LICORICE)),
	HONEYCOMB(INCORRECT_FOR_IRON_TOOL, 2, 250, 6.0F, 2.0F, 14,
			() -> Ingredient.of(CItems.HONEYCOMB)),
	PEZ(INCORRECT_FOR_DIAMOND_TOOL, 3, 1561, 8.0F, 3.0F, 10,
			() -> Ingredient.of(CItems.PEZ));
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final LazySupplier<Ingredient> repairIngredient;
	private final int level;
	private final TagKey<Block> incorrectBlocksForDrops;
	
	CTiers(TagKey<Block> incorrectBlocksForDrops, int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
		this.incorrectBlocksForDrops = incorrectBlocksForDrops;
		this.level = level;
		this.uses = uses;
		this.speed = speed;
		this.damage = damage;
		this.enchantmentValue = enchantmentValue;
		this.repairIngredient = LazySupplier.of(repairIngredient);
	}
	
	@Override
	public int getUses() {
		return uses;
	}
	
	@Override
	public float getSpeed() {
		return speed;
	}
	
	@Override
	public float getAttackDamageBonus() {
		return damage;
	}
	
	@Override
	public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
		return incorrectBlocksForDrops;
	}
	
	@Override
	public int getEnchantmentValue() {
		return enchantmentValue;
	}
	
	@Override
	public @NotNull Ingredient getRepairIngredient() {
		return repairIngredient.get();
	}
	
	//Forge Override
	public @Nullable TagKey<Block> getTag() {
		return switch (level) {
			case 1 -> BlockTags.NEEDS_STONE_TOOL;
			case 2 -> BlockTags.NEEDS_IRON_TOOL;
			case 3 -> BlockTags.NEEDS_DIAMOND_TOOL;
			default -> null;
		};
	}
}
