package cn.breadnicecat.candycraftce.core.block


import cn.breadnicecat.candycraftce.client.PuddingColor
import cn.breadnicecat.candycraftce.core.block.BlockBuilderClientScope.Companion.client
import cn.breadnicecat.candycraftce.core.block.blocks.*
import cn.breadnicecat.candycraftce.core.block.blocks.jelly.JellyBlock
import cn.breadnicecat.candycraftce.core.block.blocks.jelly.SensitiveJellyBlock
import cn.breadnicecat.candycraftce.core.block.blocks.jelly.TrampoJellyBlock
import cn.breadnicecat.candycraftce.core.block.blocks.plant.*
import cn.breadnicecat.candycraftce.core.item.CItems
import cn.breadnicecat.candycraftce.core.item.ItemBuilderClientScope.Companion.client
import cn.breadnicecat.candycraftce.core.tab.CItemTabs.CANDYCRAFT
import cn.breadnicecat.candycraftce.core.tab.CItemTabs.tab
import cn.breadnicecat.candycraftce.core.tag.CTags
import cn.breadnicecat.candycraftce.data.extension.BlockBuilderDataScope.Companion.data
import cn.breadnicecat.candycraftce.data.extension.ItemBuilderDataScope.Companion.data
import cn.breadnicecat.candycraftce.data.extension.MappingScope.Companion.mapping
import cn.breadnicecat.candycraftce.mixin.core.AccessorAxeItem
import cn.breadnicecat.candycraftce.utils.Arguments
import cn.breadnicecat.candycraftce.utils.CUtils
import cn.breadnicecat.candycraftce.utils.CUtils.clog
import cn.breadnicecat.candycraftce.utils.CUtils.generator
import cn.breadnicecat.candycraftce.utils.CUtils.mcLoc
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.Immediate.Companion.immediate
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.BlockModelGenerators.createEmptyOrFullDispatch
import net.minecraft.data.models.BlockModelGenerators.createRotatedVariants
import net.minecraft.data.models.blockstates.*
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping.getBlockTexture
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.data.models.model.TexturedModel
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.Blocks.*
import net.minecraft.world.level.block.state.properties.BlockSetType
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.WoodType
import net.minecraft.world.level.material.MapColor


object CBlocks {
    init {
        CUtils.sign()
    }

    private val mixed_bricks_raw by mutableListOf<BlockBuilder.BlockEntry<*>>().immediate()

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
            loot {
                dropSelf(it)
            }
        }
    private val no_itemModel = simple.copy("*no_itemModel")
        .modifyBlockItem {
            it.removeRecord("model", true)
        }
    //========
    //  植物
    //========

    private val crossPlant = simple.copy("*crossPlant")
        .data {
            model { cross().simpleState() }
        }
        .client { cutout() }
        .copyProperties(POPPY)
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
        .data {
            translate("Mint", "水生薄荷")
            loot { dropWhenSilkTouch(it) }
        }
        .copyProperties(KELP_PLANT)
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
            loot { dropWhenSilkTouch(it) }
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
        .data {
            translate("Mint Block", "水生薄荷块")
            byHoe()
        }
        .copyProperties(HAY_BLOCK)
        .save()
    val raspberry_block = mint_block.copy("raspberry_block")
        .data {
            translate("Raspberry Block", "水生树莓块")
            byHoe()
        }
        .save()
    val banana_seaweeds_block = mint_block.copy("banana_seaweeds_block")
        .data {
            translate("Banana Seaweeds Block", "香蕉海草块")
            byHoe()
        }
        .save()
    val cotton_candy_block = mint_block.copy("cotton_candy_block")
        .data {
            translate("Cotton Candy Block", "棉花糖块")
            byHoe()
        }
        .save()
    val candied_cherry_sack = mint_block.copy("candied_cherry_sack")
        .data {
            translate("Candied Cherry Sack", "蜜饯樱桃袋")
            model {
                cubeBottomTop().simpleState()
            }
            byHoe()
        }
        .save()

    val chewing_gum_block = simple.copy("chewing_gum_block")
        .data {
            translate("Chewing Gum Block", "口香糖块")
            byHoe()
        }
        .copyProperties(SLIME_BLOCK)
        .modifyProperties {
            it.destroyTime(0.6F)
        }
        .save()


    // 树叶
    private val leaves = simple.sub("*leaves", { LeavesBlock(it) })
        .data {
            tag2(CTags.candy_leaves)
            tag2(ItemTags.LEAVES, BlockTags.LEAVES)
            model {
                action {
                    createTrivialBlock(it, TexturedModel.LEAVES)
                }
            }
        }
        .copyProperties(OAK_LEAVES)
        .client { cutout() }

    val chocolate_leaves = leaves.copy("chocolate_leaves")
        .data {
            translate("Chocolate Leaves", "巧克力树叶")
            loot {
                dropLeave(it, chocolate_sapling.block)
            }
        }
        .mapColor(MapColor.DIRT)
        .save()
    val white_chocolate_leaves = leaves.copy("white_chocolate_leaves")
        .data {
            translate("White_chocolate Leaves", "白巧克力树叶")
            loot {
                dropLeave(it, white_chocolate_sapling.block)
            }
        }
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()
    val caramel_leaves = leaves.sub("caramel_leaves", { CaramelLeavesBlock(it) })
        .data {
            translate("Caramel Leaves", "焦糖树叶")
            loot {
                dropLeave(it, caramel_sapling.block)
            }
        }
        .mapColor(MapColor.TERRACOTTA_ORANGE)
        .save()
    val candied_cherry_leaves = leaves.copy("candied_cherry_leaves")
        .data {
            translate("Candied_cherry Leaves", "蜜饯樱桃树叶")
            loot {
                dropLeave(it, candied_cherry_sapling.block)
            }
        }
        .save()
    val magical_leaves = leaves.copy("magical_leaves")
        .data {
            translate("Magical Leaves", "魔法树叶")
            loot {
                dropWhenSilkTouchElse(it, Items.SUGAR, (1..2).generator())
            }
        }
        .client {
            tint { _, level, pos, _ ->
                return@tint if (level != null && pos != null && level.hasBiomes()/*TODO &&is enchantForest*/) {
                    PuddingColor.getEnchantColor(pos.center)
                } else {
                    PuddingColor.getDefaultEnchantColor()
                }
            }
        }
        .modifyBlockItem {
            it.client {
                tint { _, _ -> PuddingColor.getDefaultEnchantColor() }
            }
        }
        .mapColor(MapColor.COLOR_LIGHT_BLUE)
        .save()

    //树苗
    private val sapling = crossPlant.sub("*sapling", { CandySaplingBlock(get("grower"), it) })
        .copyProperties(OAK_SAPLING)

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
        .copyProperties(OAK_LOG)
        .data {
            model {
                action {
                    woodProvider(it).logWithHorizontal(it)
                }
            }
            tag(BlockTags.LOGS)
            tag2(CTags.marshmallow_logs)
            byAxe()
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
        .data { translate("Stripped Marshmallow Log", "去皮棉花软糖原木") }
        .save()
    val stripped_dark_marshmallow_log = log.copy("stripped_dark_marshmallow_log")
        .data { translate("Stripped Dark Marshmallow Log", "去皮深色棉花软糖原木") }
        .save()
    val stripped_light_marshmallow_log = log.copy("stripped_light_marshmallow_log")
        .data { translate("Stripped Light Marshmallow Log", "去皮浅色棉花软糖原木") }
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
            byAxe()
        }
        .copyProperties(OAK_PLANKS)
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
            byShovel()
        }
        .mapColor(MapColor.SNOW)
        .save()
    val custard_pudding = simple.sub("custard_pudding", { CustardPuddingBlock(it) })
        .data {
            translate("Custard Pudding", "奶皮布丁")
            tag(CTags.CBlockTags.candy_plant_suitable)
            model {
                withParent("grass_block".mcLoc()) {
                    TextureSlot.PARTICLE provide pudding.block
                    TextureSlot.BOTTOM provide pudding.block
                    TextureSlot.TOP provide it suffix "_top_overlay"
                    TextureSlot.SIDE provide it suffix "_side"
                    "overlay" provide it suffix "_side_overlay"
                }.applyState { block, model ->
                    MultiVariantGenerator.multiVariant(block, *createRotatedVariants(model))
                }
            }
            loot {
                dropWhenSilkTouchElse(it, pudding.block)
            }
            byShovel()
        }
        .client {
            renderType(RenderType.cutoutMipped())
            tint { _, level, pos, _ ->
                return@tint if (pos != null && level != null && level.hasBiomes()) {
                    PuddingColor.getPuddingColor(level.getBiomeFabric(pos), pos.center)
                } else {
                    PuddingColor.getDefaultPuddingColor()
                }
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
            loot {
                dropOther(it, pudding.block)
            }
            byShovel()
        }
        .modifyProperties { it.randomTicks() }
        .mapColor(MapColor.COLOR_GRAY)
        .save()

    val ice_cream = simple.copy("ice_cream")
        .data {
            translate("Ice Cream", "冰淇淋")
            tag2(CTags.ice_creams)
            byShovel()
        }
        .copyProperties(SNOW)
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
    val sugar_block = simple.copy("sugar_block")
        .data {
            translate("Sugar Block", "糖块")
            tag(CTags.CBlockTags.caramel_portal_frame)
            byPickaxe()
        }
        .save()

    val sugar_sand = simple.sub("sugar_sand", factory = { SugarSandBlock(it) })
        .data {
            translate("Sugar Sand", "砂糖块")
            tag(CTags.CBlockTags.caramel_portal_frame)
            loot {
                dropWhenSilkTouchElse(it, Items.SUGAR, 4.generator())
            }
            byShovel()
        }
        .copyProperties(SAND)
        .modifyProperties { it.randomTicks() }
        .mapColor(MapColor.QUARTZ)
        .save()

    val jawbreaker_bricks = simple.copy("jawbreaker_bricks")
        .data {
            translate("Jawbreaker Bricks", "基岩硬糖砖块")
            loot { noDrop(it) }
        }
        .copyProperties(BEDROCK)
        .save()

    val jawbreaker_light = jawbreaker_bricks.copy("jawbreaker_light")
        .data {
            translate("Jawbreaker Light", "基岩硬糖灯")
        }
        .modifyProperties { it.lightLevel { 14 } }
        .save()

    val chocolate_block = simple.copy("chocolate_stone")
        .data {
            translate("Chocolate Block", "巧克力")
            tag2(CTags.chocolates)
            loot { dropWhenSilkTouchElse(it, chocolate_crushed) }
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_BROWN)
        .save()
    val chocolate_crushed = simple.copy("chocolate_cobblestone")
        .data {
            translate("Crushed Chocolate ", "碎巧克力")
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_BROWN)
        .save()
    val white_chocolate_crushed = simple.copy("white_chocolate_cobblestone")
        .data {
            translate("Crushed White Chocolate", "碎白巧克力")
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_BROWN)
        .save()
    val chocolate_bricks = simple.copy("chocolate_bricks")
        .data {
            translate("Chocolate Bricks", "巧克力砖块")
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_BROWN)
        .save()

    val white_chocolate_block = chocolate_block.copy("white_chocolate_stone")
        .data {
            translate("White Chocolate Block", "白巧克力")
            loot { dropWhenSilkTouchElse(it, white_chocolate_crushed) }
            byPickaxe()
        }
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()
    val white_chocolate_bricks = chocolate_crushed.copy("white_chocolate_bricks")
        .data {
            translate("White Chocolate Bricks", "白巧克力砖块")
            byPickaxe()
        }
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()
    val chocolate_smooth = white_chocolate_crushed.copy("chocolate_stone_tile")
        .data {
            translate("Smooth Chocolate Tile", "平滑巧克力")
            byPickaxe()
        }
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()
    val white_chocolate_smooth = chocolate_bricks.copy("white_chocolate_stone_tile")
        .data {
            translate("Smooth White Chocolate Tile", "平滑白巧克力")
            byPickaxe()
        }
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()

    val caramel_block = simple.copy("caramel_block")
        .data {
            translate("Caramel Block", "焦糖块")
            tag(CTags.CBlockTags.caramel_portal_frame)
            byPickaxe()
        }
        .copyProperties(STONE)
        .mapColor(MapColor.COLOR_ORANGE)
        .save()
        .apply { mixed_bricks_raw!!.add(this) }

    val caramel_bricks = caramel_block.copy("caramel_bricks")
        .data {
            translate("Caramel Bricks", "焦糖砖块")
            tag2(CTags.candy_bricks)
            byPickaxe()
        }
        .copyProperties(STONE_BRICKS)
        .mapColor(MapColor.TERRACOTTA_ORANGE)
        .save()

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

    private val ore = simple.sub("*ore", { DropExperienceBlock(it, get("xp", ConstantInt.ZERO)) })
        .data {
            model {
                withParent("ore".modLoc()) {
                    "stone" provide arguments["parent"] as Block
                    "ore" provide (arguments["ore"] as String).modLoc().withPrefix("block/")
                }.simpleState()
            }
        }
        .client {
            cutout()
        }

    private val chocolate_arg = Arguments.of("parent" to chocolate_block.block)
    private val white_chocolate_arg = Arguments.of("parent" to white_chocolate_block.block)

    val jelly_ore = ore.copy("jelly_ore")
        .data { translate("Jelly Ore", "果冻矿石") }
        .arguments(chocolate_arg)
        .argument("ore", "jelly_ore")
        .copyProperties(IRON_ORE)
        .data {
            tag2(CTags.ore_jelly)
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_BROWN)
        .save()
    val nougat_ore = ore.copy("nougat_ore")
        .data { translate("Nougat Ore", "牛轧糖矿石") }
        .arguments(chocolate_arg)
        .argument("ore", "nougat_ore")
        .argument("xp", UniformInt.of(1, 5))
        .copyProperties(IRON_ORE)
        .data {
            tag2(CTags.ore_nougat)
            loot {
                dropOre(it, CItems.nougat_powder, (3..5).generator())
            }
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_BROWN)
        .save()
    val licorice_ore = ore.copy("licorice_ore")
        .data { translate("Licorice Ore", "盐甘草糖矿石") }
        .arguments(chocolate_arg)
        .argument("ore", "licorice_ore")
        .copyProperties(COAL_ORE)
        .data {
            tag2(CTags.ore_licorice)
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_BROWN)
        .save()
    val honeycomb_ore = ore.copy("honeycomb_ore")
        .data { translate("Honeycomb Ore", "蜜蜡矿石") }
        .arguments(chocolate_arg)
        .argument("xp", UniformInt.of(0, 3))
        .argument("ore", "honeycomb_ore")
        .copyProperties(IRON_ORE)
        .data {
            tag2(CTags.ore_honeycomb)
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_BROWN)
        .save()
    val pez_ore = ore.copy("pez_ore")
        .data { translate("PEZ Ore", "皮礼士糖矿石") }
        .arguments(chocolate_arg)
        .argument("ore", "pez_ore")
        .copyProperties(DIAMOND_BLOCK)
        .modifyProperties { it.lightLevel { 8 } }
        .data {
            tag2(CTags.ore_pez)
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_BROWN)
        .save()
    val white_jelly_ore = jelly_ore.copy("white_jelly_ore")
        .data { translate("White Jelly Ore", "白果冻矿石") }
        .arguments(white_chocolate_arg)
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()
    val white_nougat_ore = nougat_ore.copy("white_nougat_ore")
        .data { translate("White Nougat Ore", "白牛轧糖矿石") }
        .arguments(white_chocolate_arg)
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()
    val white_licorice_ore = licorice_ore.copy("white_licorice_ore")
        .data { translate("White Licorice Ore", "白盐甘草糖矿石") }
        .arguments(white_chocolate_arg)
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()
    val white_honeycomb_ore = honeycomb_ore.copy("white_honeycomb_ore")
        .data { translate("White Honeycomb Ore", "白蜜蜡矿石") }
        .arguments(white_chocolate_arg)
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()
    val white_pez_ore = pez_ore.copy("white_pez_ore")
        .data { translate("White PEZ Ore", "白皮礼士糖矿石") }
        .arguments(white_chocolate_arg)
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()

    val candy_cane_block = simple.copy("candy_cane_block")
        .data {
            translate("Candy Cane Block", "拐杖糖块")
            model {
                cubeColumn().simpleState()
            }
            byPickaxe()
        }
        .copyProperties(STONE)
        .mapColor(MapColor.COLOR_PINK)
        .save()
    val licorice_block = simple.copy("licorice_block")
        .data {
            translate("Licorice Block", "盐甘草糖块")
            byPickaxe()
        }
        .copyProperties(COAL_BLOCK)
        .mapColor(MapColor.COLOR_BLACK)
        .save()
    val licorice_bricks = licorice_block.copy("licorice_bricks")
        .data {
            translate("Licorice Bricks", "盐甘草糖砖块")
            byPickaxe()
        }
        .save()

    val nougat_block = simple.copy("nougat_block")
        .data {
            translate("Nougat Block", "牛轧糖块")
            byPickaxe()
        }
        .copyProperties(IRON_BLOCK)
        .mapColor(MapColor.TERRACOTTA_ORANGE)
        .save()
    val nougat_head = nougat_block.copy("nougat_head")
        .data {
            translate("Nougat Head", "牛轧糖头")
            byPickaxe()
        }
        .mapColor(MapColor.COLOR_ORANGE)
        .save()

    val honeycomb_block = simple.copy("honeycomb_block")
        .data {
            translate("Honeycomb Block", "蜜蜡块")
            byPickaxe()
        }
        .copyProperties(IRON_BLOCK)
        .mapColor(MapColor.COLOR_ORANGE)
        .save()
    val honeycomb_lamp = simple.copy("honeycomb_lamp")
        .data {
            translate("Honeycomb Lamp", "蜜蜡灯")
            byPickaxe()

        }
        .copyProperties(GLOWSTONE)
        .modifyProperties { it.strength(1.5f) }
        .mapColor(MapColor.COLOR_ORANGE)
        .save()

    val pez_block = simple.copy("pez_block")
        .data {
            translate("PEZ Block", "皮礼士糖块")
            byPickaxe()
        }
        .copyProperties(DIAMOND_BLOCK)
        .modifyProperties { it.strength(1.5f) }
        .mapColor(MapColor.TERRACOTTA_WHITE)
        .save()

    private val fence = no_itemModel.sub("*fence", { FenceBlock(it) })
        .data {
            tag2(ItemTags.FENCES, BlockTags.FENCES)
            model {
                family(arguments["parent"], arguments["texture", null]) {
                    fence(it)
                }
            }
        }

    private val marshmallow_arg = Arguments.builder {
        "parent" of marshmallow_planks.block
        "set_type" of BlockSetType.OAK
        "wood_type" of WoodType.OAK
    }
    private val light_marshmallow_arg = Arguments.builder {
        "parent" of light_marshmallow_planks.block
        "set_type" of BlockSetType.BAMBOO
        "wood_type" of WoodType.BAMBOO
    }
    private val dark_marshmallow_arg = Arguments.builder {
        "parent" of dark_marshmallow_planks.block
        "set_type" of BlockSetType.DARK_OAK
        "wood_type" of WoodType.DARK_OAK
    }
    private val candy_cane_arg = Arguments.builder {
        "parent" of candy_cane_block.block
        "texture" of mapping {
            TextureSlot.ALL provide candy_cane_block.block suffix "_side"
        }
    }
    private val licorice_arg = Arguments.of("parent" to licorice_block.block)
    private val licorice_brick_arg = Arguments.of("parent" to licorice_bricks.block)


    val marshmallow_fence = fence.copy("marshmallow_fence")
        .data {
            translate("Marshmallow Fence", "棉花软糖木栅栏")
            byAxe()
        }
        .arguments(marshmallow_arg)
        .copyProperties(marshmallow_planks)
        .save()
    val light_marshmallow_fence = fence.copy("light_marshmallow_fence")
        .data {
            translate("Light Marshmallow Fence", "浅色棉花软糖木栅栏")
            byAxe()
        }
        .arguments(light_marshmallow_arg)
        .copyProperties(light_marshmallow_planks)
        .save()
    val dark_marshmallow_fence = fence.copy("dark_marshmallow_fence")
        .data {
            translate("Dark Marshmallow Fence", "深色棉花软糖木栅栏")
            byAxe()
        }
        .arguments(dark_marshmallow_arg)
        .copyProperties(dark_marshmallow_planks)
        .save()
    val candy_cane_fence = fence.copy("candy_cane_fence")
        .data {
            translate("Candy Cane Fence", "拐杖糖栅栏")
            byPickaxe()
        }
        .arguments(candy_cane_arg)
        .copyProperties(candy_cane_block)
        .save()
    val licorice_fence = fence.copy("licorice_fence")
        .data {
            translate("Licorice Fence", "盐甘草糖栅栏")
            byPickaxe()
        }
        .arguments(licorice_arg)
        .copyProperties(licorice_block)
        .save()
    val licorice_brick_fence = fence.copy("licorice_brick_fence")
        .data {
            translate("Licorice Brick Fence", "盐甘草糖砖栅栏")
            byPickaxe()
        }
        .arguments(licorice_brick_arg)
        .copyProperties(licorice_bricks)
        .save()

    private val wall = no_itemModel.sub("*wall", { WallBlock(it) })
        .data {
            tag2(ItemTags.WALLS, BlockTags.WALLS)
            model {
                family(arguments["parent"], arguments["texture", null]) {
                    wall(it)
                }
            }
        }

    val candy_cane_wall = wall.copy("candy_cane_wall")
        .data {
            translate("Candy Cane Wall", "拐杖糖墙")
            byPickaxe()
        }
        .arguments(candy_cane_arg)
        .copyProperties(candy_cane_block)
        .save()
    val licorice_wall = wall.copy("licorice_wall")
        .data {
            translate("Licorice Wall", "盐甘草糖墙")
            byPickaxe()
        }
        .arguments(licorice_arg)
        .copyProperties(licorice_block)
        .save()
    val licorice_brick_wall = wall.copy("licorice_brick_wall")
        .data {
            translate("Licorice Brick Wall", "盐甘草糖砖墙")
            byPickaxe()
        }
        .arguments(licorice_brick_arg)
        .copyProperties(licorice_bricks)
        .save()

    val slab = no_itemModel.sub("*slab", { SlabBlock(it) })
        .data {
            tag2(ItemTags.SLABS, BlockTags.SLABS)
            model {
                family(arguments["parent"], arguments["texture", null]) {
                    slab(it)
                }
            }
        }


    private val mint_arg = Arguments.of("parent" to mint_block.block)
    private val raspberry_arg = Arguments.of("parent" to raspberry_block.block)
    private val banana_seaweeds_arg = Arguments.of("parent" to banana_seaweeds_block.block)
    private val cotton_candy_arg = Arguments.of("parent" to cotton_candy_block.block)
    private val candied_cherry_arg = Arguments.builder {
        "parent" of candied_cherry_sack.block
        "texture" of mapping {
            TextureSlot.ALL provide candied_cherry_sack.block suffix "_side"
        }
    }
    private val chewing_gum_arg = Arguments.of("parent" to chewing_gum_block.block)
    private val ice_cream_arg = Arguments.of("parent" to ice_cream.block)
    private val mint_ice_cream_arg = Arguments.of("parent" to mint_block.block)
    private val strawberry_ice_cream_arg = Arguments.of("parent" to strawberry_ice_cream.block)
    private val blueberry_ice_cream_arg = Arguments.of("parent" to blueberry_ice_cream.block)

    val mint_slab = slab.copy("mint_slab")
        .data {
            translate("Mint Slab", "薄荷台阶")
            byHoe()
        }
        .arguments(mint_arg)
        .copyProperties(mint_block)
        .save()
    val raspberry_slab = slab.copy("raspberry_slab")
        .data {
            translate("Raspberry Slab", "水生树莓台阶")
            byHoe()
        }
        .arguments(raspberry_arg)
        .copyProperties(raspberry_block)
        .save()
    val banana_seaweeds_slab = slab.copy("banana_seaweeds_slab")
        .data {
            translate("Banana Seaweeds Slab", "香蕉海草台阶")
            byHoe()
        }
        .arguments(banana_seaweeds_arg)
        .copyProperties(banana_seaweeds_block)
        .save()
    val cotton_candy_slab = slab.copy("cotton_candy_slab")
        .data {
            translate("Cotton Candy Slab", "棉花糖台阶")
            byHoe()
        }
        .arguments(cotton_candy_arg)
        .copyProperties(cotton_candy_block)
        .save()
    val candied_cherry_slab = slab.copy("candied_cherry_slab")
        .data {
            translate("Candied Cherry Slab", "蜜饯樱桃台阶")
            byHoe()
        }
        .arguments(candied_cherry_arg)
        .copyProperties(candied_cherry_sack)
        .save()
    val chewing_gum_slab = slab.copy("chewing_gum_slab")
        .data {
            translate("Chewing Gum Slab", "口香糖台阶")
            byHoe()
        }
        .arguments(chewing_gum_arg)
        .copyProperties(chewing_gum_block)
        .save()
    val marshmallow_slab = slab.copy("marshmallow_slab")
        .data {
            translate("Marshmallow Slab", "棉花软糖木台阶")
            byAxe()
        }
        .arguments(marshmallow_arg)
        .copyProperties(marshmallow_planks)
        .save()
    val light_marshmallow_slab = slab.copy("light_marshmallow_slab")
        .data {
            translate("Light Marshmallow Slab", "浅色棉花软糖木台阶")
            byAxe()
        }
        .arguments(light_marshmallow_arg)
        .copyProperties(light_marshmallow_planks)
        .save()
    val dark_marshmallow_slab = slab.copy("dark_marshmallow_slab")
        .data {
            translate("Dark Marshmallow Slab", "深色棉花软糖木台阶")
            byAxe()
        }
        .arguments(dark_marshmallow_arg)
        .copyProperties(dark_marshmallow_planks)
        .save()
    val candy_cane_slab = slab.copy("candy_cane_slab")
        .data {
            translate("Candy Cane Slab", "拐杖糖台阶")
            byPickaxe()
        }
        .arguments(candy_cane_arg)
        .copyProperties(candy_cane_block)
        .save()
    val licorice_slab = slab.copy("licorice_slab")
        .data {
            translate("Licorice Slab", "盐甘草糖台阶")
            byPickaxe()
        }
        .arguments(licorice_arg)
        .copyProperties(licorice_block)
        .save()
    val licorice_brick_slab = slab.copy("licorice_brick_slab")
        .data {
            translate("Licorice Brick Slab", "盐甘草糖砖台阶")
            byPickaxe()
        }
        .arguments(licorice_brick_arg)
        .copyProperties(licorice_bricks)
        .save()
    val ice_cream_slab = slab.copy("ice_cream_slab")
        .data {
            translate("Ice Cream Slab", "冰淇淋台阶")
            byShovel()
        }
        .arguments(ice_cream_arg)
        .copyProperties(ice_cream)
        .save()
    val mint_ice_cream_slab = slab.copy("mint_ice_cream_slab")
        .data {
            translate("Mint Ice Cream Slab", "薄荷冰淇淋台阶")
            byShovel()
        }
        .arguments(mint_ice_cream_arg)
        .copyProperties(mint_ice_cream)
        .save()
    val strawberry_ice_cream_slab = slab.copy("strawberry_ice_cream_slab")
        .data {
            translate("Strawberry Ice Cream Slab", "草莓冰淇淋台阶")
            byShovel()
        }
        .arguments(strawberry_ice_cream_arg)
        .copyProperties(strawberry_ice_cream)
        .save()
    val blueberry_ice_cream_slab = slab.copy("blueberry_ice_cream_slab")
        .data {
            translate("Blueberry Ice Cream Slab", "蓝莓冰淇淋台阶")
            byShovel()
        }
        .arguments(blueberry_ice_cream_arg)
        .copyProperties(blueberry_ice_cream)
        .save()
    private val stair = no_itemModel.sub("*stairs", { StairBlock((get("parent") as Block).defaultBlockState(), it) })
        .data {
            tag2(ItemTags.STAIRS, BlockTags.STAIRS)
            model {
                family(arguments["parent"], arguments["texture", null]) {
                    stairs(it)
                }
            }
        }

    val mint_stairs = stair.copy("mint_stairs")
        .data {
            translate("Mint Stairs", "薄荷楼梯")
            byHoe()
        }
        .arguments(mint_arg)
        .copyProperties(mint_block)
        .save()
    val raspberry_stairs = stair.copy("raspberry_stairs")
        .data {
            translate("Raspberry Stairs", "水生树莓楼梯")
            byHoe()
        }
        .arguments(raspberry_arg)
        .copyProperties(raspberry_block)
        .save()
    val banana_seaweeds_stairs = stair.copy("banana_seaweeds_stairs")
        .data {
            translate("Banana Seaweeds Stairs", "香蕉海草楼梯")
            byHoe()
        }
        .arguments(banana_seaweeds_arg)
        .copyProperties(banana_seaweeds_block)
        .save()
    val cotton_candy_stairs = stair.copy("cotton_candy_stairs")
        .data {
            translate("Cotton Candy Stairs", "棉花糖楼梯")
            byHoe()
        }
        .arguments(cotton_candy_arg)
        .copyProperties(cotton_candy_block)
        .save()
    val candied_cherry_stairs = stair.copy("candied_cherry_stairs")
        .data {
            translate("Candied Cherry Stairs", "蜜饯樱桃楼梯")
            byHoe()
        }
        .arguments(candied_cherry_arg)
        .copyProperties(candied_cherry_sack)
        .save()
    val chewing_gum_stairs = stair.copy("chewing_gum_stairs")
        .data {
            translate("Chewing Gum Stairs", "口香糖楼梯")
            byHoe()
        }
        .arguments(chewing_gum_arg)
        .copyProperties(chewing_gum_block)
        .save()
    val marshmallow_stairs = stair.copy("marshmallow_stairs")
        .data {
            translate("Marshmallow Stairs", "棉花软糖木楼梯")
            byAxe()
        }
        .arguments(marshmallow_arg)
        .copyProperties(marshmallow_planks)
        .save()
    val light_marshmallow_stairs = stair.copy("light_marshmallow_stairs")
        .data {
            translate("Light Marshmallow Stairs", "浅色棉花软糖木楼梯")
            byAxe()
        }
        .arguments(light_marshmallow_arg)
        .copyProperties(light_marshmallow_planks)
        .save()
    val dark_marshmallow_stairs = stair.copy("dark_marshmallow_stairs")
        .data {
            translate("Dark Marshmallow Stairs", "深色棉花软糖木楼梯")
            byAxe()
        }
        .arguments(dark_marshmallow_arg)
        .copyProperties(dark_marshmallow_planks)
        .save()
    val candy_cane_stairs = stair.copy("candy_cane_stairs")
        .data {
            translate("Candy Cane Stairs", "拐杖糖楼梯")
            byPickaxe()
        }
        .arguments(candy_cane_arg)
        .copyProperties(candy_cane_block)
        .save()
    val licorice_stairs = stair.copy("licorice_stairs")
        .data {
            translate("Licorice Stairs", "盐甘草糖楼梯")
            byPickaxe()
        }
        .arguments(licorice_arg)
        .copyProperties(licorice_block)
        .save()
    val licorice_brick_stairs = stair.copy("licorice_brick_stairs")
        .data {
            translate("Licorice Brick Stairs", "盐甘草糖砖楼梯")
            byPickaxe()
        }
        .arguments(licorice_brick_arg)
        .copyProperties(licorice_bricks)
        .save()
    val ice_cream_stairs = stair.copy("ice_cream_stairs")
        .data {
            translate("Ice Cream Stairs", "冰淇淋楼梯")
            byShovel()
        }
        .arguments(ice_cream_arg)
        .copyProperties(ice_cream)
        .save()
    val mint_ice_cream_stairs = stair.copy("mint_ice_cream_stairs")
        .data {
            translate("Mint Ice Cream Stairs", "薄荷冰淇淋楼梯")
            byShovel()
        }
        .arguments(mint_ice_cream_arg)
        .copyProperties(mint_ice_cream)
        .save()
    val strawberry_ice_cream_stairs = stair.copy("strawberry_ice_cream_stairs")
        .data {
            translate("Strawberry Ice Cream Stairs", "草莓冰淇淋楼梯")
            byShovel()
        }
        .arguments(strawberry_ice_cream_arg)
        .copyProperties(strawberry_ice_cream)
        .save()
    val blueberry_ice_cream_stairs = stair.copy("blueberry_ice_cream_stairs")
        .data {
            translate("Blueberry Ice Cream Stairs", "蓝莓冰淇淋楼梯")
            byShovel()
        }
        .arguments(blueberry_ice_cream_arg)
        .copyProperties(blueberry_ice_cream)
        .save()

    private val door = no_itemModel.sub("*door", { DoorBlock(it, get("set_type")) })
        .data {
            tag2(ItemTags.DOORS, BlockTags.DOORS)
            model {
                action {
                    createDoor(it)
                }
            }
        }

    val marshmallow_door = door.copy("marshmallow_door")
        .data {
            translate("Marshmallow Door", "棉花软糖木门")
            byAxe()
        }
        .arguments(marshmallow_arg)
        .copyProperties(OAK_DOOR)
        .save()
    val light_marshmallow_door = door.copy("light_marshmallow_door")
        .data {
            translate("Light Marshmallow Door", "浅色棉花软糖木门")
            byAxe()
        }
        .arguments(light_marshmallow_arg)
        .copyProperties(marshmallow_door)
        .save()
    val dark_marshmallow_door = door.copy("dark_marshmallow_door")
        .data {
            translate("Dark Marshmallow Door", "深色棉花软糖木门")
            byAxe()
        }
        .arguments(dark_marshmallow_arg)
        .copyProperties(marshmallow_door)
        .save()

    private val trap_door = no_itemModel.sub("*trap_door", { TrapDoorBlock(it, get("set_type")) })
        .data {
            tag2(ItemTags.TRAPDOORS, BlockTags.TRAPDOORS)
            model {
                action {
                    createTrapdoor(it)
                }
            }
        }
    val marshmallow_trapdoor = trap_door.copy("marshmallow_trapdoor")
        .data {
            translate("Marshmallow Trapdoor", "棉花软糖木活板门")
            byAxe()
        }
        .arguments(marshmallow_arg)
        .copyProperties(OAK_TRAPDOOR)
        .save()
    val light_marshmallow_trapdoor = trap_door.copy("light_marshmallow_trapdoor")
        .data {
            translate("Light Marshmallow Trapdoor", "浅色棉花软糖木活板门")
            byAxe()
        }
        .arguments(light_marshmallow_arg)
        .copyProperties(marshmallow_trapdoor)
        .save()
    val dark_marshmallow_trapdoor = trap_door.copy("dark_marshmallow_trapdoor")
        .data {
            translate("Dark Marshmallow Trapdoor", "深色棉花软糖木活板门")
            byAxe()
        }
        .arguments(dark_marshmallow_arg)
        .copyProperties(marshmallow_trapdoor)
        .save()
    private val fence_gate = no_itemModel.sub("*fence_gate", { FenceGateBlock(it, get("wood_type")) })
        .data {
            tag2(ItemTags.FENCE_GATES, BlockTags.FENCE_GATES)
            model {
                family(arguments["parent"], arguments["texture", null]) {
                    fenceGate(it)
                }
            }
        }

    val marshmallow_fence_gate = fence_gate.copy("marshmallow_fence_gate")
        .data {
            translate("Marshmallow Fence Gate", "棉花软糖木栅栏门")
            byAxe()
        }
        .arguments(marshmallow_arg)
        .copyProperties(OAK_FENCE_GATE)
        .save()
    val light_marshmallow_fence_gate = fence_gate.copy("light_marshmallow_fence_gate")
        .data {
            translate("Light Marshmallow Fence Gate", "浅色棉花软糖木栅栏门")
            byAxe()
        }
        .arguments(light_marshmallow_arg)
        .copyProperties(marshmallow_fence_gate)
        .save()
    val dark_marshmallow_fence_gate = fence_gate.copy("dark_marshmallow_fence_gate")
        .data {
            translate("Dark Marshmallow Fence Gate", "深色棉花软糖木栅栏门")
            byAxe()
        }
        .arguments(dark_marshmallow_arg)
        .copyProperties(marshmallow_fence_gate)
        .save()

    val cotton_candy_web = simple.sub("cotton_candy_web", { WebBlock(it) })
        .data {
            translate("Cotton Candy Web", "棉花糖网")
            model { cross().simpleState() }
            bySword()
        }
        .copyProperties(COBWEB)
        .modifyBlockItem {
            it.data {
                modelBlockDirect(block)
            }
        }
        .client { cutout() }
        .save()

    private val jelly = simple.sub("*jelly", { JellyBlock(it) })
        .client { translucent() }
        .copyProperties(SLIME_BLOCK)
        .modifyProperties { it.strength(5f, 2000f) }

    val trampojelly =
        jelly.sub("trampojelly", {
            TrampoJellyBlock(
                it, get("accY", null),
                get("fallMultiplier", null)
            )
        }).data { translate("Trampojelly", " 弹力果冻") }
            .argument("accY", 2.0)
            .argument("fallMultiplier", 0.2f)
            .save()
    val red_trampojelly = trampojelly.copy("red_trampojelly")
        .data { translate("Red Trampojelly", " 红色弹力果冻") }
        .argument("accY", 4.0)
        .argument("fallMultiplier", 0.1f)
        .save()
    val soft_trampojelly = trampojelly.copy("soft_trampojelly")
        .data { translate("Soft Trampojelly", " 软弹力果冻") }
        .argument("accY", 2.0)
        .argument("fallMultiplier", 0f)
        .save()
    val jelly_shock_absorber = trampojelly.copy("jelly_shock_absorber")
        .data { translate("Jelly Shock Absorber", "减震果冻") }
        .argument("accY", null)
        .argument("fallMultiplier", 0f)
        .save()
    val sensitive_jelly = jelly.sub("sensitive_jelly", { SensitiveJellyBlock(it) })
        .data {
            translate("Sensitive Jelly", "敏感果冻")
            model {
                val simple by cubeAll()
                val active by template(ModelTemplates.CUBE_ALL, suffix = "_powered") {
                    TextureSlot.ALL provide getBlockTexture(it, "_powered")
                }
                state {
                    MultiVariantGenerator.multiVariant(it)
                        .with(
                            BlockModelGenerators.createBooleanModelDispatch(
                                SensitiveJellyBlock.POWERED,
                                active,
                                simple
                            )
                        )
                }
            }
        }
        .save()


    val sugar_spikes = simple.sub("sugar_spikes", { SugarSpikesBlock(it) })
        .data {
            translate("Sugar Spikes", "糖刺")
            model { cross().simpleState() }
        }
        .copyProperties(pez_block)
        .modifyProperties { it.noCollission() }
        .modifyBlockItem {
            it.data {
                modelFlat(getBlockTexture(block))
            }
        }
        .client { cutout() }
        .save()

    val cranberry_spikes = sugar_spikes.copy("cranberry_spikes")
        .data { translate("Cranberry Spikes", "蔓越莓刺") }
        .save()


    val chewing_gum_puddle = simple.sub("chewing_gum_puddle", { ChewingGumPuddleBlock(it) })
        .data {
            translate("Chewing Gum Puddle", "口香糖片")
            model {
                modelExisted().simpleState()
            }
        }
        .copyProperties(SLIME_BLOCK)
        .modifyProperties {
            it.destroyTime(2.5F).noCollission()
        }
        .modifyBlockItem {
            it.data {
                modelBlockDirect(block)
            }
        }
        .client { cutout() }
        .save()

    val marshmallow_ladder = simple.sub("marshmallow_ladder", { LadderBlock(it) })
        .data {
            tag(BlockTags.CLIMBABLE)
        }
        .copyProperties(LADDER)
        .modifyBlockItem {
            it.data {
                modelBlockDirect(block)
            }
        }
        .client { cutout() }
        .save()

    val grenadine_ice = simple.sub("grenadine_ice", { IceBlock(it) })
        .data {
            translate("Grenadine Ice", "红石榴浆冰")
            loot {
                dropWhenSilkTouch(it)
            }
        }
        .client { translucent() }
        .mapColor(MapColor.COLOR_PINK)
        .copyProperties(ICE)
        .save()

    val caramel_glass = simple.sub("caramel_glass", { GlassBlock(it) })
        .data {
            translate("Caramel Glass", "焦糖玻璃")
            loot {
                dropWhenSilkTouch(it)
            }
        }
        .apply {
            removeRecord("model", true)
            modifyBlockItem {
                it.removeRecord("model", true)
            }
        }
        .client { cutout() }
        .copyProperties(GLASS)
        .save()
    val round_caramel_glass = caramel_glass.copy("round_caramel_glass")
        .data { translate("Round Caramel Glass", "圆形焦糖玻璃") }
        .save()
    val diamond_caramel_glass = caramel_glass.copy("diamond_caramel_glass")
        .data { translate("Diamond Caramel Glass", "钻石焦糖玻璃") }
        .save()

    val caramel_glass_pane = simple.sub("caramel_glass_pane", { IronBarsBlock(it) })
        .data {
            translate("Caramel Glass Pane", "焦糖玻璃板")
            loot {
                dropWhenSilkTouch(it)
            }
            model {
                action {
                    createGlassBlocks(arguments["glass"], it)
                }
            }
        }
        .client { cutout() }
        .modifyBlockItem {
            it.removeRecord("model", true)
        }
        .argument("glass", caramel_glass.block)
        .copyProperties(GLASS_PANE)
        .save()
    val round_caramel_glass_pane = caramel_glass_pane.copy("round_caramel_glass_pane")
        .data { translate("Round Caramel Glass Pane", "圆形焦糖玻璃板") }
        .argument("glass", round_caramel_glass.block)
        .save()
    val diamond_caramel_glass_pane = caramel_glass_pane.copy("diamond_caramel_glass_pane")
        .data { translate("Diamond Caramel Glass Pane", "钻石焦糖玻璃板") }
        .argument("glass", diamond_caramel_glass.block)
        .save()

    val caramel_portal = simple.sub("caramel_portal", { CaramelPortalBlock(it) })
        .data {
            translate("Caramel Portal", "焦糖传送门")
            model {
                val x by withParent(
                    getBlockTexture(NETHER_PORTAL, "_ew"),
                    parentPrefix = null,
                    outputSuffix = "_x"
                ) {
                    "portal" provide it
                    TextureSlot.PARTICLE provide it
                }
                val y by modelExisted("caramel_portal_y".modLoc().withPrefix("block/"))
                state {
                    MultiPartGenerator.multiPart(it)
                        .with(
                            Condition.condition().term(CaramelPortalBlock.X, true),
                            Variant.variant().with(VariantProperties.MODEL, x)
                        )
                        .with(
                            Condition.condition().term(CaramelPortalBlock.Y, true),
                            Variant.variant().with(VariantProperties.MODEL, y)
                        )
                        .with(
                            Condition.condition().term(CaramelPortalBlock.Z, true),
                            Variant.variant().with(VariantProperties.MODEL, x)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                        )

                }
            }
            loot { noDrop(it) }
        }
        .noBlockItem()
        .client { translucent() }
        .copyProperties(NETHER_PORTAL)
        .save()

    val honeycomb_torch = simple.sub("honeycomb_torch", { TorchBlock(it, ParticleTypes.FLAME) })
        .data {
            translate("Honeycomb Torch", "蜜蜡火把")
        }
        .apply { removeRecord("model", optional = true) }
        .client { cutout() }
        .copyProperties(TORCH)
        .noBlockItem()
        .save()

    val wall_honeycomb_torch = honeycomb_torch.sub("wall_honeycomb_torch", { WallTorchBlock(it, ParticleTypes.FLAME) })
        .data {
            model {
                action {
                    createNormalTorch(honeycomb_torch.block, it)
                }
            }
        }
        .copyProperties(WALL_TORCH)
        .modifyProperties { it.dropsLike(honeycomb_torch.block) }
        .save()


}

