package cn.breadnicecat.candycraftce.block.blockentity.entities;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.level.data.DungeonData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
        blockPos = NbtUtils.readBlockPos(tag, "TeleportTarget").orElse(null);
        this.generated = tag.getBoolean("Teleported");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (blockPos != null) {
            tag.put("TeleportTarget", NbtUtils.writeBlockPos(blockPos));
        }
        tag.putBoolean("Teleported", this.generated);
    }

    public BlockPos findDungeons(ServerLevel level) {
        if (this.blockPos == null) {
            DungeonData data = DungeonData.get(level);

            BlockPos zeroPos = new BlockPos(data.getDungeonsSize() * 32 * 16, 64, 0);
            this.blockPos = zeroPos;
            this.setChanged();
            data.increaseDungeonsSize();
            return zeroPos;
        } else {
            return this.blockPos;
        }
    }
}
