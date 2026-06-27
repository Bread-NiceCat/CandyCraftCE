package cn.breadnicecat.candycraftce.core.tag

import cn.breadnicecat.candycraftce.data.CDataUtils
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid


data class TagKeys(val item: TagKey<Item>, val block: TagKey<Block>)
object CTags {
    val candy_leaves = bind2("candy_leaves")
    val marshmallow_logs = bind2("marshmallow_logs")
    val marshmallow_planks = bind2("marshmallow_planks")
    val candy_bricks = bind2("candy_bricks")
    val jelly = bind2("jelly")
    val ice_creams = bind2("ice_cream")
    val chocolates = bind2("chocolate")
    val sugary = bind2("sugary")
    val ore_honeycomb = bind2("ore_honeycomb")
    val ore_pez = bind2("ore_pez")
    val ore_licorice = bind2("ore_licorice")
    val ore_nougat = bind2("ore_nougat")
    val ore_jelly = bind2("ore_jelly")

    object CFluidTags {
        val candy_water_plant_suitable = bind("candy_water_plant_suitable")
        fun bind(name: String): TagKey<Fluid> {
            return TagKey.create(Registries.FLUID, name.modLoc())
        }
    }

    object CItemTags {
        val keys = bind("keys")
        val pez = bind("pez")
        val leaf = bind("leaf")
        val emblem = bind("emblem")
        val licorice = bind("licorice")
        val honeycomb = bind("honeycomb")

        fun bind(name: String): TagKey<Item> {
            return TagKey.create(Registries.ITEM, name.modLoc())
        }

    }

    object CBlockTags {
        val caramel_portal_frame = bind("caramel_portal_frame")
        val candy_plant_suitable = bind("candy_plant_suitable")
        val candy_animal_spawnable_on = bind("candy_animal_spawnable_on")
        val carver_overrideable = bind("carver_overrideable")

        val ore_white_overrideable = bind("ore_white_overrideable")
        val ore_black_overrideable = bind("ore_black_overrideable")
        val candy_ores = bind("candy_ores")

        fun bind(name: String): TagKey<Block> {
            return TagKey.create(Registries.BLOCK, name.modLoc())
        }
    }

    fun bind2(name: String): TagKeys {
        val v = TagKeys(CItemTags.bind(name), CBlockTags.bind(name))
        CDataUtils.tagCopy(v)
        return v
    }

}