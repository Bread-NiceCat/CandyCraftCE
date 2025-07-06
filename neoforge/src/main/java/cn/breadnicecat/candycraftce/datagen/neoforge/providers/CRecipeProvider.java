package cn.breadnicecat.candycraftce.datagen.neoforge.providers;

import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.candycraftce.block.CBlockTags.*;
import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.datagen.neoforge.providers.recipes.SugarFactoryRecipeBuilder.factory;
import static cn.breadnicecat.candycraftce.datagen.neoforge.providers.recipes.SugarFurnaceRecipeBuilder.furnace;
import static cn.breadnicecat.candycraftce.item.CItemTags.IT_LEAF;
import static cn.breadnicecat.candycraftce.item.CItems.*;
import static java.util.Arrays.asList;
import static net.minecraft.data.recipes.RecipeCategory.*;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;
import static net.minecraft.world.item.Items.GOLD_NUGGET;
import static net.minecraft.world.item.Items.SUGAR;
import static net.minecraft.world.item.crafting.Ingredient.of;
import static net.minecraft.world.level.block.Blocks.REDSTONE_WIRE;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 * * def()用来确定配方的输出,id()依此生成唯一配方名，防止重复.一次def()只能且必须使用一次id(),否则异常
 * * have()用来确定该配方最重要的物品,has()依此生成解锁成就，hasn()依此获取解锁成就的id.
 * 一次have()只能且必须使用has()和hasn()共记两次,否则异常.用hasrst()可重置计数器,
 * 目前只支持单物品,多物品需要自定义
 */
public class CRecipeProvider extends RecipeProvider {
	public CRecipeProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> reg) {
		super(arg, reg);
	}
	
	
	@SuppressWarnings("DuplicatedCode")
	@Override
	protected void buildRecipes(@NotNull RecipeOutput writer) {
		shaped(MISC, def(MARSHMALLOW_STICK), 4)
				.pattern("x  ").define('x', have(BT_MARSHMALLOW_PLANKS.it()))
				.pattern("x  ")
				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(MISC, def(MARSHMALLOW_STICK), 16)
				.pattern("x  ").define('x', have(BT_MARSHMALLOW_LOGS.it()))
				.pattern("x  ")
				.unlockedBy(hasn(), has()).save(writer, id());
		furnace(def(HOT_GUMMY)).ingredient(CItems.GUMMY).exp(2f).save(writer, id());
		shaped(FOOD, def(WAFFLE))
				.pattern("## ")
				.pattern("## ").define('#', have(WAFFLE_NUGGET))
				.pattern("## ")
				.unlockedBy(hasn(), has()).save(writer, id());
		furnace(def(CRANFISH_COOKED)).ingredient(CRANFISH).exp(2f).save(writer, id());
		shaped(TOOLS, def(FORK))
				.pattern(" / ").define('/', have(PEZ))
				.pattern("/  ")
				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(COMBAT, def(HONEYCOMB_ARROW), 4)
				.pattern("^  ").define('^', have(HONEYCOMB))
				.pattern("/  ").define('/', MARSHMALLOW_STICK)
				.pattern("*  ").define('*', IT_LEAF)
				.unlockedBy(hasn(), has()).save(writer, id());
//		shaped(COMBAT, def(CARAMEL_BOW))
//				.pattern(" #|").define('#', MARSHMALLOW_STICK)
//				.pattern("#x|").define('x', have(HONEYCOMB))
//				.pattern(" #|").define('|', COTTON_CANDY)
//				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(TOOLS, def(HONEYCOMB_TORCH_ITEM), 4)
				.pattern("*  ").define('*', have(HONEYCOMB))
				.pattern("/  ").define('/', MARSHMALLOW_STICK)
				.unlockedBy(hasn(), has()).save(writer, id());
		tools(MARSHMALLOW_SWORD, MARSHMALLOW_SHOVEL, MARSHMALLOW_PICKAXE, MARSHMALLOW_AXE, MARSHMALLOW_HOE,
				of(MARSHMALLOW_STICK), of(have(BT_MARSHMALLOW_PLANKS.it())))
				.forEach(e -> {
					def(e.getResult());
					hasrst();
					e.unlockedBy(hasn(), has()).save(writer, id());
				});
		tools(LICORICE_SWORD, LICORICE_SHOVEL, LICORICE_PICKAXE, LICORICE_AXE, LICORICE_HOE,
				of(MARSHMALLOW_STICK), of(have(LICORICE)))
				.forEach(e -> {
					def(e.getResult());
					hasrst();
					e.unlockedBy(hasn(), has()).save(writer, id());
				});
		tools(HONEY_SWORD, HONEY_SHOVEL, HONEY_PICKAXE, HONEY_AXE, HONEY_HOE,
				of(MARSHMALLOW_STICK), of(have(HONEYCOMB)))
				.forEach(e -> {
					def(e.getResult());
					hasrst();
					e.unlockedBy(hasn(), has()).save(writer, id());
				});
		tools(PEZ_SWORD, PEZ_SHOVEL, PEZ_PICKAXE, PEZ_AXE, PEZ_HOE,
				of(MARSHMALLOW_STICK), of(have(PEZ)))
				.forEach(e -> {
					def(e.getResult());
					hasrst();
					e.unlockedBy(hasn(), has()).save(writer, id());
				});
		armors(LICORICE_HELMET, LICORICE_CHESTPLATE, LICORICE_LEGGINGS, LICORICE_BOOTS, of(have(LICORICE)))
				.forEach(e -> {
					def(e.getResult());
					hasrst();
					e.unlockedBy(hasn(), has()).save(writer, id());
				});
		armors(HONEYCOMB_HELMET, HONEYCOMB_CHESTPLATE, HONEYCOMB_LEGGINGS, HONEYCOMB_BOOTS, of(have(HONEYCOMB)))
				.forEach(e -> {
					def(e.getResult());
					hasrst();
					e.unlockedBy(hasn(), has()).save(writer, id());
				});
		armors(PEZ_HELMET, PEZ_CHESTPLATE, PEZ_LEGGINGS, PEZ_BOOTS, of(have(PEZ)))
				.forEach(e -> {
					def(e.getResult());
					hasrst();
					e.unlockedBy(hasn(), has()).save(writer, id());
				});
		shaped(COMBAT, def(WATER_MASK))
				.pattern("###").define('#', SUGAR_CRYSTAL)
				.pattern("#@#").define('@', have(CRANFISH_SCALE))
				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(COMBAT, def(TRAMPOJELLY_BOOTS))
				.pattern("# #").define('#', SUGAR_CRYSTAL)
				.pattern("@ @").define('@', have(JELLY_SHOCK_ABSORBER))
				.unlockedBy(hasn(), has()).save(writer, id());
		furnace(def(CARAMEL_BLOCK)).ingredient(SUGAR_BLOCK).exp(0.5f).save(writer, id());
		
		shaped(MISC, def(CHEWING_GUM_PUDDLE))
				.pattern("##").define('#', have(CHEWING_GUM))
				.unlockedBy(hasn(), has()).save(writer, id());
		
		of3x3(def(SUGAR_BLOCK), 1, of(have(SUGAR))).unlockedBy(hasn(), has()).save(writer, id());
		
		//BRICKS（方块）和BRICK（物品）一定要区分开来！
		factory(def(CARAMEL_BRICK), 4).ingredient(CARAMEL_BLOCK).save(writer, id());
		factory(def(CHOCOLATE_BRICK)).ingredient(CHOCOLATE_STONE).save(writer, id());
		factory(def(WHITE_CHOCOLATE_BRICK)).ingredient(WHITE_CHOCOLATE_STONE).save(writer, id());
		//类似于地狱砖的方式
		of2x2(def(CARAMEL_BRICKS), 1, of(have(CARAMEL_BRICK))).unlockedBy(hasn(), has()).save(writer, id());
		shaped(BUILDING_BLOCKS, def(CHOCOLATE_CARAMEL_BRICKS))
				.pattern("#@").define('#', have(CARAMEL_BRICK))
				.pattern("@#").define('@', CHOCOLATE_BRICK)
				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(BUILDING_BLOCKS, def(WHITE_CHOCOLATE_CARAMEL_BRICKS))
				.pattern("#@").define('#', have(CARAMEL_BRICK))
				.pattern("@#").define('@', WHITE_CHOCOLATE_BRICK)
				.unlockedBy(hasn(), has()).save(writer, id());
		
		//甜草越往下越珍稀,所以只能3->0 , 0-/->3
		factory(def(SWEET_GRASS_3)).ingredient(SWEET_GRASS_2).save(writer, id());
		factory(def(SWEET_GRASS_2)).ingredient(SWEET_GRASS_1).save(writer, id());
		factory(def(SWEET_GRASS_1)).ingredient(SWEET_GRASS_0).save(writer, id());
		factory(def(SWEET_GRASS_0)).ingredient(SWEET_GRASS_3).advanced().save(writer, id());
		
		of3x3(def(MINT_BLOCK), 1, of(have(MINT))).unlockedBy(hasn(), has()).save(writer, id());
		of3x3(def(RASPBERRY_BLOCK), 1, of(have(ROPE_RASPBERRY))).unlockedBy(hasn(), has()).save(writer, id());
		of3x3(def(BANANA_SEAWEEDS_BLOCK), 1, of(have(BANANA_SEAWEED))).unlockedBy(hasn(), has()).save(writer, id());
		of3x3(def(COTTON_CANDY_BLOCK), 1, of(have(COTTON_CANDY))).unlockedBy(hasn(), has()).save(writer, id());
		of3x3(def(CANDIED_CHERRY_SACK), 1, of(have(CANDIED_CHERRY))).unlockedBy(hasn(), has()).save(writer, id());
		of3x3(def(CHEWING_GUM_BLOCK), 1, of(have(CHEWING_GUM_PUDDLE))).unlockedBy(hasn(), has()).save(writer, id());
		shapeless(BUILDING_BLOCKS, def(MARSHMALLOW_PLANKS), 4).requires(have(MARSHMALLOW_LOG)).unlockedBy(hasn(), has()).save(writer, id());
		shapeless(BUILDING_BLOCKS, def(DARK_MARSHMALLOW_PLANKS), 4).requires(have(DARK_MARSHMALLOW_LOG)).unlockedBy(hasn(), has()).save(writer, id());
		shapeless(BUILDING_BLOCKS, def(LIGHT_MARSHMALLOW_PLANKS), 4).requires(have(LIGHT_MARSHMALLOW_LOG)).unlockedBy(hasn(), has()).save(writer, id());
		shapeless(BUILDING_BLOCKS, def(MARSHMALLOW_PLANKS), 4).requires(have(STRIPPED_MARSHMALLOW_LOG)).unlockedBy(hasn(), has()).save(writer, id());
		shapeless(BUILDING_BLOCKS, def(DARK_MARSHMALLOW_PLANKS), 4).requires(have(STRIPPED_DARK_MARSHMALLOW_LOG)).unlockedBy(hasn(), has()).save(writer, id());
		shapeless(BUILDING_BLOCKS, def(LIGHT_MARSHMALLOW_PLANKS), 4).requires(have(STRIPPED_LIGHT_MARSHMALLOW_LOG)).unlockedBy(hasn(), has()).save(writer, id());
		furnace(def(CHOCOLATE_STONE)).ingredient(CHOCOLATE_COBBLESTONE).exp(0.5f).save(writer, id());
		furnace(def(WHITE_CHOCOLATE_STONE)).ingredient(WHITE_CHOCOLATE_COBBLESTONE).exp(0.5f).save(writer, id());
		of2x2(def(CHOCOLATE_BRICKS), 1, of(have(CHOCOLATE_STONE))).unlockedBy(hasn(), has()).save(writer, id());
		of2x2(def(WHITE_CHOCOLATE_BRICKS), 1, of(have(WHITE_CHOCOLATE_STONE))).unlockedBy(hasn(), has()).save(writer, id());
		furnace(def(CHOCOLATE_STONE_TILE)).ingredient(CHOCOLATE_STONE).exp(0.5f).save(writer, id());
		furnace(def(WHITE_CHOCOLATE_STONE_TILE)).ingredient(WHITE_CHOCOLATE_STONE).exp(0.5f).save(writer, id());
		of3x3(def(CANDY_CANE_BLOCK), 1, of(have(CANDY_CANE))).unlockedBy(hasn(), has()).save(writer, id());
		
		furnace(def(TRAMPOJELLY)).ingredient(BT_ORE_JELLY.it()).exp(2f).save(writer, id());
		factory(def(RED_TRAMPOJELLY)).ingredient(TRAMPOJELLY).save(writer, id());
		furnace(def(JELLY_SHOCK_ABSORBER)).ingredient(RED_TRAMPOJELLY).exp(2f).save(writer, id());
		shapeless(BUILDING_BLOCKS, def(SENSITIVE_JELLY)).requires(REDSTONE_WIRE).requires(have(TRAMPOJELLY)).unlockedBy(hasn(), has()).save(writer, id());
		
		furnace(def(LICORICE)).ingredient(BT_ORE_LICORICE.it()).exp(2f).save(writer, id());
		of2x2(def(LICORICE_BRICKS), 4, of(have(LICORICE_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		
		furnace(def(HONEYCOMB)).ingredient(BT_ORE_HONEYCOMB.it()).exp(4f).save(writer, id());
		of3x3(def(HONEYCOMB), 1, of(have(HONEYCOMB_SHARD))).unlockedBy(hasn(), has()).save(writer, id());
		of3x3(def(HONEYCOMB_BLOCK), 1, of(have(HONEYCOMB))).unlockedBy(hasn(), has()).save(writer, id());
		
		furnace(def(PEZ)).ingredient(BT_ORE_PEZ.it()).exp(6f).save(writer, id());
		furnace(def(PEZ)).ingredient(PEZ_DUST).save(writer, id());
		factory(def(PEZ_DUST)).ingredient(BT_ORE_PEZ.it()).save(writer, id());
		of3x3(def(PEZ_BLOCK), 1, of(have(PEZ))).unlockedBy(hasn(), has()).save(writer, id());
		
		factory(def(NOUGAT_POWDER)).ingredient(BT_ORE_NOUGAT.it()).save(writer, id());
		of3x3(def(NOUGAT_BLOCK), 1, of(have(NOUGAT_POWDER))).unlockedBy(hasn(), has()).save(writer, id());
		factory(def(NOUGAT_HEAD)).ingredient(NOUGAT_BLOCK).advanced().save(writer, id());
		
		shaped(BUILDING_BLOCKS, def(HONEYCOMB_LAMP))
				.pattern("###").define('#', have(HONEYCOMB))
				.pattern("#/#").define('/', HONEYCOMB_TORCH_ITEM)
				.pattern("###")
				.unlockedBy(hasn(), has()).save(writer, id());
		of2x2(def(MARSHMALLOW_CRAFTING_TABLE), 1, of(have(BT_MARSHMALLOW_PLANKS.it()))).unlockedBy(hasn(), has()).save(writer, id());
		shaped(DECORATIONS, def(CHOCOLATE_FURNACE))
				.pattern("###")
				.pattern("# #").define('#', have(CHOCOLATE_COBBLESTONE))
				.pattern("###")
				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(DECORATIONS, def(WHITE_CHOCOLATE_FURNACE))
				.pattern("###")
				.pattern("# #").define('#', have(WHITE_CHOCOLATE_COBBLESTONE))
				.pattern("###")
				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(DECORATIONS, def(SUGAR_FACTORY))
				.pattern("#%#").define('#', have(LICORICE))
				.pattern("%$%").define('%', MARSHMALLOW_STICK)
				.pattern("#%#").define('$', CANDY_CANE_WALL)
				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(DECORATIONS, def(ADVANCED_SUGAR_FACTORY))
				.pattern("#%#").define('#', RED_TRAMPOJELLY)
				.pattern("%$%").define('%', CANDIED_CHERRY)
				.pattern("#%#").define('$', have(SUGAR_FACTORY))
				.unlockedBy(hasn(), has()).save(writer, id());
		
		fence(def(MARSHMALLOW_FENCE), 6, of(MARSHMALLOW_STICK), of(have(MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		fence(def(DARK_MARSHMALLOW_FENCE), 6, of(MARSHMALLOW_STICK), of(have(DARK_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		fence(def(LIGHT_MARSHMALLOW_FENCE), 6, of(MARSHMALLOW_STICK), of(have(LIGHT_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		fence(def(CANDY_CANE_FENCE), 6, of(CANDY_CANE), of(have(CANDY_CANE_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		
		fenceGate(def(MARSHMALLOW_FENCE_GATE), 1, of(MARSHMALLOW_STICK), of(have(MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		fenceGate(def(DARK_MARSHMALLOW_FENCE_GATE), 1, of(MARSHMALLOW_STICK), of(have(DARK_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		fenceGate(def(LIGHT_MARSHMALLOW_FENCE_GATE), 1, of(MARSHMALLOW_STICK), of(have(LIGHT_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		
		of3x2(def(CANDY_CANE_WALL), 6, of(have(CANDY_CANE_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		of3x2(def(LICORICE_WALL), 6, of(have(LICORICE_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		of3x2(def(LICORICE_BRICK_WALL), 6, of(have(LICORICE_BRICK_WALL))).unlockedBy(hasn(), has()).save(writer, id());
		
		of3x1(def(MINT_SLAB), 6, of(have(MINT_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(RASPBERRY_SLAB), 6, of(have(RASPBERRY_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(BANANA_SEAWEEDS_SLAB), 6, of(have(BANANA_SEAWEEDS_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(COTTON_CANDY_SLAB), 6, of(have(COTTON_CANDY_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(MARSHMALLOW_SLAB), 6, of(have(MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(DARK_MARSHMALLOW_SLAB), 6, of(have(DARK_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(LIGHT_MARSHMALLOW_SLAB), 6, of(have(LIGHT_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(CANDY_CANE_SLAB), 6, of(have(CANDY_CANE_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(LICORICE_SLAB), 6, of(have(LICORICE_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(LICORICE_BRICK_SLAB), 6, of(have(LICORICE_BRICKS))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(ICE_CREAM_SLAB), 6, of(have(ICE_CREAM))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(BLUEBERRY_ICE_CREAM_SLAB), 6, of(have(BLUEBERRY_ICE_CREAM))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(MINT_ICE_CREAM_SLAB), 6, of(have(MINT_ICE_CREAM))).unlockedBy(hasn(), has()).save(writer, id());
		of3x1(def(STRAWBERRY_ICE_CREAM_SLAB), 6, of(have(STRAWBERRY_ICE_CREAM))).unlockedBy(hasn(), has()).save(writer, id());
		
		stair(def(MINT_STAIRS), 4, of(have(MINT_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(RASPBERRY_STAIRS), 4, of(have(RASPBERRY_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(BANANA_SEAWEEDS_STAIRS), 4, of(have(BANANA_SEAWEEDS_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(COTTON_CANDY_STAIRS), 4, of(have(COTTON_CANDY_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(MARSHMALLOW_STAIRS), 4, of(have(MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(DARK_MARSHMALLOW_STAIRS), 4, of(have(DARK_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(LIGHT_MARSHMALLOW_STAIRS), 4, of(have(LIGHT_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(CANDY_CANE_STAIRS), 4, of(have(CANDY_CANE_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(LICORICE_STAIRS), 4, of(have(LICORICE_BLOCK))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(LICORICE_BRICK_STAIRS), 4, of(have(LICORICE_BRICKS))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(ICE_CREAM_STAIRS), 4, of(have(ICE_CREAM))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(BLUEBERRY_ICE_CREAM_STAIRS), 4, of(have(BLUEBERRY_ICE_CREAM))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(MINT_ICE_CREAM_STAIRS), 4, of(have(MINT_ICE_CREAM))).unlockedBy(hasn(), has()).save(writer, id());
		stair(def(STRAWBERRY_ICE_CREAM_STAIRS), 4, of(have(STRAWBERRY_ICE_CREAM))).unlockedBy(hasn(), has()).save(writer, id());
		
		door(def(MARSHMALLOW_DOOR), 3, of(have(MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		door(def(LIGHT_MARSHMALLOW_DOOR), 3, of(have(LIGHT_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		door(def(DARK_MARSHMALLOW_DOOR), 3, of(have(DARK_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		
		of3x2(def(MARSHMALLOW_TRAPDOOR), 2, of(have(MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		of3x2(def(LIGHT_MARSHMALLOW_TRAPDOOR), 2, of(have(LIGHT_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		of3x2(def(DARK_MARSHMALLOW_TRAPDOOR), 2, of(have(DARK_MARSHMALLOW_PLANKS))).unlockedBy(hasn(), has()).save(writer, id());
		
		shaped(DECORATIONS, def(MARSHMALLOW_LADDER), 3)
				.pattern("/ /")
				.pattern("///").define('/', have(MARSHMALLOW_STICK))
				.pattern("/ /")
				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(DECORATIONS, def(SUGAR_SPIKES), 1)
				.pattern(" * ")
				.pattern("* *").define('*', have(SUGAR_CRYSTAL))
				.unlockedBy(hasn(), has()).save(writer, id());
		shaped(DECORATIONS, def(CRANBERRY_SPIKES), 1)
				.pattern(" * ")
				.pattern("* *").define('*', have(CRANFISH_SCALE))
				.unlockedBy(hasn(), has()).save(writer, id());
		
		furnace(def(CARAMEL_GLASS)).ingredient(of(CARAMEL_BLOCK)).exp(0.5f).save(writer, id());
		furnace(def(CARAMEL_GLASS)).ingredient(of(ROUND_CARAMEL_GLASS)).exp(0.5f).save(writer, id());
		furnace(def(CARAMEL_GLASS)).ingredient(of(DIAMOND_CARAMEL_GLASS)).exp(0.5f).save(writer, id());
		
		shapeless(BUILDING_BLOCKS, def(ROUND_CARAMEL_GLASS)).requires(have(CARAMEL_GLASS)).unlockedBy(hasn(), has()).save(writer, id());
		shapeless(BUILDING_BLOCKS, def(DIAMOND_CARAMEL_GLASS)).requires(have(ROUND_CARAMEL_GLASS)).unlockedBy(hasn(), has()).save(writer, id());
		
		of3x2(def(CARAMEL_GLASS_PANE), 16, of(have(CARAMEL_GLASS))).unlockedBy(hasn(), has()).save(writer, id());
		of3x2(def(ROUND_CARAMEL_GLASS_PANE), 16, of(have(ROUND_CARAMEL_GLASS))).unlockedBy(hasn(), has()).save(writer, id());
		of3x2(def(DIAMOND_CARAMEL_GLASS_PANE), 16, of(have(DIAMOND_CARAMEL_GLASS))).unlockedBy(hasn(), has()).save(writer, id());
		
		factory(def(HONEYCOMB_SHARD), 1).ingredient(of(FRAISE_TAGADA_FLOWER)).save(writer, id());
		factory(def(GOLD_NUGGET), 1).ingredient(of(GOLDEN_SUGAR_FLOWER)).save(writer, id());
		
		factory(def(MAGICAL_LEAF), 2).ingredient(of(MAGICAL_LEAVES)).save(writer, id());
		factory(def(CHOCOLATE_LEAF), 2).ingredient(of(CHOCOLATE_LEAVES)).save(writer, id());
		factory(def(WHITE_CHOCOLATE_LEAF), 2).ingredient(of(WHITE_CHOCOLATE_LEAVES)).save(writer, id());
		factory(def(CARAMEL_LEAF), 2).ingredient(of(CARAMEL_LEAVES)).save(writer, id());
		factory(def(CANDIED_CHERRY_LEAF), 2).ingredient(of(CANDIED_CHERRY_LEAVES)).save(writer, id());
	}
	
	
	//============================================
	
	private ShapedRecipeBuilder stair(ItemLike result, int cnt, Ingredient material) {
		return shaped(BUILDING_BLOCKS, result, cnt)
				.pattern("#  ")
				.pattern("## ").define('#', material)
				.pattern("###");
	}
	
	private ShapedRecipeBuilder door(ItemLike result, int cnt, Ingredient ingredient) {
		return shaped(BUILDING_BLOCKS, result, cnt)
				.pattern("## ")
				.pattern("## ").define('#', ingredient)
				.pattern("## ");
	}
	
	private ShapedRecipeBuilder of3x3(ItemLike result, int cnt, Ingredient ingredient) {
		return shaped(BUILDING_BLOCKS, result, cnt)
				.pattern("###")
				.pattern("###").define('#', ingredient)
				.pattern("###");
	}
	
	private ShapedRecipeBuilder fence(ItemLike result, int cnt, Ingredient stick, Ingredient material) {
		return shaped(BUILDING_BLOCKS, result, cnt)
				.pattern("#/#").define('#', material)
				.pattern("#/#").define('/', stick);
	}
	
	private ShapedRecipeBuilder fenceGate(ItemLike result, int cnt, Ingredient stick, Ingredient material) {
		return shaped(BUILDING_BLOCKS, result, cnt)
				.pattern("/#/").define('#', material)
				.pattern("/#/").define('/', stick);
	}
	
	private ShapedRecipeBuilder of3x1(ItemLike result, int cnt, Ingredient ingredient) {
		return shaped(BUILDING_BLOCKS, result, cnt)
				.pattern("###").define('#', ingredient);
	}
	
	private ShapedRecipeBuilder of3x2(ItemLike result, int cnt, Ingredient ingredient) {
		return shaped(BUILDING_BLOCKS, result, cnt)
				.pattern("###").define('#', ingredient)
				.pattern("###");
	}
	
	private ShapedRecipeBuilder of2x2(ItemLike result, int cnt, Ingredient ingredient) {
		return shaped(BUILDING_BLOCKS, result, cnt)
				.pattern("## ")
				.pattern("## ").define('#', ingredient);
	}
	
	private @NotNull List<ShapedRecipeBuilder> tools(ItemLike sword, ItemLike shovel, ItemLike pickaxe, ItemLike axe, ItemLike hoe,
													 Ingredient stick, Ingredient material) {
		return asList(
				sword != null ? shaped(COMBAT, sword)
						.pattern(" @ ").define('@', material)
						.pattern(" @ ").define('/', stick)
						.pattern(" / ") : null,
				shovel != null ? shaped(TOOLS, shovel)
						.pattern(" @ ").define('@', material)
						.pattern(" / ").define('/', stick)
						.pattern(" / ") : null,
				pickaxe != null ? shaped(TOOLS, pickaxe)
						.pattern("@@@").define('@', material)
						.pattern(" / ").define('/', stick)
						.pattern(" / ") : null,
				axe != null ? shaped(TOOLS, axe)
						.pattern("@@ ").define('@', material)
						.pattern("@/ ").define('/', stick)
						.pattern(" / ") : null,
				hoe != null ? shaped(TOOLS, hoe)
						.pattern("@@ ").define('@', material)
						.pattern(" / ").define('/', stick)
						.pattern(" / ") : null
		);
	}
	
	private @NotNull List<ShapedRecipeBuilder> armors(ItemLike helmet, ItemLike chest, ItemLike legs, ItemLike boots,
													  Ingredient material) {
		return asList(
				helmet != null ? shaped(COMBAT, helmet)
						.pattern("@@@").define('@', material)
						.pattern("@ @") : null,
				chest != null ? shaped(COMBAT, chest)
						.pattern("@ @").define('@', material)
						.pattern("@@@")
						.pattern("@@@") : null,
				legs != null ? shaped(COMBAT, legs)
						.pattern("@@@").define('@', material)
						.pattern("@ @")
						.pattern("@ @") : null,
				boots != null ? shaped(COMBAT, boots)
						.pattern("@ @").define('@', material)
						.pattern("@ @") : null
		);
	}
	
	//============================================
	private String _hasK;
	private Criterion<?> _hasV;
	private int _used = 0;
	private final HashMap<String, Criterion<?>> _haves = new HashMap<>();
	
	private <T> T have(T t) {
		if (_hasK != null) {
			if (_used >= 0 && _used != 2) {
				throw new RuntimeException("previous is unused with used " + _used + " times and expected 2");
			} else _used = 0;
		}
		_hasK = _hasn2(t);
		_hasV = _haves.computeIfAbsent(_hasK, i -> _has2(t));
		return t;
	}
	
	private void hasrst() {
		_used = 0;
	}
	
	private String hasn() {
		CommonUtils.check(_used++ < 2, "abused");
		return _hasK;
	}
	
	private Criterion<?> has() {
		CommonUtils.check(_used++ < 2, "abused");
		return _hasV;
	}
	
	private String hasn(ItemLike il) {
		return "has_" + BuiltInRegistries.ITEM.getKey(il.asItem()).getPath();
	}
	
	private String hasn(TagKey<?> tk) {
		return "has_" + tk.location().getPath();
	}
	
	private String _hasn2(Object o) {
		if (o instanceof ItemLike i) return hasn(i);
		if (o instanceof TagKey<?> t) return hasn(t);
		throw new UnsupportedOperationException(o.getClass() + " is undefined");
	}
	
	@SuppressWarnings("unchecked")
	private Criterion<?> _has2(Object o) {
		if (o instanceof ItemLike i) return has(i);
		if (o instanceof TagKey<?> t) return has((TagKey<Item>) t);
		throw new UnsupportedOperationException(o.getClass() + " is undefined");
	}
	
	//NameResolver
	private final HashSet<ResourceLocation> _nameCache = new HashSet<>();
	private ItemLike _now;
	
	private <T extends ItemLike> T def(T t) {
		CommonUtils.check(_now == null, "previous item is unused");
		_now = t;
		return t;
	}
	
	private ResourceLocation id() {
		Objects.requireNonNull(_now, "undefined");
		final ResourceLocation key = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(_now.asItem()));
		ResourceLocation recipeId = key;
		for (int i = 1; _nameCache.contains(recipeId); i++) {
			recipeId = key.withSuffix(String.valueOf(i));
		}
		_nameCache.add(recipeId);
		_now = null;
		return recipeId;
	}
	
}
