package cn.breadnicecat.candycraftce.network;

import cn.breadnicecat.candycraftce.entity.entities.IBoss;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2025/2/5 12:25
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class BossBarPayload implements CustomPacketPayload {
	public static final Type<BossBarPayload> TYPE = new Type<>(prefix("boss_bar"));
	public static final StreamCodec<RegistryFriendlyByteBuf, BossBarPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT,
			p -> p.barType.getTier(),
			ByteBufCodecs.VAR_INT,
			p -> p.entityID,
			BossBarPayload::new
	);
	private final IBoss.BarType barType;
	private final int entityID;
	
	public BossBarPayload(int barTier, int entityID) {
		this.barType = IBoss.BarType.byTier(barTier);
		this.entityID = entityID;
	}
	
	public BossBarPayload(IBoss.BarType barType, Entity entity) {
		this.barType = barType;
		this.entityID = entity.getId();
	}
	
	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	
}
