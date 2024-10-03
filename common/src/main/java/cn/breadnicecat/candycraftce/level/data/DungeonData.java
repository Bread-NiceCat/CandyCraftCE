package cn.breadnicecat.candycraftce.level.data;

import cn.breadnicecat.candycraftce.level.CDims;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DungeonData extends SavedData {
	
	public static final String IDENTIFIER = "candy_dungeons_data";
	public static final String COUNT_KEY = "dungeons_count";
	public static final Factory<DungeonData> DATA_FACTORY = new SavedData.Factory<>(
			DungeonData::new,
			DungeonData::load,
			DataFixTypes.SAVED_DATA_RAIDS);
	
	private int dungeonCount;
	
	public DungeonData() {
	}
	
	public static @NotNull DungeonData get(MinecraftServer server) {
		ServerLevel level = Objects.requireNonNull(server.getLevel(CDims.DUNGEONS), "Unable to access dim-dungeons.");
		DimensionDataStorage storage = level.getDataStorage();
		DungeonData data = storage.computeIfAbsent(DATA_FACTORY, IDENTIFIER);
		return data;
	}
	
	public static @NotNull DungeonData load(CompoundTag nbt, HolderLookup.Provider provider) {
		DungeonData data = new DungeonData();
		if (nbt.contains(COUNT_KEY, CompoundTag.TAG_INT)) data.dungeonCount = nbt.getInt(COUNT_KEY);
		return data;
	}
	
	@Override
	public @NotNull CompoundTag save(CompoundTag compound, HolderLookup.Provider provider) {
		compound.putInt(COUNT_KEY, this.dungeonCount);
		return compound;
	}
	
	public int getDungeonCount() {
		return dungeonCount;
	}
	
	public void increaseDungeonCount() {
		this.dungeonCount++;
		setDirty();
	}
}