package cn.breadnicecat.candycraftce.level.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;

public class DungeonData extends SavedData {

    private static final String IDENTIFIER = "candy_dungeons_data";
    private int dungeonsSize;
    private final ServerLevel server;
    private static Map<Level, DungeonData> dataMap = new HashMap<>();


    public DungeonData(ServerLevel p_300199_) {
        super();
        this.server = p_300199_;
    }

    public static DungeonData get(Level world) {
        if (world instanceof ServerLevel serverLevel) {
            ServerLevel level = world.getServer().getLevel(world.dimension());
            DungeonData fromMap = dataMap.get(level);
            if (fromMap == null) {
                DimensionDataStorage storage = level.getDataStorage();
                DungeonData data = storage.computeIfAbsent(DungeonData.factory(serverLevel), IDENTIFIER);
                if (data != null) {
                    data.setDirty();
                }
                dataMap.put(world, data);
                return data;
            }
            return fromMap;
        }
        return null;
    }

    public static SavedData.Factory<DungeonData> factory(ServerLevel p_300199_) {
        return new SavedData.Factory<>(() -> {
            return new DungeonData(p_300199_);
        }, (p_296865_, provider) -> {
            return load(p_300199_, p_296865_);
        }, DataFixTypes.SAVED_DATA_RAIDS);
    }

    public static DungeonData load(ServerLevel p_300199_, CompoundTag nbt) {
        DungeonData data = new DungeonData(p_300199_);
        if (nbt.contains("DungeonSize", 99)) {
            data.dungeonsSize = nbt.getInt("DungeonSize");
        }
        return data;
    }

    public int getDungeonsSize() {
        return dungeonsSize;
    }

    public void increaseDungeonsSize() {
        this.dungeonsSize++;
    }

    @Override
    public CompoundTag save(CompoundTag compound, HolderLookup.Provider p_323640_) {
        compound.putInt("DungeonSize", this.dungeonsSize);
        return compound;
    }
}