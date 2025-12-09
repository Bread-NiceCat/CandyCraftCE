package cn.breadnicecat.candycraftce.data.extension

import cn.breadnicecat.candycraftce.core.block.BlockBuilder
import cn.breadnicecat.candycraftce.data.DataUtils.abstractTranslate
import cn.breadnicecat.candycraftce.data.DataUtils.ifDatagen
import cn.breadnicecat.candycraftce.data.providers.CModelProvider
import cn.breadnicecat.candycraftce.data.providers.CTagProviders
import cn.breadnicecat.candycraftce.mixin.data.AccessorModelTemplate
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.BlockModelGenerators.createSimpleBlock
import net.minecraft.data.models.model.ModelLocationUtils.getModelLocation
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureMapping.getBlockTexture
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

/**
 * Created by NiceCat on 2025/12/8.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class BlockBuilderDataScope<B : Block>(
    private val builder: BlockBuilder<B>,
) {
    companion object {
        inline fun <B : Block> BlockBuilder<B>.data(
            action: BlockBuilderDataScope<B>.() -> Unit,
        ): BlockBuilder<B> {
            ifDatagen {
                action(BlockBuilderDataScope(this))
            }
            return this
        }
    }


    fun tag(vararg tag: TagKey<Block>) {
        ifDatagen {
            builder.record("tag") {
                lateUsage { (id, _) ->
                    tag.forEach { key ->
                        CTagProviders.putBlockOp(key) { add(id) }
                    }
                }
            }

        }
    }

    fun translate(en: String, zh: String? = null) {
        ifDatagen {
            builder.record("translate", private = true) {
                lateUsage { (_, b) ->
                    abstractTranslate(TranslationBuilder::add, b, en, zh)
                }
            }
        }
    }

    fun model(modelAction: BlockModelGenerators.(B) -> Unit) {
        ifDatagen {
            builder.record("model") {
                lateUsage { e ->
                    CModelProvider.blocks.add { modelAction(this, e.block) }
                }
            }
        }
    }

    fun modelExisted(modelLocation: ResourceLocation? = null) {
        ifDatagen {
            model {
                blockStateOutput.accept(
                    createSimpleBlock(
                        it,
                        modelLocation ?: getModelLocation(it)
                    )
                )
            }
        }
    }

    fun modelSimple(
        template: ModelTemplate,
        mapping: TextureMapping.(B) -> Unit = {},
    ) {
        ifDatagen {
            model {
                val tex = TextureMapping()
                mapping(tex, it)
                val model = template.create(it, tex, this.modelOutput)
                blockStateOutput.accept(
                    createSimpleBlock(
                        it, model
                    )
                )
            }
        }
    }

    fun modelSimply(
        template: ModelTemplate,
        withSuffix: Boolean = true,
    ) {
        ifDatagen {
            modelSimple(template) {
                val requiredSlots = (template as AccessorModelTemplate).requiredSlots
                requiredSlots.forEach { slot ->
                    put(
                        slot,
                        if (withSuffix) getBlockTexture(it, "_" + slot.id)
                        else getBlockTexture(it)
                    )
                }
            }
        }
    }

    fun modelCubeAll() {
        ifDatagen {
            modelSimple(ModelTemplates.CUBE_ALL) {
                put(TextureSlot.ALL, getBlockTexture(it))
            }
        }
    }

    fun modelCross(tinted: Boolean = false) {
        ifDatagen {
            model {
                createCrossBlock(
                    it,
                    if (tinted) BlockModelGenerators.TintState.TINTED else BlockModelGenerators.TintState.NOT_TINTED
                )
            }
        }
    }

}