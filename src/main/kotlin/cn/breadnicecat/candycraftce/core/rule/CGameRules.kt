package cn.breadnicecat.candycraftce.core.rule

import cn.breadnicecat.candycraftce.utils.CUtils
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory.createBooleanRule
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
import net.minecraft.world.level.GameRules

/**
 * Created by NiceCat on 2026/2/26.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object CGameRules {
    init {
        CUtils.sign()

    }

    val doCaramelPortalWorks: GameRules.Key<GameRules.BooleanValue> = GameRuleRegistry.register(
        "doCaramelPortalWorks",
        GameRules.Category.SPAWNING,
        createBooleanRule(true)
    )
}