package cn.breadnicecat.candycraftce.core.block


import cn.breadnicecat.candycraftce.core.block.blocks.CustardPuddingBlock
import cn.breadnicecat.candycraftce.core.block.blocks.PuddingFarmBlock
import cn.breadnicecat.candycraftce.core.block.blocks.SugarBlock
import cn.breadnicecat.candycraftce.core.block.blocks.plant.*
import cn.breadnicecat.candycraftce.core.items.CItems
import cn.breadnicecat.candycraftce.core.items.ItemFactory
import cn.breadnicecat.candycraftce.core.items.PropertiesFactory
import cn.breadnicecat.candycraftce.core.tab.CItemTabs.CANDYCRAFT
import cn.breadnicecat.candycraftce.core.tab.CItemTabs.tab
import cn.breadnicecat.candycraftce.core.tag.CTags
import cn.breadnicecat.candycraftce.data.DataUtils.model
import cn.breadnicecat.candycraftce.data.DataUtils.modelBlockDirect
import cn.breadnicecat.candycraftce.data.DataUtils.modelBlockSimple
import cn.breadnicecat.candycraftce.data.DataUtils.modelCross
import cn.breadnicecat.candycraftce.data.DataUtils.modelCubeAll
import cn.breadnicecat.candycraftce.data.DataUtils.tag
import cn.breadnicecat.candycraftce.data.DataUtils.template
import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.Arguments
import cn.breadnicecat.candycraftce.utils.OperationRecordable
import cn.breadnicecat.candycraftce.utils.Utils.modLoc
import cn.breadnicecat.candycraftce.utils.Utils.register
import cn.breadnicecat.candycraftce.utils.V
import cn.breadnicecat.candycraftce.utils.V.Companion.v
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.models.BlockModelGenerators.createEmptyOrFullDispatch
import net.minecraft.data.models.BlockModelGenerators.createRotatedVariants
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.data.models.model.ModelLocationUtils.getModelLocation
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureMapping.getBlockTexture
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour.Properties.of
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import java.util.*
import java.util.function.Consumer

private typealias BlockBuilderOp<I> = CBlocks.BlockBuilder<I>.() -> Unit
private typealias BlockFactory<B> = Arguments.(BlockBehaviour.Properties) -> B
private typealias BehaviourFactory = (BlockBehaviour.Properties) -> Unit

object CBlocks {
    private val simple = BlockBuilder("*simple", { Block(it) })
        .simpleBlockItem {
            it.modelBlockSimple(this.block)
                .tab(CANDYCRAFT)
        }
        .modelCubeAll()


    val sugar_block = simple.copy("sugar_block", factory = { SugarBlock(it) })
        .translate("Sugar Block", "糖块")
        .save()

    val pudding = simple.copy("pudding")
        .translate("Pudding", "布丁")
        .tag(CTags.CBlockTags.candy_plant_suitable)
        .save()
    val custard_pudding = simple.copy("custard_pudding", { CustardPuddingBlock(it) })
        .translate("Custard Pudding", "奶皮布丁")
        .model {
            val overlay = TextureSlot.create("overlay")
            val template = template(
                "grass_block", null,
                TextureSlot.PARTICLE, TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE, overlay
            )
            val tex = TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(pudding.block))
                .put(TextureSlot.BOTTOM, getBlockTexture(pudding.block))
                .put(TextureSlot.TOP, getBlockTexture(it, "_top_overlay"))
                .put(TextureSlot.SIDE, getBlockTexture(it, "_side"))
                .put(overlay, getBlockTexture(it, "_side_overlay"))

            val model = template.create(it, tex, modelOutput)
            val variant = createRotatedVariants(model)
            blockStateOutput.accept(MultiVariantGenerator.multiVariant(it, *variant))
        }
        .tag(CTags.CBlockTags.candy_plant_suitable)
        .save()

    val pudding_farm = simple.copy("pudding_farmland", { PuddingFarmBlock(it) })
        .translate("Pudding Farmland", "布丁耕地")
        .tag(CTags.CBlockTags.candy_plant_suitable)
        .modifyBlockItem {
            it.removeRecord("tab")
        }
        .model {
            val dry = ModelTemplates.FARMLAND.create(
                it,
                TextureMapping()
                    .put(TextureSlot.DIRT, getBlockTexture(pudding.block))
                    .put(TextureSlot.TOP, getBlockTexture(it, "_top")),
                modelOutput
            )
            val moist = ModelTemplates.FARMLAND.create(
                getModelLocation(it, "_moist"),
                TextureMapping()
                    .put(TextureSlot.DIRT, getBlockTexture(pudding.block))
                    .put(TextureSlot.TOP, getBlockTexture(it, "_top_moist")),
                modelOutput
            )
            blockStateOutput
                .accept(
                    MultiVariantGenerator.multiVariant(it).with(
                        createEmptyOrFullDispatch(
                            BlockStateProperties.MOISTURE,
                            PuddingFarmBlock.MAX_MOISTURE,
                            moist,
                            dry
                        )
                    )
                )
        }
        .save()

    val sweet_grass_0 = simple.copy("sweet_grass_0", factory = { SweetGrassBlock(it) })
        .translate("Sweet Grass", "甜草")
        .modelCross()
        .modifyBlockItem {
            it.modelBlockDirect(block)
        }
        .save()
    val sweet_grass_1 = sweet_grass_0.copy("sweet_grass_1")
        .save()
    val sweet_grass_2 = sweet_grass_0.copy("sweet_grass_2")
        .save()
    val sweet_grass_3 = sweet_grass_0.copy("sweet_grass_3")
        .save()

    val mint = simple.copy("mint", { CandyWaterPlantBlock(it) })
        .modelCross()
        .modifyBlockItem {
            it.modelBlockDirect(block)
        }
        .translate("Mint", "水生薄荷")
        .save()
    val rope_raspberry = mint.copy("rope_raspberry")
        .translate("Rope Raspberry", "绳状树莓")
        .save()
    val banana_seaweed = mint.copy("banana_seaweed")
        .translate("Banana Seaweed", "香蕉海草")
        .save()

    val fraise_tagada_flower = simple.copy("fraise_tagada_flower", { CandyPlantBlock(it) })
        .translate("Fraise Tagada Flower", "果蜜花")
        .modifyBlockItem {
            it.modelBlockDirect(block)
        }
        .modelCross()
        .save()
    val golden_sugar_flower = fraise_tagada_flower.copy("golden_sugar_flower", { GoldenSugarFlowerBlock(it) })
        .translate("Golden Sugar Flower", "金果蜜花")
        .save()
    val acid_mint_flower = fraise_tagada_flower.copy("acid_mint_flower", { AcidMintFlower(it) })
        .translate("Acid Mint Flower", "酸薄荷花")
        .save()


    class BlockBuilder<B : Block>(
        val id: String,
        val factory: BlockFactory<B>,
        internal var propBuilder: BehaviourFactory = {},
    ) : OperationRecordable<BlockBuilder<B>>() {

        private val lateUsage = LinkedList<Consumer<Entry<B>>>()
        private val arguments = Arguments.Builder()
        private var blockItem: CItems.Entry<out BlockItem>? = null
        private var blockItemMod: (Entry<B>.(CItems.ItemBuilder<out BlockItem>) -> Unit) = {}
        fun modifyBlockItem(action: Entry<B>.(CItems.ItemBuilder<out BlockItem>) -> Unit = {}): BlockBuilder<B> {
            record("modifyBlockItem", overridable = false) {
                val old = blockItemMod
                blockItemMod = { old(it); action(it); }
            }
            return this
        }

        fun <I : BlockItem> blockItem(
            factory: (Block) -> ItemFactory<I>,
            properties: PropertiesFactory = {},
            action: Entry<B>.(CItems.ItemBuilder<I>) -> Unit = {},
        ): BlockBuilder<B> {
            record("blockItem") {
                lateUsage {
                    val fn = factory(it.block)
                    val builder = CItems.ItemBuilder(id, fn, properties)
                    action(it, builder)
                    blockItemMod(it, builder)
                    blockItem = builder.save()
                }
            }
            return this
        }

        fun simpleBlockItem(
            properties: PropertiesFactory = {},
            action: Entry<B>.(CItems.ItemBuilder<BlockItem>) -> Unit = {},
        ) = blockItem(
            factory = { block -> { BlockItem(block, it) } },
            properties = properties,
            action = action
        )


        /*==============================
                    Core Zone
         ==============================*/

        fun modifyProperties(action: BehaviourFactory): BlockBuilder<B> {
            record("modifyProperties", overridable = false) {
                val prop = propBuilder
                propBuilder = { prop(it); action(it) }
            }
            return this
        }

        /**
         * 在注册后调用
         * */
        fun lateUsage(action: (Entry<B>) -> Unit) {
            check(!frozen)
            lateUsage.add(action)
        }

        fun argument(key: String, value: String?): BlockBuilder<B> {
            if (value == null) arguments.remove(key)
            else arguments[key] = value
            return this
        }

        fun arguments(vararg args: Pair<String, String?>): BlockBuilder<B> {
            args.forEach { (key, value) ->
                argument(key, value)
            }
            return this
        }

        fun save(): Entry<B> {
            executeRecords()
            val block = register(id.modLoc()) {
                factory(arguments.build(), of().apply(propBuilder))
            }
            val entry = Entry(id.modLoc(), block, blockItem, this.v())
            lateUsage.forEach { it.accept(entry) }
            return entry
        }

        fun copy(
            id: String,
            factory: BlockFactory<B> = this.factory,
            properties: BehaviourFactory = this.propBuilder,
        ): BlockBuilder<B> {
            val new = BlockBuilder(id, factory, properties)
            new.copyRecord(this)
            return new
        }

        override val receiver: BlockBuilder<B>
            get() = this

        companion object {
            @Suppress("UNCHECKED_CAST")
            private fun <B : Block> register(
                id: ResourceLocation,
                factory: () -> B,
            ): B {
                return BuiltInRegistries.BLOCK.register(id, factory()) as B
            }
        }
    }

    class Entry<B : Block>(
        val id: ResourceLocation,
        val block: B,
        val item: CItems.Entry<out BlockItem>?,
        private val builder: V<BlockBuilder<B>>,
    ) {
        operator fun component1() = id
        operator fun component2() = block
        fun copy(
            id: String,
            factory: BlockFactory<B> = builder.get().factory,
            properties: BehaviourFactory = builder.get().propBuilder,
        ) = builder.get().copy(id, factory, properties)

        fun defaultBlockState(): BlockState = block.defaultBlockState()
    }

}

