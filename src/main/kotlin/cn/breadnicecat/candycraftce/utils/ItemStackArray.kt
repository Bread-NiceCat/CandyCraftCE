package cn.breadnicecat.candycraftce.utils

import net.minecraft.core.NonNullList
import net.minecraft.world.ContainerHelper
import net.minecraft.world.item.ItemStack
import kotlin.math.min

/**
 * Created in 2024/2/3
 * Project: candycraftce
 * 
 * @author [Bread_NiceCat](https://github.com/Bread-NiceCat)
 * 
 * 
 */
class ItemStackArray private constructor(arr: Array<ItemStack>) :
    NonNullList<ItemStack>(arr.asList(), ItemStack.EMPTY) {

    constructor(size: Int) : this(Array(size) { ItemStack.EMPTY })
    constructor(size: Int, factory: (Int) -> ItemStack?) : this(Array(size) { factory(it) ?: ItemStack.EMPTY })

    override fun add(element: ItemStack): Boolean {
        throw UnsupportedOperationException("ItemStackArray is size-immutable")
    }

    fun isEmpty(index: Int): Boolean {
        return get(index).isEmpty
    }

    fun extract(index: Int, expectCount: Int): ItemStack {
        return ContainerHelper.removeItem(this, index, expectCount)
    }

    /**
     * 注意：会额外对参数`item`直接修改
     * 
     * @return 未被插入的部分
     */
    fun insert(index: Int, item: ItemStack): ItemStack {
        if (item.isEmpty) { //不能插空
            return item
        }
        val cur = get(index)
        if (cur.isEmpty) { //若空则直接放入
            set(index, item.copy())
            item.count = 0
            return item
        }
        val left: Int = cur.maxStackSize - cur.count //能插的最大数量
        if (left != 0 && ItemStack.isSameItemSameTags(cur, item)) {
            val itemCnt: Int = item.count
            if (left >= itemCnt) { //剩余 >= 外来
                cur.grow(itemCnt)
                item.shrink(itemCnt) //0
            } else {
                cur.grow(left) //max
                item.shrink(left)
            }
        }
        return item
    }

    /**
     * 模拟插入
     * 插入，但是不对列表中的数据进行修改
     * 注意：仍然会对参数`item`进行修改
     * 
     * @return 未被插入的部分
     */
    fun simulateInsert(index: Int, item: ItemStack): ItemStack {
        val cur: ItemStack = get(index)
        if (item.isEmpty) { //不能插空
            return item
        }
        if (cur.isEmpty) { //若空则直接放入
            item.count = 0
            return item
        }
        val left: Int = cur.maxStackSize - cur.count //能插的最大数量
        if (left != 0 && ItemStack.isSameItemSameTags(cur, item)) {
            val itemCnt: Int = item.count
            //剩余 >= 外来
            item.shrink(min(left, itemCnt)) //0
        }
        return item
    }


    /**
     * 尝试从from中转移`min(count,expectedCount)`到to中
     *
     * param: from,to 索引
     * 
     * @return 转移的数量
     */
    fun transfer(from: Int, to: Int, expectedCount: Int): Int {
        val ori: ItemStack = get(from)
        val dest: ItemStack = get(to)
        if (dest.isEmpty) {
            val split: ItemStack = ori.split(expectedCount)
            set(to, split)
            return split.count
        } else if (dest.`is`(ori.item)) {
            val cnt = min(expectedCount, min(ori.count, dest.maxStackSize - dest.count))
            ori.shrink(cnt)
            dest.grow(cnt)
            return cnt
        }
        return 0
    }

    /**
     * 交换
     */
    fun swap(from: Int, to: Int) {
        val fr: ItemStack = get(from)
        val dest: ItemStack = get(to)
        set(from, dest)
        set(to, fr)
    }
}
