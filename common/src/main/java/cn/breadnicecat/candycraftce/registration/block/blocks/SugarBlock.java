package cn.breadnicecat.candycraftce.registration.block.blocks;

import cn.breadnicecat.candycraftce.level.CDims;
import cn.breadnicecat.candycraftce.misc.muitlblocks.NetherLikePortalShape;
import cn.breadnicecat.candycraftce.registration.block.CBlockTags;
import cn.breadnicecat.candycraftce.utils.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

import static cn.breadnicecat.candycraftce.registration.block.CBlocks.*;

/**
 * Created in 2023/12/30 21:30
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarBlock extends FallingBlock {

	private final Predicate<BlockState> IS_FRAME = (b) -> b.is(CBlockTags.CARAMEL_PORTAL_FRAME) || b.is(TEST_BLOCK.getBlock());
	private final Predicate<BlockState> CAN_IGNORE = blockState -> blockState.isAir() || blockState.is(CARAMEL_PORTAL.getBlock());

	public SugarBlock(Properties properties) {
		super(properties);
	}


	@NotNull
	private Optional<NetherLikePortalShape> tryFindPortal(Level level, @Nullable BlockPos portalPos) {
		if (portalPos == null) return Optional.empty();
		return NetherLikePortalShape.findPortalShape(level, portalPos, IS_FRAME, CAN_IGNORE);
	}


	@SuppressWarnings("deprecation")
	@Override
	public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if ((level.dimension().equals(Level.OVERWORLD) || level.dimension().equals(CDims.CANDYLAND))) {
			ItemStack itemInHand = player.getItemInHand(hand);
			if (itemInHand.getItem() instanceof BucketItem bucketItem && bucketItem.content == Fluids.LAVA) {
				player.setItemInHand(hand, BucketItem.getEmptySuccessItem(itemInHand, player));

				//PortalShape.findEmptyPortalShape必须传入火的pos,而参数pos是当前方块的pos
				BlockPos portalPos = LevelUtils.move(pos, hit.getDirection(), 1);
				level.setBlock(pos, CARAMEL_BLOCK.defaultBlockState(), 2 | 16);
				Optional<NetherLikePortalShape> shape = tryFindPortal(level, portalPos);
				if (shape.isPresent()) {
					shape.get().build(CARAMEL_PORTAL.getBlock().defaultBlockState());
					return InteractionResult.sidedSuccess(level.isClientSide());
				}
			}

		}
		return InteractionResult.PASS;
	}

}
