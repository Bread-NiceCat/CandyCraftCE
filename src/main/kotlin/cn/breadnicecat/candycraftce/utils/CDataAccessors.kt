package cn.breadnicecat.candycraftce.utils

import net.minecraft.world.inventory.ContainerData

/**
 * 用来更加方便写方块实体的同步
 * 
 * @author [Bread_NiceCat](https://gitee.com/Bread_NiceCat)
 * @date 2023/1/24 20:34
 */

fun accessors(builder: CDataAccessorBuilder.() -> Unit): CDataAccessors {
    val builder = CDataAccessorBuilder()
    builder(builder)
    return builder.build()
}

class CDataAccessorBuilder {
    private val accessors = mutableListOf<Accessor<Int>>()
    operator fun Accessor<Int>.unaryPlus() {
        accessors.add(this)
    }

    fun build(): CDataAccessors {
        return CDataAccessors(*accessors.toTypedArray())
    }
}

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
