package cn.breadnicecat.candycraftce.block.blockentity.entities;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.poi.CPoiTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class JellyDungeonTeleporterBE extends BlockEntity {
    public BlockPos blockPos;
    public boolean generated;

    protected JellyDungeonTeleporterBE(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public JellyDungeonTeleporterBE(BlockPos blockPos, BlockState blockState) {
        this(CBlockEntities.JELLY_DUNGEON_TELEPORTER_BE.get(), blockPos, blockState);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (blockPos != null) {
            tag.put("TeleportTarget", NbtUtils.writeBlockPos(blockPos));
        }
        tag.putBoolean("Teleported", this.generated);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        blockPos = NbtUtils.readBlockPos(tag, "TeleportTarget").orElse(null);
        this.generated = tag.getBoolean("Teleported");
    }

    public BlockPos findDungeons(ServerLevel level, BlockPos blockPos) {
        if (this.blockPos == null) {
            Optional<BlockPos> optional = level.getPoiManager().findClosest(poiTypeHolder -> {
                return poiTypeHolder.value() == CPoiTypes.JELLY_DUNGEON_TELEPORTER.get();
            }, blockPos, 128, PoiManager.Occupancy.ANY);
            if (optional.isPresent()) {
                //If found nearest teleporter. set and return pos
                this.blockPos = optional.get();
                return optional.get();
            }
            this.blockPos = blockPos;
            return blockPos;
        } else {
            return this.blockPos;
        }
    }
}
