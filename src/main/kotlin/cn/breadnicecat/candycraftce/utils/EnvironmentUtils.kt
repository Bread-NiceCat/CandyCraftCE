package cn.breadnicecat.candycraftce.utils

import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level

val isClient by lazy { FabricLoader.getInstance().environmentType == EnvType.CLIENT }
inline fun <R> ifClient(block: () -> R): R? {
    return if (isClient) block() else null
}

inline fun Level.ifClient(block: (ClientLevel) -> Unit): Level {
    if (this is ClientLevel) {
        block(this)
    }
    return this
}

inline fun <R> ifServer(block: () -> R): R? {
    return if (!isClient) block() else null
}

inline fun Level.ifServer(block: (ServerLevel) -> Unit): Level {
    if (this is ServerLevel) {
        block(this)
    }
    return this
}

val isDevelopmentEnvironment by lazy { FabricLoader.getInstance().isDevelopmentEnvironment }
inline fun <R> ifDev(block: () -> R): R? {
    return if (isDevelopmentEnvironment) block() else null
}

inline fun ifLoaded(modId: String, block: () -> Unit) {
    if (isLoaded(modId)) block()
}

fun isLoaded(modId: String): Boolean = FabricLoader.getInstance().isModLoaded(modId)