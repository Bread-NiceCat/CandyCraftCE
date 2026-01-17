package cn.breadnicecat.candycraftce.core.item.items.debugger

import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.component1
import cn.breadnicecat.candycraftce.utils.CUtils.component2
import cn.breadnicecat.candycraftce.utils.CUtils.component3
import cn.breadnicecat.candycraftce.utils.CUtils.ifClient
import net.minecraft.ChatFormatting
import net.minecraft.ChatFormatting.GREEN
import net.minecraft.ChatFormatting.YELLOW
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Component.translatable
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

/**
 * Created by NiceCat on 2025/12/11.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object Measuring : DebugFunc<Measuring.MeasuringData>() {
    override val id: String = "measuring"
    override val displayName: Component = translatable(transPrefix)
        .withStyle(ChatFormatting.AQUA)
        .translate("Measuring", "测距")
    val set_tip = translatable("$transPrefix.set")
        .withStyle(YELLOW)
        .translate("Left Click to set zero", "左键 设置调零")
    val reset_tip = translatable("$transPrefix.reset")
        .withStyle(YELLOW)
        .translate("Press Shift+Left Click to reset", "SHIFT+左键 清除调零")
    val get_tip = translatable("$transPrefix.get")
        .withStyle(YELLOW)
        .translate("Right Click to measure coords", "右键 获取坐标")

    val zero_tip_pre = translatable("$transPrefix.zero_pre")
        .withStyle(GREEN)
        .translate("Zero Pos: ", "当前零点: ")


    override fun onSave(
        data: MeasuringData,
        nbt: CompoundTag,
    ) {
        val (x, y, z) = data.getZero()
        nbt.putInt("x", x)
        nbt.putInt("y", y)
        nbt.putInt("z", z)
    }

    override fun appendTooltip(
        data: MeasuringData,
        item: ItemStack,
        tooltips: MutableList<Component>,
        isAdvanced: TooltipFlag,
    ) {
        tooltips.add(set_tip)
        tooltips.add(reset_tip)
        tooltips.add(get_tip)
        tooltips.add(zero_tip_pre.copy().append(data.zeroToString()))
    }

    override fun leftClickOn(
        data: MeasuringData,
        level: Level,
        pos: BlockPos,
        clickedFace: Direction,
        player: Player,
        item: ItemStack,
    ): Boolean {
        if (player.isShiftKeyDown) {
            data.reset()
        } else {
            data.setZero(pos)
        }
        sendMessage(player, zero_tip_pre.copy().append(data.zeroToString()), level.isClientSide)
        return true
    }

    override fun rightClickOn(
        data: MeasuringData,
        level: Level,
        pos: BlockPos,
        clickedFace: Direction,
        player: Player,
        item: ItemStack,
    ): Boolean {
        sendMessage(player, data.get(pos).toShortString(), level.isClientSide) {
            withStyle(GREEN)
        }
        level.ifClient {
            CUtils.particleBlock(ParticleTypes.HAPPY_VILLAGER, it, pos, 0.25)
        }
        return true
    }

    override fun onLoad(nbt: CompoundTag): MeasuringData {
        return MeasuringData(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"))
    }

    override fun onInventoryTick(
        data: MeasuringData,
        level: Level,
        player: Player,
        item: ItemStack,
        isInHand: Boolean,
    ) {
        if (isInHand && level is ClientLevel && data.shouldRenderZero() && player.tickCount % 3 == 0) {
            val zero = data.getZero()
            if (player.position().distanceTo(zero.center) <= 32) {
                CUtils.particleBlock(ParticleTypes.FLAME, level, zero, 0.25)
            }
        }
    }


    class MeasuringData(x0: Int, y0: Int, z0: Int) : Data() {
        private val zero: BlockPos.MutableBlockPos = BlockPos.MutableBlockPos(x0, y0, z0)
        fun shouldRenderZero(): Boolean = !isZeroZero()

        fun isZeroZero() = zero.x == 0 && zero.y == 0 && zero.z == 0
        fun reset() = setZero(0, 0, 0)

        fun setZero(blockPos: BlockPos) = setZero(blockPos.x, blockPos.y, blockPos.z)

        fun setZero(x0: Int, y0: Int, z0: Int) {
            zero.set(x0, y0, z0)
            setDirty()
        }

        fun getZero(): BlockPos {
            return zero.immutable()
        }

        fun zeroToString(): String {
            return zero.toShortString()
        }

        fun get(blockPos: BlockPos): BlockPos {
            if (isZeroZero()) return blockPos
            return blockPos.offset(-zero.x, -zero.y, -zero.z)
        }
    }
}