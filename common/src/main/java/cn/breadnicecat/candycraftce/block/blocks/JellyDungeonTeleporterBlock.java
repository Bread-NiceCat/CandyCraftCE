package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.blockentity.entities.JellyDungeonTeleporterBE;
import cn.breadnicecat.candycraftce.level.generator.JellyDungeonGenerator;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static cn.breadnicecat.candycraftce.level.CDims.CANDYLAND;
import static cn.breadnicecat.candycraftce.level.CDims.DUNGEONS;

/**
 * Created in 2024/9/16 9:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class JellyDungeonTeleporterBlock extends BaseEntityBlock {
    private static final MapCodec<JellyDungeonTeleporterBlock> CODEC = simpleCodec(JellyDungeonTeleporterBlock::new);

    private static final VoxelShape DEFAULT = Shapes.empty();

    public JellyDungeonTeleporterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isAlive() && !player.isPassenger() && !player.isVehicle()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof JellyDungeonTeleporterBE jellyDungeonTeleporterBE && level instanceof ServerLevel serverLevel) {
                MinecraftServer server = serverLevel.getServer();
                ServerLevel dungeonLevel = server.getLevel(DUNGEONS);
                BlockPos dungeonPos = jellyDungeonTeleporterBE.findDungeons(dungeonLevel, pos.mutable().set(pos.getX(), JellyDungeonGenerator.GENERATED_Y, pos.getZ()).immutable());

                if (!jellyDungeonTeleporterBE.generated) {
                    jellyDungeonTeleporterBE.generated = true;
                    JellyDungeonGenerator jellyDungeonGenerator = new JellyDungeonGenerator();
                    jellyDungeonGenerator.generate(dungeonLevel, dungeonPos.below());
                }
                //传送
                DimensionTransition destination = getDestination(level, dungeonPos, player);
                if (destination != null && player.canChangeDimensions(level, destination.newLevel())) {

                    player.changeDimension(destination);
                    return ItemInteractionResult.SUCCESS;
                }
            }

        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    /**
     * @return null, 如果无法传送
     */
    protected @Nullable DimensionTransition getDestination(Level level, BlockPos pos, Entity entity) {
        MinecraftServer server = level.getServer();
        if (server == null) return null;

        ResourceKey<Level> ori = level.dimension();
        if (ori == DUNGEONS) {
            ServerLevel candyworld = server.getLevel(CANDYLAND);
            BlockPos.MutableBlockPos dest = pos.mutable();
            int xOff = 0, zOff = 0;
            do {
                switch (level.random.nextInt(0, 2)) {
                    case 0 -> xOff++;
                    case 1 -> zOff++;
                }
                dest.move(xOff, 0, zOff);
                int y = candyworld.getChunk(dest).getHeight(Heightmap.Types.MOTION_BLOCKING, dest.getX() + xOff, dest.getZ() + zOff) + 1;
                dest.setY(y);
            } while (candyworld.getBlockState(dest).is(this) || dest.getY() <= level.getMinBuildHeight());

            return new DimensionTransition(candyworld, dest.getCenter(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), (e) -> {
            });

        } else {
            Vec3 dest = pos.getCenter();
            ServerLevel ccl = server.getLevel(DUNGEONS);

            return new DimensionTransition(ccl, dest, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), (e) -> {
                if (e instanceof LivingEntity li) {
                }
            });
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new JellyDungeonTeleporterBE(pos, state);
    }

}
