package cn.breadnicecat.candycraftce.data.extension

import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureMapping.getBlockTexture
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block

class MappingScope {
    val mappings = HashMap<TextureSlot, ResourceLocation>()

    companion object {
        fun mapping(action: MappingScope.() -> Unit): TextureMapping {
            val scope = MappingScope()
            action(scope)
            return scope.toMapping()
        }
    }

    infix fun TextureSlot.provide(texture: ResourceLocation) {
        mappings[this] = texture
    }

    infix fun String.provide(texture: ResourceLocation) {
        TextureSlot.create(this) provide texture
    }

    infix fun TextureSlot.provide(block: Block): Providing {
        this provide getBlockTexture(block)
        return Providing(this, block)
    }

    infix fun String.provide(block: Block): Providing {
        val slot = TextureSlot.create(this)
        slot provide block
        return Providing(slot, block)
    }


    infix fun Providing.suffix(suffix: String) {
        slot provide getBlockTexture(block, suffix)
    }
    

    fun toMapping(): TextureMapping {
        val map = TextureMapping()
        mappings.forEach(map::put)
        return map
    }

    class Providing(
        internal val slot: TextureSlot,
        internal val block: Block,
    )
}