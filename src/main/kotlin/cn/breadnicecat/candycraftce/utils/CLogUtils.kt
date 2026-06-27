package cn.breadnicecat.candycraftce.utils

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by NiceCat on 2026/5/1.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object CLogUtils {

    val walker: StackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)

    private val logCache = mutableMapOf<String, Logger>()
    val mainLog = modLogger("Core")
    val registerLog = modLogger("Registry")
    val debugLog = modLogger("Debug")
    val clog: Logger
        get() = modLogger(walker.callerClass.let {
            if (it.kotlin.isCompanion) it.enclosingClass else it
        }.simpleName)

    fun modLogger(tag: String): Logger {
        return logCache.computeIfAbsent(tag) { LoggerFactory.getLogger("CandyCraftCE|${it}") }
    }

    private val signed = mutableSetOf<Class<*>>()
    private var late = false
    fun sign() {
        val clazz = walker.callerClass
        require(signed.add(clazz)) { "Class ${clazz.simpleName} is already registered" }
        mainLog.info("${clazz.simpleName} loaded")
        if (late) {
            mainLog.error("This class is late for sign! It is a bug, please report it!")
        }
    }

    internal fun markLateForSign() {
        late = true
    }

    fun logRegister(type: String, id: ResourceLocation) {
        registerLog.info("Registering $type/$id")
    }
}
