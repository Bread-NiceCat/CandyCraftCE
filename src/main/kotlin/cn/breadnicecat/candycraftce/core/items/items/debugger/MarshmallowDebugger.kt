package cn.breadnicecat.candycraftce.core.items.items.debugger

import cn.breadnicecat.candycraftce.core.items.items.debugger.DebugFunc.Companion.DEBUG_TRANS_PREFIX
import cn.breadnicecat.candycraftce.core.items.items.debugger.DebugFunc.Data
import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.ModUtils.use
import net.minecraft.ChatFormatting.GREEN
import net.minecraft.ChatFormatting.YELLOW
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import org.apache.logging.log4j.core.util.UuidUtil
import java.util.*

/**
 * Created by NiceCat on 2025/12/11.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class MarshmallowDebugger(properties: Properties) : Item(properties) {
    companion object {
        private val current_func_tip_pre = Component.translatable("$DEBUG_TRANS_PREFIX.current_func")
            .withStyle(GREEN)
            .translate("Current Mode: ", "当前模式: ")
        private val next_func_tip = Component.translatable("$DEBUG_TRANS_PREFIX.next_func_tip")
            .withStyle(YELLOW)
            .translate("Press Shift+Right Click on the Air to switch mode", "对空气 SHIFT+右键 切换模式")

        val funcs = LinkedList<DebugFunc<out Data>>()
            .apply {
                add(Nop)
                add(Measuring)
                add(PortalTest)
            }

        //uuid -> currentData
        private val dataCaches = mutableMapOf<UUID, Pair<DebugFunc<Data>, Data>>()
    }

    private fun getCurrentFunc(stack: ItemStack): DebugFunc<Data> {
        val string = stack.orCreateTag.getString("current")
        val f = funcs.firstOrNull { it.id == string } ?: funcs.first()
        @Suppress("UNCHECKED_CAST")
        return f as DebugFunc<Data>
    }

    private fun setCurrentFunc(stack: ItemStack, func: DebugFunc<Data>) {
        if (getCurrentFunc(stack) != func) {
            stack.orCreateTag.putString("current", func.id)
            val uuid = orCreateUuid(stack)
            val prev = dataCaches[uuid]
            if (prev != null) {
                val (prevFunc, prevData) = prev
                prevFunc.onInactive(prevData)
                saveData(stack, prevFunc, prevData)
            }
            val (_, data) = orLoadData(stack, func, forceLoad = true)
            func.onActive(data)
        }
    }


    /**
     * 保存对应func的数据
     * */
    private fun <T : Data> saveData(
        stack: ItemStack,
        func: DebugFunc<T>,
        data: T,
    ) {
        if (data.isDirty()) {
            stack.orCreateTag.also { root ->
                root.use("data") { dataTag ->
                    dataTag.use(func.id) { funcData ->
                        func.onSave(data, funcData)
                    }
                }
            }
        }
    }

    /**
     * 加载当前的func及其数据
     * */
    private fun orLoadData(
        stack: ItemStack,
        func: DebugFunc<Data> = getCurrentFunc(stack),
        forceLoad: Boolean = false,
    ): Pair<DebugFunc<Data>, Data> {
        val uuid = orCreateUuid(stack)
        val dp = dataCaches[uuid]
        //验证是否对应，防止缓存数据被修改或者nbt被修改
        return if (forceLoad || dp == null || dp.first != func) {
            val root = stack.orCreateTag
            val data = root.getCompound("data")
            val funcData = data.getCompound(func.id)
            (func to func.onLoad(funcData))
                .also { dataCaches[uuid] = it }
        } else return dp
    }

    private fun orCreateUuid(stack: ItemStack): UUID {
        val root = stack.orCreateTag
        val key = "debugger_uuid"
        if (root.hasUUID(key))
            return root.getUUID(key)
        else {
            val u = UuidUtil.getTimeBasedUuid()
            root.putUUID(key, u)
            return u
        }

    }

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        tooltips: MutableList<Component>,
        isAdvanced: TooltipFlag,
    ) {
        val (func, data) = orLoadData(stack)
        tooltips.add(current_func_tip_pre.copy().append(func.displayName))
        tooltips.add(next_func_tip)
        func.appendTooltip(data, stack, tooltips, isAdvanced)
    }


    override fun getName(stack: ItemStack): Component {
        return super.getName(stack).copy()
            .append("(")
            .append(getCurrentFunc(stack).displayName)
            .append(")")
    }


    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack?> {
        val stack = player.getItemInHand(usedHand)

        if (player.isShiftKeyDown) {
            if (!level.isClientSide) {
                val idx = (funcs.indexOf(getCurrentFunc(stack)) + 1) % funcs.size

                @Suppress("UNCHECKED_CAST")
                val func = funcs[idx] as DebugFunc<Data>
                setCurrentFunc(stack, func)
                player.sendSystemMessage(current_func_tip_pre.copy().append(func.displayName))
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide)
        }

        val (f, data) = orLoadData(stack)

        val result = f.use(data, level, player, stack)
        saveData(stack, f, data)
        return if (result) {
            InteractionResultHolder.sidedSuccess(stack, level.isClientSide)
        } else {
            InteractionResultHolder.pass(stack)
        }
    }

    override fun canAttackBlock(state: BlockState, level: Level, pos: BlockPos, player: Player): Boolean {
        val stack = player.mainHandItem

        val view = player.getViewVector(1f)
        val face = Direction.getNearest(view.x, view.y, view.z)

        val (f, data) = orLoadData(stack)

        val result = f.leftClickOn(data, level, pos, face, player, stack)
        if (result) {
            saveData(stack, f, data)
        }
        return false
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player ?: return InteractionResult.PASS
        val level = context.level
        val stack = context.itemInHand

        val view = player.getViewVector(1f)
        val face = Direction.getNearest(view.x, view.y, view.z)

        val (f, data) = orLoadData(stack)

        val result = f.rightClickOn(data, level, context.clickedPos, face, player, stack)
        saveData(stack, f, data)
        return if (result) {
            InteractionResult.sidedSuccess(level.isClientSide)
        } else {
            InteractionResult.PASS
        }
    }

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        if (entity is Player) {
            val (f, data) = orLoadData(stack)
            f.onInventoryTick(data, level, entity, stack, entity.handSlots.any { it == stack })
            saveData(stack, f, data)
        }
    }

    override fun isFoil(stack: ItemStack): Boolean = true
}