package cn.breadnicecat.candycraftce.misc;

import cn.breadnicecat.candycraftce.utils.tools.LazySupplier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created in 2025/1/25 09:13
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class TierImpl implements Tier {
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final LazySupplier<Ingredient> repairIngredient;
//	private final int level;
	private final TagKey<Block> incorrectBlocksForDrops;
	public TierImpl(TagKey<Block> incorrectBlocksForDrops, /*int level,*/ int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
		this.incorrectBlocksForDrops = incorrectBlocksForDrops;
//		this.level = level;
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
}
