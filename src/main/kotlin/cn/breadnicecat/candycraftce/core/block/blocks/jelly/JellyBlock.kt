package cn.breadnicecat.candycraftce.core.block.blocks.jelly

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

/**
 * Created by NiceCat on 2026/2/27.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
open class JellyBlock(properties: Properties) : Block(properties) {
    companion object {
        val shape: VoxelShape = Shapes.create(0.0, 0.0, 0.0, 1.0, 0.995, 1.0)
    }

    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext,
    ): VoxelShape = shape
}