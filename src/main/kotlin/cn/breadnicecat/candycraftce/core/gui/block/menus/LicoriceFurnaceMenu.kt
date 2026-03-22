package cn.breadnicecat.candycraftce.core.gui.block.menus

import cn.breadnicecat.candycraftce.core.block.blockentities.LicoriceFurnaceBE
import cn.breadnicecat.candycraftce.core.gui.block.CBlockMenus
import cn.breadnicecat.candycraftce.core.gui.block.menus.slots.ResultSlot
import cn.breadnicecat.candycraftce.core.gui.block.menus.slots.SugarFuelSlot
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.*
import net.minecraft.world.item.ItemStack

class LicoriceFurnaceMenu(
    type: MenuType<*>,
    containerId: Int,
    inventory: Inventory,
    val container: Container,
    val containerData: ContainerData,
) : AbstractContainerMenu(type, containerId) {

    constructor(i: Int, inventory: Inventory) : this(i, inventory, SimpleContainer(3), SimpleContainerData(4))

    constructor(
        containerId: Int,
        inventory: Inventory,
        container: Container,
        containerData: ContainerData,
    ) : this(CBlockMenus.licorice_furnace_menu, containerId, inventory, container, containerData)

    companion object {
        private const val INV_START = 0
        private const val INV_END = 35
        private const val INPUT_SLOT = 36
        private const val FUEL_SLOT = 37
        private const val OUTPUT_SLOT = 38
    }

    init {
        checkContainerSize(container, 3)
        checkContainerDataCount(containerData, 4)

        //player inv
        for (i in 0..2) {
            for (k in 0..8) {
                this.addSlot(Slot(inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18))
            }
        }
        for (i in 0..8) {
            this.addSlot(Slot(inventory, i, 8 + i * 18, 142))
        }

        addSlot(Slot(container, LicoriceFurnaceBE.INPUT_SLOT, 56, 17))
        addSlot(SugarFuelSlot(container, LicoriceFurnaceBE.FUEL_SLOT, 56, 53))
        addSlot(object : ResultSlot(container, LicoriceFurnaceBE.OUTPUT_SLOT, 116, 35) {
            override fun onTake(player: Player, pStack: ItemStack) {
                if (player is ServerPlayer) {
                    (container as? LicoriceFurnaceBE)?.dropExp(player.position())
                }
                super.onTake(player, pStack)
            }
        })

        this.addDataSlots(containerData)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        val slot: Slot = slots[index]
        val item: ItemStack = slot.item
        if (index in INV_START..INV_END) {
            moveItemStackTo(
                item,
                INPUT_SLOT,
                FUEL_SLOT + 1,
                true
            )
        } else {
            moveItemStackTo(
                item,
                INV_START,
                INV_END + 1,
                false
            )
        }
        return ItemStack.EMPTY
    }

    override fun stillValid(player: Player): Boolean = container.stillValid(player)
}