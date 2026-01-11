package cn.breadnicecat.candycraftce.core.block


import cn.breadnicecat.candycraftce.CandyCraftCE.clog
import cn.breadnicecat.candycraftce.client.PuddingColor
import cn.breadnicecat.candycraftce.core.block.BlockBuilderClientScope.Companion.client
import cn.breadnicecat.candycraftce.core.block.blocks.CaramelLeavesBlock
import cn.breadnicecat.candycraftce.core.block.blocks.CustardPuddingBlock
import cn.breadnicecat.candycraftce.core.block.blocks.PuddingFarmBlock
import cn.breadnicecat.candycraftce.core.block.blocks.SugarBlock
import cn.breadnicecat.candycraftce.core.block.blocks.plant.*
import cn.breadnicecat.candycraftce.core.items.ItemBuilderClientScope.Companion.client
import cn.breadnicecat.candycraftce.core.tab.CItemTabs.CANDYCRAFT
import cn.breadnicecat.candycraftce.core.tab.CItemTabs.tab
import cn.breadnicecat.candycraftce.core.tag.CTags
import cn.breadnicecat.candycraftce.data.extension.BlockBuilderDataScope.Companion.data
import cn.breadnicecat.candycraftce.data.extension.ItemBuilderDataScope.Companion.data
import cn.breadnicecat.candycraftce.mixin.core.AccessorAxeItem
import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.mcLoc
import cn.breadnicecat.candycraftce.utils.V.Companion.v
import net.minecraft.client.renderer.RenderType
import net.minecraft.data.models.BlockModelGenerators.createEmptyOrFullDispatch
import net.minecraft.data.models.BlockModelGenerators.createRotatedVariants
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping.getBlockTexture
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.MapColor


object CBlocks {
    init {
        CUtils.sign()
    }

    private val mixed_bricks_raw by mutableListOf<BlockBuilder.BlockEntry<*>>().v()

    /**
     * 提供最基本的方块设置：
     * 简单mod物品
     * 简单cubeall模型
     * */
    private val simple = BlockBuilder("*simple", { Block(it) })
        .simpleBlockItem {
            it.data {
                modelBlockSimple(this@simpleBlockItem.block)
            }.tab(CANDYCRAFT)
        }
        .data {
            model {
                cubeAll().simpleState()
            }
        }

    //========
    //  植物
    //========

    private val crossPlant = simple.copy("*crossPlant")
        .data {
            model { cross().simpleState() }
        }
        .client { cutout() }
        .copyProperties(Blocks.POPPY)
        .modifyBlockItem {
            it.data { modelBlockDirect(block) }
        }

    val fraise_tagada_flower = crossPlant.sub("fraise_tagada_flower", { CandyPlantBlock(it) })
        .data { translate("Fraise Tagada Flower", "果蜜花") }
        .mapColor(MapColor.COLOR_PINK)
        .save()
    val golden_sugar_flower = fraise_tagada_flower.sub("golden_sugar_flower", { GoldenSugarFlowerBlock(it) })
        .data { translate("Golden Sugar Flower", "金果蜜花") }
        .mapColor(MapColor.GOLD)
        .save()
    val acid_mint_flower = fraise_tagada_flower.sub("acid_mint_flower", { AcidMintFlower(it) })
        .data { translate("Acid Mint Flower", "酸薄荷花") }
        .mapColor(MapColor.PLANT)
        .save()

    //水生
    val mint = crossPlant.sub("mint", { CandyWaterPlantBlock(it) })
        .data { translate("Mint", "水生薄荷") }
        .copyProperties(Blocks.KELP_PLANT)
        .mapColor(MapColor.PLANT)
        .save()
    val rope_raspberry = mint.copy("rope_raspberry")
        .data { translate("Rope Raspberry", "绳状树莓") }
        .mapColor(MapColor.COLOR_RED)
        .save()
    val banana_seaweed = mint.copy("banana_seaweed")
        .data { translate("Banana Seaweed", "香蕉海草") }
        .mapColor(MapColor.GOLD)
        .save()

    val sweet_grass_0 = fraise_tagada_flower.copy("sweet_grass_0")
        .data {
            translate("Sweet Grass", "甜草")
        }
        .mapColor(MapColor.COLOR_PINK)
        .save()
    val sweet_grass_1 = sweet_grass_0.copy("sweet_grass_1")
        .save()
    val sweet_grass_2 = sweet_grass_0.copy("sweet_grass_2")
        .save()
    val sweet_grass_3 = sweet_grass_0.copy("sweet_grass_3")
        .save()

    val mint_block = simple.copy("mint_block")
        .data { translate("Mint Block", "水生薄荷块") }
        .copyProperties(Blocks.HAY_BLOCK)
        .save()
    val raspberry_block = mint_block.copy("raspberry_block")
        .data { translate("Raspberry Block", "水生树莓块") }
        .save()
    val banana_seaweeds_block = mint_block.copy("banana_seaweeds_block")
        .data { translate("Banana Seaweeds Block", "香蕉海草块") }
        .save()
    val cotton_candy_block = mint_block.copy("cotton_candy_block")
        .data { translate("Cotton Candy Block", "棉花糖块") }
        .save()
    val candied_cherry_sack = mint_block.copy("candied_cherry_sack")
        .data {
            translate("Candied Cherry Sack", "蜜饯樱桃袋")
            model {
                cubeBottomTop().simpleState()
            }
        }
        .save()

    val chewing_gum_block = simple.copy("chewing_gum_block")
        .copyProperties(Blocks.SLIME_BLOCK)
        .modifyProperties {
            it.destroyTime(0.6F)
        }
        .save()


    // 树叶
    private val leaves = simple.sub("*leaves", { LeavesBlock(it) })
        .data {
            tag2(CTags.candy_leaves)
            tag2(ItemTags.LEAVES, BlockTags.LEAVES)
        }
        .copyProperties(Blocks.OAK_LEAVES)
        .client { cutout() }

    val chocolate_leaves = leaves.copy("chocolate_leaves")
        .data { translate("Chocolate Leaves", "巧克力树叶") }
        .mapColor(MapColor.DIRT)
        .save()
    val white_chocolate_leaves = leaves.copy("white_chocolate_leaves")
        .data { translate("White_chocolate Leaves", "白巧克力树叶") }
        .save()
    val caramel_leaves = leaves.sub("caramel_leaves", { CaramelLeavesBlock(it) })
        .data { translate("Caramel Leaves", "焦糖树叶") }
        .save()
    val candied_cherry_leaves = leaves.copy("candied_cherry_leaves")
        .data { translate("Candied_cherry Leaves", "蜜饯樱桃树叶") }
        .save()
    val magical_leaves = leaves.copy("magical_leaves")
        .data { translate("Magical Leaves", "魔法树叶") }
        .client {
            tint { _, level, pos, _ ->
                if (level != null && pos != null && level.hasBiomes()) {
                    return@tint PuddingColor.getColor(level.getBiomeFabric(pos), pos)
                }
                return@tint PuddingColor.getDefaultEnchantColor()
            }
        }
        .save()

    //树苗
    private val sapling = crossPlant.sub("*sapling", { CandySaplingBlock(get("grower"), it) })
        .copyProperties(Blocks.OAK_SAPLING)

    val chocolate_sapling = sapling.copy("chocolate_sapling")
        .data { translate("Chocolate Sapling", "巧克力树苗") }
        .argument("grower", CGrowers.chocolate_grower)
        .save()
    val white_chocolate_sapling = sapling.copy("white_chocolate_sapling")
        .data { translate("White Chocolate Sapling", "白巧克力树苗") }
        .argument("grower", CGrowers.white_chocolate_grower)
        .save()
    val caramel_sapling = sapling.copy("caramel_sapling")
        .data { translate("Caramel Sapling", "焦糖树苗") }
        .argument("grower", CGrowers.caramel_grower)
        .save()
    val candied_cherry_sapling = sapling.copy("candied_cherry_sapling")
        .data { translate("Candied Cherry Sapling", "蜜饯樱桃树苗") }
        .argument("grower", CGrowers.candied_cherry_grower)
        .save()

    //原木
    private val log = simple.sub("*log", { RotatedPillarBlock(it) })
        .copyProperties(Blocks.OAK_LOG)
        .data {
            model {
                action {
                    woodProvider(it).logWithHorizontal(it)
                }
            }
            tag(BlockTags.LOGS)
            tag2(CTags.marshmallow_logs)
        }
    val marshmallow_log = log.copy("marshmallow_log")
        .data { translate("Marshmallow Log", "棉花软糖原木") }
        .save()
    val dark_marshmallow_log = log.copy("dark_marshmallow_log")
        .data { translate("Dark Marshmallow Log", "深色棉花软糖原木") }
        .save()
    val light_marshmallow_log = log.copy("light_marshmallow_log")
        .data { translate("Light Marshmallow Log", "浅色棉花软糖原木") }
        .save()
    val stripped_marshmallow_log = log.copy("stripped_marshmallow_log")
        .data { translate("Stripped Marshmallow Log", " 去皮棉花软糖原木") }
        .save()
    val stripped_dark_marshmallow_log = log.copy("stripped_dark_marshmallow_log")
        .data { translate("Stripped Dark Marshmallow Log", " 去皮深色棉花软糖原木") }
        .save()
    val stripped_light_marshmallow_log = log.copy("stripped_light_marshmallow_log")
        .data { translate("Stripped Light Marshmallow Log", " 去皮浅色棉花软糖原木") }
        .save()

    init {
        AccessorAxeItem.setSTRIPPABLES(HashMap(AccessorAxeItem.getSTRIPPABLES()).apply {
            clog.info("Inject Axe Strippable")
            put(marshmallow_log.block, stripped_marshmallow_log.block)
            put(dark_marshmallow_log.block, stripped_dark_marshmallow_log.block)
            put(light_marshmallow_log.block, stripped_light_marshmallow_log.block)
        })
    }

    // 木板
    val marshmallow_planks = simple.copy("marshmallow_planks")
        .data {
            translate("Marshmallow Planks", "棉花软糖木板")
            tag2(CTags.marshmallow_planks)
        }
        .copyProperties(Blocks.OAK_PLANKS)
        .mapColor(MapColor.COLOR_PINK)
        .save()
    val dark_marshmallow_planks = marshmallow_planks.copy("dark_marshmallow_planks")
        .data {
            translate("Dark Marshmallow Planks", "深色棉花软糖木板")
        }
        .mapColor(MapColor.PODZOL)
        .save()
    val light_marshmallow_planks = marshmallow_planks.copy("light_marshmallow_planks")
        .data {
            translate("Light Marshmallow Planks", "浅色棉花软糖木板")
        }
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()


    // 环境基础方块
    val pudding = simple.copy("pudding")
        .data {
            translate("Pudding", "布丁")
            tag(CTags.CBlockTags.candy_plant_suitable)
        }
        .mapColor(MapColor.SNOW)
        .save()
    val custard_pudding = simple.sub("custard_pudding", { CustardPuddingBlock(it) })
        .data {
            translate("Custard Pudding", "奶皮布丁")
            tag(CTags.CBlockTags.candy_plant_suitable)
            model {
                withParent("grass_block".mcLoc()) {
                    TextureSlot.PARTICLE provide getBlockTexture(pudding.block)
                    TextureSlot.BOTTOM provide getBlockTexture(pudding.block)
                    TextureSlot.TOP provide getBlockTexture(it, "_top_overlay")
                    TextureSlot.SIDE provide getBlockTexture(it, "_side")
                    "overlay" provide getBlockTexture(it, "_side_overlay")
                }.applyState { block, model ->
                    MultiVariantGenerator.multiVariant(block, *createRotatedVariants(model))
                }
            }
        }
        .client {
            renderType(RenderType.cutoutMipped())
            tint { _, level, pos, _ ->
                if (pos != null && level != null && level.hasBiomes()) {
                    return@tint PuddingColor.getColor(level.getBiomeFabric(pos), pos)

                }
                PuddingColor.getDefaultPuddingColor()
            }
        }
        .modifyBlockItem {
            it.client {
                tint { _, _ -> PuddingColor.getDefaultPuddingColor() }
            }
        }
        .modifyProperties { it.randomTicks() }
        .mapColor(MapColor.COLOR_PINK)
        .save()
    val pudding_farm = simple.sub("pudding_farmland", { PuddingFarmBlock(it) })
        .data {
            translate("Pudding Farmland", "布丁耕地")
            tag(CTags.CBlockTags.candy_plant_suitable)
            model {
                val dry by template(ModelTemplates.FARMLAND) {
                    TextureSlot.DIRT provide getBlockTexture(pudding.block)
                    TextureSlot.TOP provide getBlockTexture(it, "_top")
                }
                val moist by template(ModelTemplates.FARMLAND, "_moist") {
                    TextureSlot.DIRT provide getBlockTexture(pudding.block)
                    TextureSlot.TOP provide getBlockTexture(it, "_top_moist")
                }
                state {
                    MultiVariantGenerator.multiVariant(it).with(
                        createEmptyOrFullDispatch(
                            BlockStateProperties.MOISTURE,
                            PuddingFarmBlock.MAX_MOISTURE,
                            moist,
                            dry
                        )
                    )
                }
            }
        }
        .modifyProperties { it.randomTicks() }
        .mapColor(MapColor.COLOR_GRAY)
        .save()

    val ice_cream = simple.copy("ice_cream")
        .data { translate("Ice Cream", "冰淇淋") }
        .copyProperties(Blocks.SNOW)
        .mapColor(MapColor.SNOW)
        .save()
    val mint_ice_cream = ice_cream.copy("mint_ice_cream")
        .data { translate("Mint Ice Cream", "薄荷冰淇淋") }
        .save()
    val strawberry_ice_cream = ice_cream.copy("strawberry_ice_cream")
        .data { translate("Strawberry Ice Cream", "草莓冰淇淋") }
        .save()
    val blueberry_ice_cream = ice_cream.copy("blueberry_ice_cream")
        .data { translate("Blueberry Ice Cream", "蓝莓冰淇淋") }
        .save()


    val sugar_block = simple.sub("sugar_block", factory = { SugarBlock(it) })
        .data {
            translate("Sugar Block", "糖块")
            tag(CTags.CBlockTags.caramel_portal_frame)
        }
        .copyProperties(Blocks.SAND)
        .modifyProperties { it.randomTicks() }
        .mapColor(MapColor.QUARTZ)
        .save()
    val caramel_block = simple.copy("caramel_block")
        .data {
            translate("Caramel Block", "焦糖块")
            tag(CTags.CBlockTags.caramel_portal_frame)
        }
        .copyProperties(Blocks.STONE)
        .mapColor(MapColor.COLOR_ORANGE)
        .save()
        .apply { mixed_bricks_raw.add(this) }

    val caramel_bricks = caramel_block.copy("caramel_bricks")
        .data {
            translate("Caramel Bricks", "焦糖砖块")
            tag2(CTags.candy_bricks)
        }
        .copyProperties(Blocks.STONE_BRICKS)
        .mapColor(MapColor.TERRACOTTA_ORANGE)
        .save()

//    val white_chocolate_stone = caramel_block.copy("white_chocolate_stone")
//        .data {
//            translate("White Chocolate Stone", "白巧克力石头")
//        }
//        .save()


//    val mixed_bricks: List<BlockBuilder.BlockEntry<*>> = mixed_bricks_raw.compose()
//        .map { (first, second) ->
//            caramel_bricks.copy("mixed_${first.id.path}_${second.id.path}")
//                .data {
//                    translate("Mixed Bricks", "混合砖块")
//                    model {
//
//
//                        TODO()
//                        modelExisted().getModel()
//                    }
//                }
//                .save()
//        }
//        .toList()


}

