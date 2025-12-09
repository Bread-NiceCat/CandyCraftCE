package cn.breadnicecat.candycraftce.core.block


import cn.breadnicecat.candycraftce.client.PuddingColor
import cn.breadnicecat.candycraftce.core.block.BlockBuilderClientScope.Companion.client
import cn.breadnicecat.candycraftce.core.block.blocks.CustardPuddingBlock
import cn.breadnicecat.candycraftce.core.block.blocks.PuddingFarmBlock
import cn.breadnicecat.candycraftce.core.block.blocks.SugarBlock
import cn.breadnicecat.candycraftce.core.block.blocks.plant.*
import cn.breadnicecat.candycraftce.core.items.ItemBuilderClientScope.Companion.client
import cn.breadnicecat.candycraftce.core.tab.CItemTabs.CANDYCRAFT
import cn.breadnicecat.candycraftce.core.tab.CItemTabs.tab
import cn.breadnicecat.candycraftce.core.tag.CTags
import cn.breadnicecat.candycraftce.data.DataUtils.template
import cn.breadnicecat.candycraftce.data.extension.BlockBuilderDataScope.Companion.data
import cn.breadnicecat.candycraftce.data.extension.ItemBuilderDataScope.Companion.data
import cn.breadnicecat.candycraftce.utils.Utils
import net.minecraft.client.renderer.RenderType
import net.minecraft.data.models.BlockModelGenerators.createEmptyOrFullDispatch
import net.minecraft.data.models.BlockModelGenerators.createRotatedVariants
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.data.models.model.ModelLocationUtils.getModelLocation
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureMapping.getBlockTexture
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.properties.BlockStateProperties


object CBlocks {
    init {
        Utils.sign()
    }

    private val simple = BlockBuilder("*simple", { Block(it) })
        .simpleBlockItem {
            it.data {
                modelBlockSimple(this@simpleBlockItem.block)
            }.tab(CANDYCRAFT)
        }
        .data {
            modelCubeAll()
        }


    val sugar_block = simple.copy("sugar_block", factory = { SugarBlock(it) })
        .data {
            translate("Sugar Block", "糖块")
        }
        .copyProperties(Blocks.SAND)
        .modifyProperties { it.randomTicks() }
        .save()

    val pudding = simple.copy("pudding")
        .data {
            translate("Pudding", "布丁")
            tag(CTags.CBlockTags.candy_plant_suitable)
        }
        .save()
    val custard_pudding = simple.copy("custard_pudding", { CustardPuddingBlock(it) })
        .data {
            translate("Custard Pudding", "奶皮布丁")
            tag(CTags.CBlockTags.candy_plant_suitable)
            model {
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
        }
        .client {
            renderType(RenderType.cutoutMipped())
            tint { _, getter, pos, _ ->
                val biome = getter?.getBiomeFabric(pos)
                if (biome != null) PuddingColor.getColor(biome) else PuddingColor.getDefaultPuddingColor()
            }
        }
        .modifyBlockItem {
            it.client {
                tint { _, _ -> PuddingColor.getDefaultPuddingColor() }
            }
        }
        .modifyProperties { it.randomTicks() }
        .save()

    val pudding_farm = simple.copy("pudding_farmland", { PuddingFarmBlock(it) })
        .data {
            translate("Pudding Farmland", "布丁耕地")
            tag(CTags.CBlockTags.candy_plant_suitable)
            model {
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
        }
        .modifyProperties { it.randomTicks() }
        .modifyBlockItem {
            it.removeRecord("tab")
        }
        .save()

    val sweet_grass_0 = simple.copy("sweet_grass_0", factory = { SweetGrassBlock(it) })
        .data {
            translate("Sweet Grass", "甜草")
            modelCross()
        }
        .copyProperties(Blocks.POPPY)
        .modifyBlockItem {
            it.data { modelBlockDirect(block) }
        }
        .save()
    val sweet_grass_1 = sweet_grass_0.copy("sweet_grass_1")
        .save()
    val sweet_grass_2 = sweet_grass_0.copy("sweet_grass_2")
        .save()
    val sweet_grass_3 = sweet_grass_0.copy("sweet_grass_3")
        .save()

    val mint = simple.copy("mint", { CandyWaterPlantBlock(it) })
        .data {
            translate("Mint", "水生薄荷")
            modelCross()
        }
        .copyProperties(Blocks.KELP_PLANT)
        .modifyBlockItem {
            it.data { modelBlockDirect(block) }
        }
        .save()
    val rope_raspberry = mint.copy("rope_raspberry")
        .data {
            translate("Rope Raspberry", "绳状树莓")
        }
        .save()
    val banana_seaweed = mint.copy("banana_seaweed")
        .data {
            translate("Banana Seaweed", "香蕉海草")
        }
        .save()

    val fraise_tagada_flower = mint.copy("fraise_tagada_flower", { CandyPlantBlock(it) })
        .data {
            translate("Fraise Tagada Flower", "果蜜花")
        }
        .save()
    val golden_sugar_flower = fraise_tagada_flower.copy("golden_sugar_flower", { GoldenSugarFlowerBlock(it) })
        .data {
            translate("Golden Sugar Flower", "金果蜜花")
        }
        .save()
    val acid_mint_flower = fraise_tagada_flower.copy("acid_mint_flower", { AcidMintFlower(it) })
        .data {
            translate("Acid Mint Flower", "酸薄荷花")
        }
        .save()

}

