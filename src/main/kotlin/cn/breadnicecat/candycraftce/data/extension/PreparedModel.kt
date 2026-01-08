package cn.breadnicecat.candycraftce.data.extension

import net.minecraft.resources.ResourceLocation
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by NiceCat on 2025/12/17.
 * Project: candycraftce
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
class PreparedModel : ReadWriteProperty<Any?, ResourceLocation> {
    private var model: ResourceLocation? = null
    fun getModel(): ResourceLocation = model!!
    fun setModel(model: ResourceLocation) {
        this.model = model
    }

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): ResourceLocation = getModel()

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: ResourceLocation,
    ) = setModel(value)
}