package cn.breadnicecat.candycraftce.registration.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static cn.breadnicecat.candycraftce.registration.block.CBlockBuilder.create;


/**
 * Created in 2023/11/26 9:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlocks {
	static {
		CLogUtils.sign();
		CandyCraftCE.hookMinecraftSetup(() -> BLOCKS = Collections.unmodifiableMap(CBlocks.BLOCKS));
	}

	public static Map<ResourceLocation, BlockEntry<? extends Block>> BLOCKS = new HashMap<>();
	public static final BlockEntry<Block> TEST_BLOCK = create("test").setProperties(BlockBehaviour.Properties.copy(Blocks.BEDROCK)).save();

	public static void init() {
	}
}
