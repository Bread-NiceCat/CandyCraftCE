package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import cn.breadnicecat.candycraftce.utils.tools.LazySupplier;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created in 2023/11/26 8:04
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public enum CTiers implements Tier {
	MARSHMALLOW(0, 59, 2.0F, 0.0F, 15,
			() -> Ingredient.of(CBlockTags.BT_MARSHMALLOW_PLANKS.i())),
	LICORICE(1, 131, 4.0F, 1.0F, 5,
			() -> Ingredient.of(CItems.LICORICE)),
	HONEYCOMB(2, 250, 6.0F, 2.0F, 14,
			() -> Ingredient.of(CItems.HONEYCOMB)),
	PEZ(3, 1561, 8.0F, 3.0F, 10,
			() -> Ingredient.of(CItems.PEZ));
	private final int level;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final LazySupplier<Ingredient> repairIngredient;
	
	CTiers(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
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
	public int getLevel() {
		return level;
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
