package cn.breadnicecat.candycraftce.utils

import cn.breadnicecat.candycraftce.utils.Utils.safeForEach

typealias Operation<Receiver> = Receiver.() -> Unit

abstract class OperationRecordable<Receiver> {

    internal abstract val receiver: Receiver

    private val ops = LinkedHashMap<String, Operation<Receiver>>()

    var executing = false
        private set
    var frozen = false
        private set

    internal fun record(
        key: String,
        overridable: Boolean = true,
        private: Boolean = false,
        op: Operation<Receiver>,
    ) {
        check(!frozen)
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
        ops[key] = op
    }

    fun copyRecord(model: OperationRecordable<Receiver>) {
        check(canAddOps())
        check("@uncopiable" !in model.ops) { "Unable to copy ops as the builder has been marked it as uncopiable" }
        val op = model.ops.filter { (key, _) -> !key.contains("@private") }
        ops.putAll(op)
    }


    fun markUncopiable() {
        record("@uncopiable") { error("uncopiable") }
    }

    fun removeRecord(key: String, optional: Boolean = false) {
        val removed = ops.keys.removeIf { it.split("@", limit = 2)[0] == key }
        if (!optional && !removed) {
            error("Unable to remove record as the record does not exist")
        }
    }

    fun executeRecords() {
        executing = true
        ops.safeForEach({ "Execute operation: $it" }) { (_, op) -> (op(receiver)) }
        frozen = true
    }

    fun canAddOps() = !frozen
}