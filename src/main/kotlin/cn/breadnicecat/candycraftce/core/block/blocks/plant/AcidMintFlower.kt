package cn.breadnicecat.candycraftce.core.block.blocks.plant

import cn.breadnicecat.candycraftce.utils.CUtils.instance
import cn.breadnicecat.candycraftce.utils.MCTimeUnit
import cn.breadnicecat.candycraftce.utils.MCTimeUnit.Companion.second
import net.minecraft.core.BlockPos
import net.minecraft.world.effect.MobEffects.CONFUSION
import net.minecraft.world.effect.MobEffects.POISON
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * <p>
 *
 */
open class AcidMintFlower(properties: Properties) : CandyPlantBlock(properties) {
    protected var probability = 0.3f//每秒30%的概率

    @Deprecated("Deprecated in Java")
    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (entity is LivingEntity && level.random.nextFloat() < probability / MCTimeUnit.tps) {
            applyEffects(entity)
        }
    }

    open fun applyEffects(entity: LivingEntity) {
        entity.addEffect(POISON.instance(2.second))
        entity.addEffect(CONFUSION.instance(2.5f.second))
    }
}