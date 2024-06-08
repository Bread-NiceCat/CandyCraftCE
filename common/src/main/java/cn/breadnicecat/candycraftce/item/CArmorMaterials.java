package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import cn.breadnicecat.candycraftce.utils.tools.LazySupplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.block.CBlocks.JELLY_SHOCK_ABSORBER;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;

/**
 * Created in 2023/11/25 20:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public enum CArmorMaterials implements ArmorMaterial {
	JELLY_CROWN("jelly_crown", 15, new int[]{4, 0, 0, 0}, 20, SoundEvents.ARMOR_EQUIP_TURTLE, 0F, 0F, () -> Ingredient.EMPTY),
	TRAMPOJELLY_BOOTS("trampojelly_boots", 10, new int[4], 8, SoundEvents.ARMOR_EQUIP_TURTLE, 0F, 0F, () -> Ingredient.of(JELLY_SHOCK_ABSORBER)),
	WATER_MASK("water_mask", 10, new int[4], 8, SoundEvents.ARMOR_EQUIP_TURTLE, 0F, 0F, () -> Ingredient.of(CItems.CRANFISH_SCALE.get())),
	
	LICORICE("licorice", 13, new int[]{1, 4, 5, 2}, 12, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> Ingredient.of(CItems.LICORICE)),
	HONEYCOMB("honeycomb", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(CItems.HONEYCOMB)),
	PEZ("pez", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(CItems.PEZ));

//	JAWBREAKER("jawbreaker", 37, new int[]{3, 6, 8, 3}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> Ingredient.of(CItemTags.JAWBREAKER.getTagKey()));
	
	/**
	 * 索引同{@link ArmorItem.Type}
	 */
	private static final int[] HEALTH_PER_TYPE = {11, 16, 15, 13};
	
	private final String name;
	private final int durabilityMultiplier;
	/**
	 * 索引同{@link ArmorItem.Type}
	 */
	private final int[] protectionFunctionForType;
	private final int enchantmentValue;
	private final SoundEvent sound;
	private final float toughness;
	private final float knockbackResistance;
	private final LazySupplier<Ingredient> repairIngredient;
	
	/**
	 * @param name                 名字
	 * @param durabilityMultiplier 耐久基数，四个栏位分别乘11，15，16，13
	 * @param protectionPerType    四个栏位分别提供的护甲值 [4]
	 * @param enchantmentValue     附魔幸运加成
	 * @param sound                穿上时的声音
	 * @param toughness            盔甲韧性
	 * @param knockbackResistance  抗击退
	 * @param repairIngredient     修复材料
	 */
	CArmorMaterials(String name, int durabilityMultiplier, int[] protectionPerType, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
		this.name = ResourceUtils.simplePrefix(name);
		this.durabilityMultiplier = durabilityMultiplier;
		assertTrue(protectionPerType.length == 4, "protectionPerType.length must equals 4 ,even you'll never use the rest");
		this.protectionFunctionForType = protectionPerType;
		this.enchantmentValue = enchantmentValue;
		this.sound = sound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = LazySupplier.of(repairIngredient);
	}
	
	@Override
	public int getDurabilityForType(ArmorItem.Type type) {
		return HEALTH_PER_TYPE[type.ordinal()] * this.durabilityMultiplier;
	}
	
	@Override
	public int getDefenseForType(ArmorItem.Type type) {
		return this.protectionFunctionForType[type.ordinal()];
	}
	
	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}
	
	@Override
	public @NotNull SoundEvent getEquipSound() {
		return this.sound;
	}
	
	@Override
	public @NotNull Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
	
	@Override
	public @NotNull String getName() {
		return this.name;
	}
	
	@Override
	public float getToughness() {
		return this.toughness;
	}
	
	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
	
}
