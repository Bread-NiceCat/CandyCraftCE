package cn.breadnicecat.candycraftce.utils.codec

import com.google.gson.JsonElement
import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.JsonOps


/**
 * Created by NiceCat on 2026/3/22.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
abstract class JsonCodec<R> : Codec<R> {
    abstract fun toJson(input: R): JsonElement
    abstract fun fromJson(json: JsonElement): R

    final override fun <T : Any> encode(
        input: R,
        ops: DynamicOps<T>,
        prefix: T,
    ): DataResult<T> {
        val value = JsonOps.INSTANCE.convertTo(ops, toJson(input))
        return ops.mergeToPrimitive(prefix, value)
    }

    final override fun <T : Any> decode(
        ops: DynamicOps<T>,
        input: T,
    ): DataResult<Pair<R, T>> {
        val json = ops.convertTo(JsonOps.INSTANCE, input)
        return DataResult.success(Pair.of(fromJson(json), input))
    }
}

class JsonCodecImpl<R>(
    val serializer: (R) -> JsonElement,
    val deserializer: (JsonElement) -> R,
) : JsonCodec<R>() {
    override fun toJson(input: R): JsonElement = serializer(input)
    override fun fromJson(json: JsonElement): R = deserializer(json)
}