package cn.breadnicecat.candycraftce.network;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import dev.architectury.networking.NetworkManager;
import org.slf4j.Logger;

/**
 * Created in 2025/2/5 12:42
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CNetworkMgr {
	
	private static final Logger log = CLogUtils.sign();
	
	public static void init() {
		NetworkManager.registerS2CPayloadType(BossBarPayload.TYPE, BossBarPayload.STREAM_CODEC);
	}
}
