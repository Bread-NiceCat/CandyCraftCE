package cn.breadnicecat.candycraftce.data.extension

import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation

class MappingScope {
    val mappings = HashMap<TextureSlot, ResourceLocation>()
    infix fun TextureSlot.provide(texture: ResourceLocation) {
        mappings[this] = texture
    }

    infix fun String.provide(texture: ResourceLocation) {
        TextureSlot.create(this) provide texture
    }

    fun toMapping(): TextureMapping {
        val map = TextureMapping()
        mappings.forEach(map::put)
        return map
    }
}