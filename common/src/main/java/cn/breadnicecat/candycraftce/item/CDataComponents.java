package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.function.UnaryOperator;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/8/23 19:11
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CDataComponents {
	static {
		CLogUtils.sign();
	}
	
	public static final SimpleEntry<DataComponentType<?>, DataComponentType<CompoundTag>> NBT = register("idebug_relative_zero", (arg) -> arg.persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG));
	
	private static <T> SimpleEntry<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
		return new SimpleEntry<>(CandyCraftCE.register(BuiltInRegistries.DATA_COMPONENT_TYPE, prefix(name), () -> builder.apply(new DataComponentType.Builder<>()).build()));
	}
	
	public static void init() {
	}
}
