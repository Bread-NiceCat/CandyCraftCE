package cn.breadnicecat.candycraftce.data.extension

import cn.breadnicecat.candycraftce.core.block.BlockBuilder
import cn.breadnicecat.candycraftce.data.providers.CModelProvider
import cn.breadnicecat.candycraftce.utils.Arguments
import cn.breadnicecat.candycraftce.utils.CLogUtils.clog
import cn.breadnicecat.candycraftce.utils.MixinExtensions.accessor
import com.google.gson.JsonElement
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.BlockModelGenerators.TintState
import net.minecraft.data.models.blockstates.BlockStateGenerator
import net.minecraft.data.models.model.ModelLocationUtils.getModelLocation
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
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
    companion object {
        val families = mutableMapOf<Block, BlockModelGenerators.BlockFamilyProvider>()
    }

    val arguments: Arguments get() = builder.arguments
    fun action(modelAction: BlockModelGenerators.(B) -> Unit) {
        builder.lateUsage { e ->
            val block = e.block
            CModelProvider.blocks.add { modelAction(this, block) }
        }
    }

    fun gen(modelGen: (B, ModelOutput) -> ResourceLocation): PreparedModel {
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
        gen { b, _ ->
            modelLocation ?: getModelLocation(b)
        }

    fun withParent(
        parent: ResourceLocation,
        parentPrefix: String? = "block",
        outputSuffix: String? = null,
        mapping: MappingScope.(B) -> Unit,
    ): PreparedModel {
        val parentModel = parentPrefix?.let { parent.withPrefix("$it/") } ?: parent
        return gen { block, output ->
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
        return gen { block, output ->
            val scope = MappingScope()
            mapping(scope, block)
            template.createWithSuffix(block, suffix, scope.toMapping(), output)
        }
    }

    fun cubeAll() = template(ModelTemplates.CUBE_ALL) {
        ALL provide it
    }

    fun cubeBottomTop() = template(ModelTemplates.CUBE_BOTTOM_TOP) {
        TOP provide it suffix "_top"
        BOTTOM provide it suffix "_bottom"
        SIDE provide it suffix "_side"
    }

    fun cubeColumn() = template(ModelTemplates.CUBE_COLUMN) {
        SIDE provide it suffix "_side"
        END provide it suffix "_end"
    }

    fun family(
        fullParent: Block,
        mapping: TextureMapping? = null,
        familyAction: BlockModelGenerators.BlockFamilyProvider.(B) -> Unit,
    ) {
        action { block ->
            val provider = families.computeIfAbsent(fullParent) {
                val map = if (mapping == null) {
                    clog.warn("Missing family of `$fullParent` and mapping is null, default cube mapping will be used")
                    TextureMapping.cube(it)
                } else {
                    mapping
                }
                BlockFamilyProvider(map).apply {
                    this.accessor().setFullBlock(getModelLocation(fullParent))
                }
            }
            familyAction(provider, block)
        }
    }

    fun cross(tinted: Boolean = false): PreparedModel {
        return gen { block, output ->
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