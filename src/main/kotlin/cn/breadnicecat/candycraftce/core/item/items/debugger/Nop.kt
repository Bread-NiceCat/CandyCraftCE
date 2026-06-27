package cn.breadnicecat.candycraftce.core.item.items.debugger

import cn.breadnicecat.candycraftce.data.CDataUtils.translate
import cn.breadnicecat.candycraftce.utils.CLogUtils.clog
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

/**
 * Created by NiceCat on 2025/12/12.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object Nop : DebugFunc<DebugFunc.Data.UnitData>() {

    override val id: String = "nop"
    override val displayName: Component = Component.translatable(transPrefix)
        .withStyle(ChatFormatting.RED)
        .translate("Nop", "无操作")

    private fun withSide(level: Level): String = when (level.isClientSide) {
        true -> "Client"; false -> "Server"
    }

    override fun onSave(data: Data.UnitData, nbt: CompoundTag) {
        clog.info("onSave")
    }

    override fun onLoad(nbt: CompoundTag): Data.UnitData {
        clog.info("onLoad")
        return Data.UnitData
    }

    override fun use(
        data: Data.UnitData,
        level: Level,
        player: Player,
        item: ItemStack,
    ): Boolean {
        clog.info("use ${withSide(level)}")
        return super.use(data, level, player, item)
    }

    override fun rightClickOn(
        data: Data.UnitData,
        level: Level,
        pos: BlockPos,
        clickedFace: Direction,
        player: Player,
        item: ItemStack,
    ): Boolean {
        clog.info("rightClickOn ${withSide(level)}")
        return super.rightClickOn(data, level, pos, clickedFace, player, item)
    }

    override fun leftClickOn(
        data: Data.UnitData,
        level: Level,
        pos: BlockPos,
        clickedFace: Direction,
        player: Player,
        item: ItemStack,
    ): Boolean {
        clog.info("leftClickOn ${withSide(level)}")
        return super.leftClickOn(data, level, pos, clickedFace, player, item)
    }

    override fun onActive(data: Data.UnitData) {
        clog.info("onActive")
        super.onActive(data)
    }

    override fun onInactive(data: Data.UnitData) {
        clog.info("onInactive")
        super.onInactive(data)
    }
}