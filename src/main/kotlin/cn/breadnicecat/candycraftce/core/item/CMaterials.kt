package cn.breadnicecat.candycraftce.core.item

import cn.breadnicecat.candycraftce.core.tag.CTags
import cn.breadnicecat.candycraftce.utils.memoized
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ArmorMaterials
import net.minecraft.world.item.crafting.Ingredient

fun durabilityFunction(durability: Int): (ArmorItem.Type) -> Int {
    return { type ->
        durability * when (type) {
            ArmorItem.Type.BOOTS -> 13
            ArmorItem.Type.LEGGINGS -> 15
            ArmorItem.Type.CHESTPLATE -> 16
            ArmorItem.Type.HELMET -> 11
        }
    }
}

enum class CMaterials(
    @JvmField val id: String,
    @JvmField val durability: (ArmorItem.Type) -> Int,
    @JvmField val protection: (ArmorItem.Type) -> Int,
    @JvmField val enchantment: Int,
    @JvmField val sound: SoundEvent,
    @JvmField val toughness: Float,
    @JvmField val knockbackResistance: Float,
    repair: () -> Ingredient,
) : ArmorMaterial {

    JELLY_CROWN(
        id = "jelly_crown",
        ArmorMaterials.TURTLE,
        durability = durabilityFunction(15),
        protection = { 3 },
        enchantment = 25,
        repair = { Ingredient.EMPTY }),
    WATER_MASK(
        "water_mask",
        ArmorMaterials.TURTLE,
        repair = { Ingredient.of(CItems.cranfish_scale) }),

    TRAMPOJELLY_BOOTS(
        "trampojelly_boots",
        durabilityFunction(10),
        { 1 },
        8,
        SoundEvents.ARMOR_EQUIP_TURTLE,
        0F,
        0F,
        { Ingredient.of(/*TODO 减震果冻*/) }),

    LICORICE(
        id = "licorice",
        ArmorMaterials.CHAIN,
        repair = { Ingredient.of(CTags.CItemTags.licorice) }),
    HONEYCOMB(
        id = "honeycomb",
        ArmorMaterials.IRON,
        repair = { Ingredient.of(CTags.CItemTags.honeycomb) }),
    PEZ(
        id = "pez",
        ArmorMaterials.DIAMOND,
        repair = { Ingredient.of(CTags.CItemTags.pez) })
    ;

    constructor(
        id: String,
        material: ArmorMaterial,
        durability: (ArmorItem.Type) -> Int = material::getDurabilityForType,
        protection: (ArmorItem.Type) -> Int = material::getDefenseForType,
        enchantment: Int = material.enchantmentValue,
        sound: SoundEvent = material.equipSound,
        toughness: Float = material.toughness,
        knockbackResistance: Float = material.knockbackResistance,
        repair: () -> Ingredient = material::getRepairIngredient,
    ) : this(id, durability, protection, enchantment, sound, toughness, knockbackResistance, repair)

    val lazyRepair by lazy(repair)
    val cachedDuration by memoized(durability)
    val cacheProtection by memoized(protection)

    override fun getDurabilityForType(type: ArmorItem.Type): Int = cachedDuration(type)

    override fun getDefenseForType(type: ArmorItem.Type): Int = cacheProtection(type)

    override fun getEnchantmentValue(): Int = enchantment

    override fun getEquipSound(): SoundEvent? = sound

    override fun getRepairIngredient(): Ingredient? = lazyRepair

    override fun getName(): String? = id

    override fun getToughness(): Float = toughness

    override fun getKnockbackResistance(): Float = knockbackResistance
}