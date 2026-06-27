package cn.breadnicecat.candycraftce.utils

import cn.breadnicecat.candycraftce.utils.CLogUtils.clog
import cn.breadnicecat.candycraftce.utils.CLogUtils.debugLog

typealias Operation<Receiver> = Receiver.() -> Unit

enum class QueueType {
    HEAD, NORMAL, LAST
}

abstract class OperationRecordable<Receiver> {

    internal abstract val receiver: Receiver

    private val ops = LinkedHashMap<String, Pair<QueueType, Operation<Receiver>>>()

    private val stacks = mutableMapOf<String, List<String>>()
    var executing = false
        private set
    var frozen = false
        private set

    internal fun record(
        key: String,
        overridable: Boolean = true,
        private: Boolean = false,
        queue: QueueType = QueueType.NORMAL,
        op: Operation<Receiver>,
    ) {
        //如果被嵌套了，就直接运行
        if (executing) {
            op(receiver)
            return
        }
        var key = key
        if (private) {
            key += "@private"
        }
        if (!overridable) {
            key += "@hash+${op.hashCode()}"
            //抗碰撞
            if (key in ops) {
                var vkey = key
                var cnt = 0
                while (vkey in ops) {
                    vkey = key + cnt++
                }
                key = vkey
            }
        }
        CLogUtils.walker.walk { frame ->
            stacks[key] = frame.map(Any::toString).toList()
        }
        ops[key] = queue to op
    }

    fun copyFrom(model: OperationRecordable<Receiver>) {
        check(canAddOps())
        check("@uncopiable" !in model.ops) { "This Operation has been marked as Uncopiable" }
        val op = model.ops.filter { (key, _) -> !key.contains("@private") }
        ops.putAll(op)
        op.keys.forEach { key ->
            model.stacks[key]?.also { stacks[key] = it }
        }
    }


    fun markUncopiable() {
        record("@uncopiable") { error("Uncopiable Operation") }
    }

    fun removeRecord(key: String, optional: Boolean = false) {
        val removed = ops.keys.removeIf { it.split("@", limit = 2)[0] == key }
        if (!optional && !removed) {
            error("Unable to remove record as the record does not exist. Probably candidates: ${ops.keys}")
        }
    }

    protected fun executeRecords() {
        require(!frozen && !executing) { "Operations has been executed" }
        executing = true
        for (type in QueueType.entries) {
            ops.filter { it.value.first == type }
                .forEach { (key, entry) ->
                    val (_, ops) = entry
                    ifDev {
                        debugLog.info("\t\trunning operation <${type.name.lowercase()}> `$key`")
                    }
                    try {
                        ops(receiver)
                    } catch (e: Throwable) {
                        clog.error("Unexpected exception encountered when execute operation `$key`", e)
                        clog.error(
                            "Operation creation stack trace:\n${
                                stacks[key]?.joinToString(
                                    "\n\tat ",
                                    prefix = "\tat "
                                ) ?: "\tNo stack trace"
                            }"
                        )
                        throw e
                    }
                }
        }
        frozen = true
    }

    fun canAddOps() = !frozen
}