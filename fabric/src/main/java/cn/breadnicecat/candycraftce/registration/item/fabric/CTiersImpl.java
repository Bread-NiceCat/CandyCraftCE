//package cn.breadnicecat.candycraftce.registration.item.fabric;
//
//import net.minecraft.world.item.Tier;
//import net.minecraft.world.item.crafting.Ingredient;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.function.Supplier;
//
///**
// * Created in 2023/10/29 11:53
// * Project: candycraftce
// *
// * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
// * <p>
// */
//public class CTiersImpl implements Tier {
//	private final int level;
//	private final int uses;
//	private final float speed;
//	private final float damage;
//	private final int enchantmentValue;
//	private final Supplier<Ingredient> repairIngredient;
//
//	public CTiersImpl(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
//		this.level = level;
//		this.uses = uses;
//		this.speed = speed;
//		this.damage = damage;
//		this.enchantmentValue = enchantmentValue;
//		this.repairIngredient = repairIngredient;
//	}
//
//	@Override
//	public int getUses() {
//		return uses;
//	}
//
//	@Override
//	public float getSpeed() {
//		return speed;
//	}
//
//	@Override
//	public float getAttackDamageBonus() {
//		return damage;
//	}
//
//	@Override
//	public int getLevel() {
//		return level;
//	}
//
//	@Override
//	public int getEnchantmentValue() {
//		return enchantmentValue;
//	}
//
//	@Override
//	public @NotNull Ingredient getRepairIngredient() {
//		return repairIngredient.get();
//	}
//
//
//	public static Tier create(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient/*, @NotNull TagKey<Block> __ignore_forgeLevelTag__*/) {
//		return new CTiersImpl(level, uses, speed, damage, enchantmentValue, repairIngredient);
//	}
//}
