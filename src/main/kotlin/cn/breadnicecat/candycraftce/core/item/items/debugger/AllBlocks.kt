package cn.breadnicecat.candycraftce.core.item.items.debugger

import cn.breadnicecat.candycraftce.data.CDataUtils.translate
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

/**
 * Created by NiceCat on 2026/1/16.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object AllBlocks : DebugFunc<DebugFunc.Data.UnitData>() {
    override val id: String
        get() = "all_blocks"
    override val displayName: Component = Component.translatable("$transPrefix.fun.all_blocks")
        .translate("All Blocks", "所有方块")

    override fun onSave(
        data: Data.UnitData,
        nbt: CompoundTag,
    ) {
    }

    override fun onLoad(nbt: CompoundTag) = Data.UnitData
    override fun rightClickOn(
        data: Data.UnitData,
        level: Level,
        pos: BlockPos,
        clickedFace: Direction,
        player: Player,
        item: ItemStack,
    ): Boolean {
        return super.rightClickOn(data, level, pos, clickedFace, player, item)
    }
}