package cn.breadnicecat.candycraftce.core.block.blocks.plant

import cn.breadnicecat.candycraftce.utils.ModUtils.instance
import cn.breadnicecat.candycraftce.utils.TimeUnit.Companion.second
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * <p>
 *
 */
class GoldenSugarFlowerBlock(properties: Properties) : AcidMintFlower(properties) {
    override fun applyEffects(entity: LivingEntity) {
        entity.addEffect(MobEffects.REGENERATION.instance(2.second))
        entity.addEffect(MobEffects.MOVEMENT_SPEED.instance(2.second))
    }
}