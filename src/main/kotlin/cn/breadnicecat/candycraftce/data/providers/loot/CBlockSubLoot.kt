package cn.breadnicecat.candycraftce.data.providers.loot

import cn.breadnicecat.candycraftce.CandyCraftCE
import cn.breadnicecat.candycraftce.utils.CUtils.clog
import cn.breadnicecat.candycraftce.utils.CUtils.generator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider
import java.util.*

/**
 * Created by NiceCat on 2026/1/11.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class CBlockSubLoot(output: FabricDataOutput) : FabricBlockLootTableProvider(output) {
    companion object {
        val loots = LinkedList<CBlockSubLoot.() -> Unit>()
    }

    val looted = mutableSetOf<Block>()
    override fun generate() {
        loots.forEach { it() }
        BuiltInRegistries.BLOCK.entrySet()
            .filter { (id, _) -> id.location().namespace == CandyCraftCE.MOD_ID }
            .mapNotNull { (id, block) -> id.takeIf { block !in looted } }
            .forEach {
                clog.warn("Missing LootTable for ${it.location()}")
            }
    }

    override fun add(block: Block, builder: LootTable.Builder) {
        super.add(block, builder)
        looted.add(block)
    }

    fun noDrop(block: Block) {
        add(block, noDrop())
    }

    fun dropWhenSilkTouchElse(block: Block, other: ItemLike, count: NumberProvider = 1.generator()) {
        add(block) {
            this.createSingleItemTableWithSilkTouch(it, other, count)
        }
    }

    fun dropLeave(block: Block, sapling: Block, chances: FloatArray = NORMAL_LEAVES_SAPLING_CHANCES) {
        add(block, createLeavesDrops(block, sapling, *chances))
    }

    fun dropOre(block: Block, drop: ItemLike = block, count: NumberProvider = 1.generator()) {
        add(
            block, createSilkTouchDispatchTable(
                block,
                this.applyExplosionDecay(
                    block, LootItem.lootTableItem(drop)
                        .apply(SetItemCountFunction.setCount(count))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                )
            )
        )
    }

}