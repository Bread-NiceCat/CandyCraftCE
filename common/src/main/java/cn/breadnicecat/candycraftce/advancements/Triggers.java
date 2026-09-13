package cn.breadnicecat.candycraftce.advancements;

import net.minecraft.server.level.ServerPlayer;

//common层进度触发器门面，实现由neoforge/fabric模块赋值

public final class Triggers {
	public static TriggerHook EAT_BLOCK;

	@FunctionalInterface
	public interface TriggerHook {
		void trigger(ServerPlayer player);
	}
}