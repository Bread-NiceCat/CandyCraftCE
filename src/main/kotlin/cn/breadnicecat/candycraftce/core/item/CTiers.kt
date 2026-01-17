package cn.breadnicecat.candycraftce.core.item

import cn.breadnicecat.candycraftce.core.tag.CTags
import net.minecraft.world.item.Tier
import net.minecraft.world.item.Tiers
import net.minecraft.world.item.crafting.Ingredient

enum class CTiers(
    @JvmField val level: Int,
    @JvmField val uses: Int,
    @JvmField val speed: Float,
    @JvmField val attack: Float,
    @JvmField val enchantment: Int,
    repair: () -> Ingredient,
) : Tier {
    //WOOD(0, 59, 2.0F, 0.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    //	STONE(1, 131, 4.0F, 1.0F, 5, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
    //	IRON(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(Items.IRON_INGOT)),
    //	DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(Items.DIAMOND)),
    //	GOLD(0, 32, 12.0F, 0.0F, 22, () -> Ingredient.of(Items.GOLD_INGOT)),
    //	NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> Ingredient.of(Items.NETHERITE_INGOT));

    MARSHMALLOW(
        Tiers.WOOD,
        repair = { Ingredient.of(CTags.marshmallow_planks.first) }),
    LICORICE(
        Tiers.STONE,
        repair = { Ingredient.of(CItems.licorice) }),
    HONEYCOMB(
        Tiers.IRON,
        repair = { Ingredient.of(CItems.honeycomb) }),
    PEZ(
        Tiers.DIAMOND,
        repair = { Ingredient.of(CItems.pez) });

    constructor(
        tier: Tier,
        level: Int = tier.level,
        uses: Int = tier.uses,
        speed: Float = tier.speed,
        attack: Float = tier.attackDamageBonus,
        enchantment: Int = tier.enchantmentValue,
        repair: () -> Ingredient = tier::getRepairIngredient,
    ) : this(level, uses, speed, attack, enchantment, repair)

    private val lazyRepair: Ingredient by lazy(repair)
    override fun getUses(): Int = uses

    override fun getSpeed(): Float = speed

    override fun getAttackDamageBonus(): Float = attack

    override fun getLevel(): Int = level

    override fun getEnchantmentValue(): Int = enchantment

    override fun getRepairIngredient(): Ingredient = lazyRepair
}