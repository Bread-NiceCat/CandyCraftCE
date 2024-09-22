package cn.breadnicecat.candycraftce.poi;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import cn.breadnicecat.candycraftce.utils.WrappedEntry;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PoiTypeEntry<T extends PoiType> extends SimpleEntry<PoiType, T> {
	
	public PoiTypeEntry(WrappedEntry<PoiType, T> wrapper) {
		super(wrapper);
	}
}
