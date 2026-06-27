package cn.breadnicecat.candycraftce.core.block.blocks

import cn.breadnicecat.candycraftce.core.block.blockentities.LicoriceFurnaceBE
import cn.breadnicecat.candycraftce.utils.ifServer
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.AbstractFurnaceBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BooleanProperty

/**
 * Created by NiceCat on 2026/3/15.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class LicoriceFurnaceBlock(properties: Properties) : AbstractFurnaceBlock(properties) {
    companion object {
        val LIT: BooleanProperty = AbstractFurnaceBlock.LIT
    }

    override fun newBlockEntity(
        pos: BlockPos,
        state: BlockState,
    ): BlockEntity = LicoriceFurnaceBE(pos, state)


    override fun openContainer(
        level: Level,
        pos: BlockPos,
        player: Player,
    ) {
        val state = level.getBlockState(pos)
        level.ifServer {
            player.openMenu(state.getMenuProvider(level, pos))
        }
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>,
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { level, pos, state, blockEntity ->
            level.ifServer {
                (blockEntity as? LicoriceFurnaceBE)?.serverTick()
            }
        }
    }


}