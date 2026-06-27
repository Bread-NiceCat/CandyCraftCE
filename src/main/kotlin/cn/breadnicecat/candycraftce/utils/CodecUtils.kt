package cn.breadnicecat.candycraftce.utils

import com.google.gson.JsonElement
import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.JsonOps
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
import net.minecraft.network.FriendlyByteBuf

/**
 * Created by NiceCat on 2026/5/1.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
object CodecUtils {
    fun <T> Codec<T>.encodeToNetwork(input: T, buf: FriendlyByteBuf) {
        val tag = this.encodeStart(NbtOps.INSTANCE, input).result().orElseThrow()
        buf.writeNbt(tag as CompoundTag)
    }

    fun <T> Codec<T>.decodeFromNetwork(buf: FriendlyByteBuf): DataResult<T> {
        val tag = buf.readAnySizeNbt()
        return this.parse(NbtOps.INSTANCE, tag)

    }

}

/**
 * Created by NiceCat on 2026/3/22.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * 用于包装fromJson和toJson方法
 */
abstract class AbstractJsonCodec<R> : Codec<R> {
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

class JsonCodec<R>(
    val serializer: (R) -> JsonElement,
    val deserializer: (JsonElement) -> R,
) : AbstractJsonCodec<R>() {
    override fun toJson(input: R): JsonElement = serializer(input)
    override fun fromJson(json: JsonElement): R = deserializer(json)
}