package cn.breadnicecat.candycraftce.poi;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PoiTypeEntry<T extends PoiType> extends SimpleEntry<PoiType, T> {
	
	public PoiTypeEntry(SimpleEntry<PoiType, T> wrapper) {
		super(wrapper);
	}
}
