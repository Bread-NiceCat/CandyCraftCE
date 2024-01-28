package cn.breadnicecat.candycraftce.registration.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.registration.block.blocks.CaramelPortal;
import cn.breadnicecat.candycraftce.registration.block.blocks.CustardPudding;
import cn.breadnicecat.candycraftce.registration.block.blocks.PuddingFarm;
import cn.breadnicecat.candycraftce.registration.block.blocks.SugarBlock;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static cn.breadnicecat.candycraftce.registration.block.CBlockBuilder.create;
import static cn.breadnicecat.candycraftce.registration.sound.CSoundTypes.JELLY_FOOTSTEP;


/**
 * Created in 2023/11/26 9:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlocks {

	private static final Logger LOGGER = CLogUtils.sign();

	static {
		CandyCraftCE.hookMinecraftSetup(() -> BLOCKS = Collections.unmodifiableMap(CBlocks.BLOCKS));
	}

	public static Map<ResourceLocation, BlockEntry<? extends Block>> BLOCKS = new HashMap<>();

//HELPER.single(CARAMEL_BLOCK, () -> new BlockSugar(Properties.copy(Blocks.STONE)), CCBlockManager::simpleBlockItem,
//        MODEL_SIMPLE, VTAG_MINEABLE_WITH_PICKAXE, CANDYLAND_PORTAL_FRAME.getGroupId(), LOOT_DROP_SELF);
//HELPER.single(CARAMEL_PORTAL, BlockCandylandPortal::new,
//        VTAG_PORTALS, LOOT_NONE, RENDERER_TYPE_TRANSLUCENT);

//HELPER.single(CHOCOLATE_STONE, () -> new Block(Properties.copy(Blocks.STONE)), CCBlockManager::simpleBlockItem,
//        MODEL_SIMPLE, VTAG_MINEABLE_WITH_PICKAXE);
//HELPER.single(CHOCOLATE_COBBLESTONE, () -> new Block(Properties.copy(Blocks.COBBLESTONE)), CCBlockManager::simpleBlockItem,
//        MODEL_SIMPLE, LOOT_DROP_SELF, VTAG_MINEABLE_WITH_PICKAXE);

	public static final BlockEntry<SugarBlock> SUGAR_BLOCK = create("sugar_block", SugarBlock::new).setProperties(Blocks.SAND, Properties::randomTicks).save();
	public static final BlockEntry<Block> CARAMEL_BLOCK = create("caramel_block").setProperties(Blocks.STONE).save();
	public static final BlockEntry<CaramelPortal> CARAMEL_PORTAL = create("caramel_portal", CaramelPortal::new).setProperties(Blocks.NETHER_PORTAL).save();

	public static final BlockEntry<Block> CHOCOLATE_STONE = create("chocolate_stone").setProperties(Blocks.STONE).save();
	public static final BlockEntry<Block> CHOCOLATE_COBBLESTONE = create("chocolate_cobblestone").setProperties(Blocks.COBBLESTONE).save();

	public static final BlockEntry<Block> PUDDING = create("pudding").setProperties(Blocks.DIRT).save();
	public static final BlockEntry<CustardPudding> CUSTARD_PUDDING = create("custard_pudding", CustardPudding::new).setProperties(Blocks.GRASS_BLOCK, p -> p.sound(JELLY_FOOTSTEP)).save();
	public static final BlockEntry<PuddingFarm> PUDDING_FARMLAND = create("pudding_farmland", PuddingFarm::new).setProperties(Blocks.FARMLAND, p -> p.sound(JELLY_FOOTSTEP)).save();


	public static void init() {
		LOGGER.info("init");
	}
}
