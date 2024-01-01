package cn.breadnicecat.candycraftce.misc.forge;

import net.minecraft.world.level.GameRules;

/**
 * Created in 2023/12/31 10:48
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CGameRulesImpl {
	public static <T extends GameRules.Value<T>> GameRules.Key<T> register(String name, GameRules.Category category, GameRules.Type<T> type) {
		return GameRules.register(name, category, type);
	}
}
