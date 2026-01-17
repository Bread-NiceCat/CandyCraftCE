package cn.breadnicecat.candycraftce.core.item.items.debugger

import cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
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
abstract class DebugFunc<D : DebugFunc.Data> {
    abstract val id: String

    abstract val displayName: Component

    protected val transPrefix by lazy { "$DEBUG_TRANS_PREFIX.fun.${id}" }

    /**
     * @return 是否处理,如果返回`false`,还会调用[use]
     **/
    open fun use(
        data: D,
        level: Level,
        player: Player,
        item: ItemStack,
    ): Boolean = false

    /**
     * @return 是否处理,如果返回`false`,还会调用[use]
     **/
    open fun rightClickOn(
        data: D,
        level: Level,
        pos: BlockPos,
        clickedFace: Direction,
        player: Player,
        item: ItemStack,
    ): Boolean = false

    /**
     * @return 是否处理,如果返回`false`,还会调用[use]
     **/
    open fun leftClickOn(
        data: D,
        level: Level,
        pos: BlockPos,
        clickedFace: Direction,
        player: Player,
        item: ItemStack,
    ): Boolean = false

    open fun onInventoryTick(
        data: D,
        level: Level,
        player: Player,
        item: ItemStack,
        isInHand: Boolean,
    ) {
    }


    open fun onActive(data: D) {}
    open fun onInactive(data: D) {}

    open fun appendTooltip(
        data: D,
        item: ItemStack,
        tooltips: MutableList<Component>,
        isAdvanced: TooltipFlag,
    ) {
    }

    abstract fun onSave(data: D, nbt: CompoundTag)

    abstract fun onLoad(nbt: CompoundTag): D

    inline fun DebugFunc<*>.sendMessage(
        player: Player,
        message: String,
        condition: Boolean = true,
        mod: MutableComponent.() -> Unit = {},
    ) =
        sendMessage(player, Component.literal(message).apply(mod), condition)

    fun DebugFunc<*>.sendMessage(player: Player, message: Component, condition: Boolean = true) {
        if (condition) player.sendSystemMessage(displayName.copy().append(": ").append(message))
    }

    companion object {
        const val DEBUG_TRANS_PREFIX = "debugger.$MOD_ID"
    }

    abstract class Data {
        private var dirty: Boolean = false
        open fun setDirty() {
            dirty = true
        }

        open fun isDirty(): Boolean {
            return dirty
        }

        object UnitData : Data() {
            override fun setDirty() {}
            override fun isDirty(): Boolean = false
        }
    }
}