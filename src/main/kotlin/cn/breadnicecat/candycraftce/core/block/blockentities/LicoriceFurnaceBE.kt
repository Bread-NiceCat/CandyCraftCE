package cn.breadnicecat.candycraftce.core.block.blockentities

import cn.breadnicecat.candycraftce.core.block.CBlockEntities
import cn.breadnicecat.candycraftce.core.block.blocks.LicoriceFurnaceBlock.Companion.LIT
import cn.breadnicecat.candycraftce.core.gui.block.menus.LicoriceFurnaceMenu
import cn.breadnicecat.candycraftce.core.misc.CSugarFuels
import cn.breadnicecat.candycraftce.core.recipe.CRecipeTypes
import cn.breadnicecat.candycraftce.core.recipe.recipes.SugarFurnaceRecipe
import cn.breadnicecat.candycraftce.utils.Accessor.Companion.access
import cn.breadnicecat.candycraftce.utils.CDataAccessors
import cn.breadnicecat.candycraftce.utils.ItemStackArray
import cn.breadnicecat.candycraftce.utils.MCTimeUnit.Companion.second
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.entity.ExperienceOrb
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeManager

import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import kotlin.math.max

//level不应该为null
class LicoriceFurnaceBE(
    type: BlockEntityType<*>,
    pos: BlockPos,
    blockState: BlockState,
    private var tickedTotal: Int,
) : BlockEntity(type, pos, blockState), MenuProvider, WorldlyContainer {
    companion object {
        const val INPUT_SLOT: Int = 0
        const val FUEL_SLOT: Int = 1
        const val OUTPUT_SLOT: Int = 2

        const val TICKED_DATA: Int = 0
        const val TICKED_TOTAL_DATA: Int = 1
        const val LIT_TIME_DATA: Int = 2
        const val LIT_TIME_TOTAL_DATA: Int = 3

        val SLOT_FOR_UP: IntArray = intArrayOf(INPUT_SLOT)
        val SLOT_FOR_DOWN: IntArray = intArrayOf(OUTPUT_SLOT)
        val SLOT_FOR_SIDE: IntArray = intArrayOf(FUEL_SLOT)
    }

    private val items = ItemStackArray(3)
    private var exp = 0f
    private var litTime = 0
    private var litTimeTotal = 0
    private var ticked = 0

    private var recipeUsed: SugarFurnaceRecipe? = null
    private val quickCheck = RecipeManager.createCheck(CRecipeTypes.sugar_furnace_type.first)

    val data: CDataAccessors = CDataAccessors(
        access({ this.ticked }, { v -> this.ticked = v }),
        access({ this.tickedTotal }, { v -> this.tickedTotal = v }),
        access({ this.litTime }, { v -> this.litTime = v }),
        access({ this.litTimeTotal }, { v -> this.litTimeTotal = v })
    )

    constructor(blockPos: BlockPos, blockState: BlockState) : this(
        CBlockEntities.licorice_furnace_be,
        blockPos,
        blockState,
        10.second.toTick
    )

    fun serverTick() {
        var legal = tickBaseLegality()
        legal = legal and tickFuel(legal)
        tickProgress(legal)
    }

    private fun tickBaseLegality(): Boolean {
        if (items.isEmpty(INPUT_SLOT)) return false

        if (recipeUsed == null || !recipeUsed!!.matches(this, level!!)) {
            val recipe = quickCheck.getRecipeFor(this, level!!)
            if (recipe.isEmpty) {
                return false
            } else {
                recipeUsed = recipe.get()
            }
        }
        return items.simulateInsert(OUTPUT_SLOT, recipeUsed!!.getResultItem(level!!.registryAccess())).isEmpty
    }

    private fun tickFuel(ignite: Boolean): Boolean {
        litTime = max(0, litTime - 1)
        if (litTime == 0) {
            if (ignite && !items.isEmpty(FUEL_SLOT)) {
                if (CSugarFuels.isFuel(items[FUEL_SLOT])) {
                    val nf: Int = CSugarFuels.getBurnDuration(items.extract(FUEL_SLOT, 1))
                    if (nf != 0) {
                        litTime = nf
                        litTimeTotal = litTime
                    }
                }
            }
        }
        val lit = litTime > 0
        if (blockState.getValue(LIT) != lit) {
            blockState.setValue(LIT, lit)
            setChanged()
        }
        return lit
    }

    private fun tickProgress(promote: Boolean) {
        if (recipeUsed != null && promote) {
            if (++ticked >= tickedTotal) {
                ticked = 0
                items.extract(INPUT_SLOT, 1)
                items.insert(OUTPUT_SLOT, recipeUsed!!.assemble(this, level!!.registryAccess()))
            }
        } else ticked = max(0, ticked - 2)
    }

    override fun getDisplayName(): Component = blockState.block.name

    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return LicoriceFurnaceMenu(i, inventory, this, data)
    }

    fun dropExp(pos: Vec3) {
        if (exp > 0f) {
            val orb = ExperienceOrb(level!!, pos.x, pos.y, pos.z, Mth.floor(exp))
            this.exp = 0f
            setChanged()
            level!!.addFreshEntity(orb)
        }
    }


    override fun saveAdditional(tag: CompoundTag) {
        ContainerHelper.saveAllItems(tag, this.items)
        tag.putFloat("exp", exp)
        tag.putInt("litTime", litTime)
        tag.putInt("litTimeTotal", litTimeTotal)
        tag.putInt("ticked", ticked)
        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        ContainerHelper.loadAllItems(tag, this.items)
        exp = tag.getFloat("exp")
        litTime = tag.getInt("litTime")
        litTimeTotal = tag.getInt("litTimeTotal")
        ticked = tag.getInt("ticked")
        super.load(tag)
    }

    override fun getSlotsForFace(side: Direction): IntArray {
        return when (side) {
            Direction.UP -> SLOT_FOR_UP
            Direction.DOWN -> SLOT_FOR_DOWN
            else -> SLOT_FOR_SIDE
        }
    }

    override fun canPlaceItemThroughFace(index: Int, itemStack: ItemStack, direction: Direction?): Boolean {
        return direction != null && direction != Direction.DOWN
    }

    override fun canTakeItemThroughFace(index: Int, stack: ItemStack, direction: Direction): Boolean {
        return direction == Direction.DOWN
    }

    override fun getContainerSize(): Int = items.size

    override fun isEmpty(): Boolean = items.isEmpty()

    override fun getItem(slot: Int): ItemStack {
        return items[slot]
    }

    override fun removeItem(slot: Int, amount: Int): ItemStack {
        return ContainerHelper.removeItem(items, slot, amount)
    }

    override fun removeItemNoUpdate(slot: Int): ItemStack {
        return ContainerHelper.takeItem(items, slot)
    }

    override fun setItem(slot: Int, stack: ItemStack) {
        items[slot] = stack
    }

    override fun stillValid(player: Player): Boolean {
        return Container.stillValidBlockEntity(this, player)
    }

    override fun clearContent() {
        items.clear()
    }

}
