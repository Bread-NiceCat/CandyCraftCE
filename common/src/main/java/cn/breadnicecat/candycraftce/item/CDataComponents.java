package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import com.mojang.serialization.Codec;
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
	
	public static final DataComponentEntry<CompoundTag> NBT = register("nbt", (arg) -> arg.persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG));
	public static final DataComponentEntry<Integer> SUGAR_BURN_TIME = register("sugar_burn_time", (arg) -> arg.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
	public static final DataComponentEntry<CompoundTag> BLOCK_TO_EAT = register("block_to_eat", (arg) -> arg.persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG));
	
	private static <T> DataComponentEntry<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
		return CandyCraftCE.register(BuiltInRegistries.DATA_COMPONENT_TYPE, prefix(name), () -> builder.apply(new DataComponentType.Builder<>()).build())
				.as(DataComponentEntry::new);
	}
	
	public static void init() {
	}
}
