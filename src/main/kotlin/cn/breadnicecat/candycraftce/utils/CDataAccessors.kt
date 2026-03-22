package cn.breadnicecat.candycraftce.utils

import net.minecraft.world.inventory.ContainerData

/**
 * 用来更加方便写方块实体的同步
 * 
 * @author [Bread_NiceCat](https://gitee.com/Bread_NiceCat)
 * @date 2023/1/24 20:34
 */
class CDataAccessors @SafeVarargs constructor(vararg val accessors: Accessor<Int>) : ContainerData {

    override fun get(pIndex: Int): Int {
        return accessors[pIndex].get()
    }

    override fun set(pIndex: Int, pValue: Int) {
        accessors[pIndex].set(pValue)
    }

    override fun getCount(): Int {
        return accessors.size
    }
}
