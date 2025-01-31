package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import cn.breadnicecat.candycraftce.utils.tools.LazySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.slf4j.Logger;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.block.CBlocks.JELLY_SHOCK_ABSORBER;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.make;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/11/25 20:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public enum CArmorMaterials {
	JELLY_CROWN("jelly_crown", 15, new int[]{4}, 20, SoundEvents.ARMOR_EQUIP_TURTLE, 0F, 0F, () -> Ingredient.EMPTY),
	TRAMPOJELLY_BOOTS("trampojelly_boots", 10, new int[4], 8, SoundEvents.ARMOR_EQUIP_TURTLE, 0F, 0F, () -> Ingredient.of(JELLY_SHOCK_ABSORBER)),
	WATER_MASK("water_mask", 10, new int[4], 8, SoundEvents.ARMOR_EQUIP_TURTLE, 0F, 0F, () -> Ingredient.of(CItems.CRANFISH_SCALE.get())),
	
	LICORICE("licorice", 13, new int[]{1, 4, 5, 2, 4}, 12, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> Ingredient.of(CItems.LICORICE)),
	HONEYCOMB("honeycomb", 15, new int[]{2, 5, 6, 2, 5}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(CItems.HONEYCOMB)),
	PEZ("pez", 33, new int[]{3, 6, 8, 3, 11}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(CItems.PEZ));
	
	private static final Logger LOGGER = CLogUtils.sign();
	public final int durabilityMultiplier;
	public final SimpleEntry<ArmorMaterial, ArmorMaterial> wrapper;
	
	CArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionFunctionForType, int enchantmentValue, Holder<SoundEvent> sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
		this.durabilityMultiplier = durabilityMultiplier;
		List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(prefix(name)));
		repairIngredient = LazySupplier.of(repairIngredient);
		wrapper = register(name, protectionFunctionForType, enchantmentValue, sound, toughness, knockbackResistance, repairIngredient, list);
	}
	
	CArmorMaterials(String name, int durabilityMultiplier, int[] protectionFunctionForType, int enchantmentValue, Holder<SoundEvent> sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
		this(name, durabilityMultiplier, make(new EnumMap<>(ArmorItem.Type.class), map -> {
			for (int j = 0; j < protectionFunctionForType.length; j++) {
				map.put(ArmorItem.Type.values()[j], protectionFunctionForType[j]);
			}
		}), enchantmentValue, sound, toughness, knockbackResistance, repairIngredient);
	}
	
	private static SimpleEntry<ArmorMaterial, ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngridient, List<ArmorMaterial.Layer> layers) {
		EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);
		for (ArmorItem.Type type : ArmorItem.Type.values()) {
			enumMap.put(type, defense.get(type));
		}
		return CandyCraftCE.register(BuiltInRegistries.ARMOR_MATERIAL, prefix(name), () -> new ArmorMaterial(enumMap, enchantmentValue, equipSound, repairIngridient, layers, toughness, knockbackResistance));
	}
	
	public Holder<ArmorMaterial> getHolder() {
		return wrapper.getHolder();
	}
	
	public static void init() {
	}
}
