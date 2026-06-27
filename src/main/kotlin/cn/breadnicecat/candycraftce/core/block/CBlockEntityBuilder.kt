package cn.breadnicecat.candycraftce.core.block

import cn.breadnicecat.candycraftce.utils.CLogUtils
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.CUtils.register
import com.mojang.datafixers.types.Type
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier

/**
 * Created in 2024/1/30 23:23
 * Project: candycraftce
 * 
 * @author [Bread_NiceCat](https://github.com/Bread-Nicecat)
 * 
 * 
 */
class CBlockEntityBuilder<B : BlockEntity> private constructor(
    val name: String,
    private val factory: BlockEntitySupplier<B>,
) {
    private lateinit var valid: Array<out Block>
    private var dsl: Type<*>? = null

    @SafeVarargs
    fun setValidBlocks(vararg blocks: Block): CBlockEntityBuilder<B> {
        this.valid = blocks
        return this
    }

    fun setDSL(dsl: Type<*>): CBlockEntityBuilder<B> {
        this.dsl = dsl
        return this
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

    fun save(): BlockEntityType<B> {
        val id = name.modLoc()
        CLogUtils.logRegister("BlockEntity", id)
        return BuiltInRegistries.BLOCK_ENTITY_TYPE.register(
            id,
            BlockEntityType.Builder.of(factory, *valid).build(dsl)
        )

    }

    companion object {
        fun <B : BlockEntity> create(name: String, factory: BlockEntitySupplier<B>): CBlockEntityBuilder<B> {
            return CBlockEntityBuilder(name, factory)
        }
    }
}
