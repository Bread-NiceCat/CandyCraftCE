package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.level.CDims;
import cn.breadnicecat.candycraftce.misc.muitlblocks.VectorPortalShape;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static cn.breadnicecat.candycraftce.block.CBlocks.CARAMEL_BLOCK;
import static cn.breadnicecat.candycraftce.block.blocks.CaramelPortalBlock.CONFIG;
import static cn.breadnicecat.candycraftce.block.blocks.CaramelPortalBlock.PLACER;
import static cn.breadnicecat.candycraftce.utils.LevelUtils.getNeighbourPos;

/**
 * Created in 2023/12/30 21:30
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class SugarBlock extends FallingBlock {

	public SugarBlock(Properties properties) {
		super(properties);
	}


	@SuppressWarnings("deprecation")
	@Override
	public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		tryBuild(level, pos);
		return super.use(state, level, pos, player, hand, hit);
	}

	/**
	 * 糖块变成焦糖块并检测附近是否有传送门
	 */
	public boolean tryHeatSugar(BlockState state, Level level, BlockPos pos) {
		boolean isSuccessful = false;
		if (state.getBlock() == this) {
			isSuccessful = level.setBlock(pos, CARAMEL_BLOCK.defaultBlockState(), 2 | 16);
			if (isSuccessful && isPortalLightingEnabled(level)) {
				//检测附近是否有岩浆
				for (BlockPos moved : getNeighbourPos(pos)) {
					if (level.getBlockState(moved).is(Blocks.LAVA)) {
						if (tryBuild(level, pos)) {
							level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS);
						}
						break;
					}
				}
			}
		}
		return isSuccessful;
	}

	/**
	 * @param pos 任意一个有效框架
	 */
	public boolean tryBuild(Level level, BlockPos pos) {
		if (isPortalLightingEnabled(level)) {
			Optional<VectorPortalShape> opt = VectorPortalShape.findPortalOnFrame(level, pos, CONFIG);
			if (opt.isPresent()) {
				VectorPortalShape shape = opt.get();
				shape.getAllFrames().forEach((p) -> tryHeatSugar(level.getBlockState(p), level, p));
				return shape.build(level, PLACER);
			}
		}
		return false;
	}


	public boolean isPortalLightingEnabled(Level level) {
		return level.dimension() == Level.OVERWORLD || level.dimension() == CDims.CANDYLAND;
	}
}
