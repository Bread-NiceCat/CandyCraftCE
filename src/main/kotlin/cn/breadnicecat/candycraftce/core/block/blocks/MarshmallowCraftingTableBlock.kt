package cn.breadnicecat.candycraftce.core.block.blocks

import net.minecraft.core.BlockPos
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.CraftingMenu
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.CraftingTableBlock
import net.minecraft.world.level.block.state.BlockState

/**
 * Created by NiceCat on 2026/3/15.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class MarshmallowCraftingTableBlock(properties: Properties) : CraftingTableBlock(properties) {
    override fun getMenuProvider(state: BlockState, level: Level, pos: BlockPos) =
        SimpleMenuProvider({ id, inventory, _ ->
            val access = ContainerLevelAccess.create(level, pos)
            object : CraftingMenu(id, inventory, access) {
                override fun stillValid(player: Player) = stillValid(access, player, this@MarshmallowCraftingTableBlock)
            }
        }, name)
}