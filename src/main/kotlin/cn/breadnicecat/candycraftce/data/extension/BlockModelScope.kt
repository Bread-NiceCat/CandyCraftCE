package cn.breadnicecat.candycraftce.data.extension

import cn.breadnicecat.candycraftce.core.block.BlockBuilder
import cn.breadnicecat.candycraftce.data.providers.CModelProvider
import com.google.gson.JsonElement
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.BlockModelGenerators.TintState
import net.minecraft.data.models.blockstates.BlockStateGenerator
import net.minecraft.data.models.model.ModelLocationUtils.getModelLocation
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureMapping.getBlockTexture
import net.minecraft.data.models.model.TextureSlot.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Supplier

typealias ModelOutput = BiConsumer<ResourceLocation, Supplier<JsonElement>>
typealias StateOutput = Consumer<BlockStateGenerator>

class BlockModelScope<B : Block> internal constructor(
    private val builder: BlockBuilder<B>,
) {

    fun action(modelAction: BlockModelGenerators.(B) -> Unit) {
        builder.lateUsage { e ->
            CModelProvider.blocks.add { modelAction(this, e.block) }
        }
    }

    fun model(modelGen: (B, ModelOutput) -> ResourceLocation): PreparedModel {
        val model = PreparedModel()
        action {
            model.setModel(modelGen(it, modelOutput))
        }
        return model
    }

    fun state(stateGen: (B, StateOutput) -> Unit) {
        action {
            stateGen(it, blockStateOutput)
        }
    }

    inline fun state(crossinline stateGen: (B) -> BlockStateGenerator) {
        state { block, output ->
            output.accept(stateGen(block))
        }
    }

    fun modelExisted(modelLocation: ResourceLocation? = null) =
        model { b, _ ->
            modelLocation ?: getModelLocation(b)
        }

    fun withParent(
        parent: ResourceLocation,
        parentPrefix: String? = "block",
        outputSuffix: String? = null,
        mapping: MappingScope.(B) -> Unit,
    ): PreparedModel {
        val parentModel = parentPrefix?.let { parent.withPrefix("$it/") } ?: parent
        return model { block, output ->
            val scope = MappingScope().apply { mapping(block) }
            val template = ModelTemplate(
                Optional.of(parentModel),
                Optional.ofNullable(outputSuffix),
                *scope.mappings.keys.toTypedArray()
            )
            template.create(block, scope.toMapping(), output)
        }
    }

    /**
     * 实现[template]
     * */
    fun template(
        template: ModelTemplate,
        suffix: String = "",
        mapping: MappingScope.(B) -> Unit,
    ): PreparedModel {
        return model { block, output ->
            val scope = MappingScope()
            mapping(scope, block)
            template.createWithSuffix(block, suffix, scope.toMapping(), output)
        }
    }

    fun cubeAll() = template(ModelTemplates.CUBE_ALL) {
        ALL provide getBlockTexture(it)
    }

    fun cubeBottomTop() = template(ModelTemplates.CUBE_BOTTOM_TOP) {
        TOP provide getBlockTexture(it, "_top")
        BOTTOM provide getBlockTexture(it, "_bottom")
        SIDE provide getBlockTexture(it, "_side")
    }

    fun cross(tinted: Boolean = false): PreparedModel {
        return model { block, output ->
            val tintState = if (tinted) TintState.TINTED else TintState.NOT_TINTED
            tintState.cross.create(block, TextureMapping.cross(block), output)
        }
    }

    fun PreparedModel.simpleState() {
        state {
            BlockModelGenerators.createSimpleBlock(it, this.getModel())
        }
    }

    inline fun PreparedModel.applyState(crossinline action: (B, ResourceLocation) -> BlockStateGenerator) {
        state { action(it, this.getModel()) }
    }

}