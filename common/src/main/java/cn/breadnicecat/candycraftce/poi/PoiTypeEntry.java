package cn.breadnicecat.candycraftce.poi;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.function.Supplier;

public class PoiTypeEntry<T extends PoiType> extends SimpleEntry<PoiType, T> {
    public PoiTypeEntry(ResourceKey<PoiType> key, Supplier<T> getter) {
        super(key, getter);
    }

    public PoiTypeEntry(Pair<ResourceKey<PoiType>, Supplier<T>> wrapper) {
        super(wrapper);
    }
}
