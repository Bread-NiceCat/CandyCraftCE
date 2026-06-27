package cn.breadnicecat.candycraftce.core.block.blocks.jelly

import cn.breadnicecat.candycraftce.core.tag.CTags
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.redstone.Redstone
import net.minecraft.world.phys.AABB

/**
 * Created by NiceCat on 2026/2/27.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class SensitiveJellyBlock(properties: Properties) : JellyBlock(properties) {
    companion object {
        val POWERED: BooleanProperty = BlockStateProperties.POWERED
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(POWERED, false))
    }

    @Deprecated("Deprecated in Java")
    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        level.scheduleTick(pos, this, 1)
    }

    @Deprecated("Deprecated in Java")
    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        var detectPos: BlockPos = pos
        val above = pos.above()
        val aboveState = level.getBlockState(above)
        if (aboveState.`is`(CTags.jelly.block) && !aboveState.`is`(this)) {
            detectPos = above
        }
        val powered = !level.getEntitiesOfClass(
            LivingEntity::class.java, AABB(
                detectPos.x.toDouble(),
                (detectPos.y + 1).toDouble(),
                detectPos.z.toDouble(),
                (detectPos.x + 1).toDouble(),
                detectPos.y + 1.5,
                (detectPos.z + 1).toDouble()
            )
        ).isEmpty()

        if (powered != state.getValue(POWERED)) {
            level.setBlockAndUpdate(pos, state.setValue(POWERED, powered))
        }
        level.scheduleTick(pos, this, 1)
    }

    protected override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(POWERED)
        super.createBlockStateDefinition(builder)
    }

    @Deprecated("Deprecated in Java")
    override fun isSignalSource(state: BlockState) = true

    @Deprecated("Deprecated in Java")
    override fun getSignal(state: BlockState, level: BlockGetter, pos: BlockPos, direction: Direction): Int {
        return if (state.getValue(POWERED)) Redstone.SIGNAL_MAX else Redstone.SIGNAL_NONE
    }


}