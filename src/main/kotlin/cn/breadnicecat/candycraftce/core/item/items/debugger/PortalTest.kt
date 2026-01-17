package cn.breadnicecat.candycraftce.core.item.items.debugger

import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.multiblock.caramel_portal.PortalConfig
import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.ifClient
import com.mojang.serialization.JsonOps
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.chat.Component
import net.minecraft.util.GsonHelper
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import kotlin.time.measureTime

/**
 * Created by NiceCat on 2025/12/13.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object PortalTest : DebugFunc<DebugFunc.Data.UnitData>() {
    override val id: String = "portal_test"
    override val displayName: Component = Component.translatable(transPrefix)
        .withStyle(ChatFormatting.LIGHT_PURPLE)
        .translate("Portal Test", "传送门测试")
    val test_tip: Component = Component.translatable("$transPrefix.test")
        .withStyle(ChatFormatting.YELLOW)
        .translate("Right Click to Find Portal", "右键点击寻找传送门")
    val notfound_tip = Component.translatable("$transPrefix.notfound")
        .withStyle(ChatFormatting.RED)
        .translate("Portal Frame not found", "未找到传送门框架")
    val found_tip = Component.translatable("$transPrefix.found")
        .withStyle(ChatFormatting.GREEN)
        .translate("Portal Frame found", "找到传送门框架")

    val timing_tip_pre = Component.translatable("$transPrefix.timing_pre")
        .withStyle(ChatFormatting.GOLD)
        .translate("Taking: ", "耗时: ")

    override fun rightClickOn(
        data: Data.UnitData,
        level: Level,
        pos: BlockPos,
        clickedFace: Direction,
        player: Player,
        item: ItemStack,
    ): Boolean {
        level.ifClient {
            val pos0 = pos.relative(clickedFace.opposite)
            measureTime {
                val portal = PortalConfig.DEFAULT.searcher.find(level, pos0)
                if (portal == null) {
                    CUtils.particleBlock(ParticleTypes.FLAME, it, pos0, 0.25)
                    sendMessage(player, notfound_tip)
                } else {
                    portal.getUnits().forEach { unit ->
                        CUtils.particleBlock(ParticleTypes.FLAME, it, unit.base, unit.end, 0.25)
                        CUtils.particleBlock(ParticleTypes.HAPPY_VILLAGER, it, unit.base, 0.25)
                    }
                    sendMessage(player, found_tip)
                    sendMessage(
                        player,
                        NbtUtils.toPrettyComponent(
                            JsonOps.INSTANCE.convertTo(
                                NbtOps.INSTANCE,
                                GsonHelper.parse(portal.toString())
                            )
                        )
                    )
                }
            }.also { time ->
                sendMessage(player, timing_tip_pre.copy().append(Component.literal(time.toString())))
            }
        }
        return true
    }

    override fun onSave(
        data: Data.UnitData,
        nbt: CompoundTag,
    ) {
    }

    override fun appendTooltip(
        data: Data.UnitData,
        item: ItemStack,
        tooltips: MutableList<Component>,
        isAdvanced: TooltipFlag,
    ) {
        tooltips.add(test_tip)
    }

    override fun onLoad(nbt: CompoundTag): Data.UnitData = Data.UnitData
}