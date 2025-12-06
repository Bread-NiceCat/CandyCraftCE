package cn.breadnicecat.candycraftce.data.providers

import cn.breadnicecat.candycraftce.core.tag.TagKeys
import cn.breadnicecat.candycraftce.data.DataUtils.checkDataRunning
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.tags.TagsProvider
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import java.util.*
import java.util.concurrent.CompletableFuture


typealias TagAppenderOp<I> = FabricTagProvider<I>.FabricTagBuilder.(HolderLookup.Provider) -> Unit

class CTagProviders(val output: FabricDataOutput, val lookup: CompletableFuture<HolderLookup.Provider>) : DataProvider {
    companion object {
        init {
            checkDataRunning()
        }

        private val items = HashMap<TagKey<Item>, LinkedList<TagAppenderOp<Item>>>()
        private val blocks = HashMap<TagKey<Block>, LinkedList<TagAppenderOp<Block>>>()
        private val copies = HashMap<TagKey<Item>, TagKey<Block>>()
        fun putItemOp(tag: TagKey<Item>, op: TagAppenderOp<Item>) {
            items.computeIfAbsent(tag) { LinkedList() }.add(op)
        }

        fun putBlockOp(tag: TagKey<Block>, op: TagAppenderOp<Block>) {
            blocks.computeIfAbsent(tag) { LinkedList() }.add(op)
        }

        fun putCopies(tag: TagKeys) {
            copies[tag.first] = tag.second
        }
    }

    private val subs: Set<TagsProvider<*>>

    init {
        val bt = BlockTag()
        val it = ItemTag(bt)
        subs = mutableSetOf(it, bt)
    }

    override fun run(output: CachedOutput): CompletableFuture<*> {
        return CompletableFuture.allOf(*subs.map { it.run(output) }.toTypedArray())
    }

    override fun getName(): String = "CTagProviders"

    private inner class ItemTag(block: FabricTagProvider.BlockTagProvider) :
        ItemTagProvider(output, lookup, block) {
        override fun addTags(arg: HolderLookup.Provider) {
            copies.forEach { (itemTag, blockTag) -> copy(blockTag, itemTag) }
            items.forEach { (tag, v) ->
                val builder = getOrCreateTagBuilder(tag)
                v.forEach { it(builder, arg) }
            }
        }
    }

    private inner class BlockTag : BlockTagProvider(output, lookup) {
        override fun addTags(arg: HolderLookup.Provider) {
            blocks.forEach { (tag, v) ->
                val builder = getOrCreateTagBuilder(tag)
                v.forEach { it(builder, arg) }
            }
        }
    }
}