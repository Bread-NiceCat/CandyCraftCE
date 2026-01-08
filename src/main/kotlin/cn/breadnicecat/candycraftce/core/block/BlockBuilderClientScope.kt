package cn.breadnicecat.candycraftce.core.block

import cn.breadnicecat.candycraftce.utils.ModUtils.ifClient
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block

/**
 * Created by NiceCat on 2025/12/8.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class BlockBuilderClientScope<B : Block>(val builder: BlockBuilder<B>) {
    companion object {
        inline fun <B : Block> BlockBuilder<B>.client(action: BlockBuilderClientScope<B>.() -> Unit): BlockBuilder<B> {
            ifClient {
                action(BlockBuilderClientScope(this))
            }
            return this
        }
    }

    fun cutout() = renderType(RenderType.cutoutMipped())
    fun translucent() = renderType(RenderType.translucent())
    fun renderType(type: RenderType) {
        builder.record("renderType") {
            lateUsage { (_, block) ->
                BlockRenderLayerMap.INSTANCE.putBlock(block, type)
            }
        }
    }

    fun tint(blockColor: BlockColor) {
        builder.record("tint") {
            lateUsage { (_, block) ->
                ColorProviderRegistry.BLOCK.register(blockColor, block)
            }
        }
    }
}