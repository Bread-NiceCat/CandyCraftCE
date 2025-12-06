package cn.breadnicecat.candycraftce.core.block.blocks

import net.minecraft.core.BlockPos
import net.minecraft.tags.BlockTags
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.FarmBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.gameevent.GameEvent

/**
 * Created by NiceCat on 2025/12/5.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class PuddingFarmBlock(properties: Properties) : FarmBlock(properties) {
    companion object {
        const val MAX_MOISTURE = 7
        val MOISTURE: IntegerProperty = BlockStateProperties.MOISTURE
        val PUDDING = CustardPuddingBlock.PUDDING_ALT
        fun turnToDirt(entity: Entity?, state: BlockState, level: Level, pos: BlockPos) {
            val blockState = pushEntitiesUp(state, PUDDING.defaultBlockState(), level, pos)
            level.setBlockAndUpdate(pos, blockState)
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState))
        }

        fun shouldMaintainFarmland(level: BlockGetter, pos: BlockPos): Boolean {
//            return level.getBlockState(pos.above()).`is`(BlockTags.MAINTAINS_FARMLAND)
            return level.getBlockState(pos.above()).`is`(BlockTags.MAINTAINS_FARMLAND)
        }
    }


}