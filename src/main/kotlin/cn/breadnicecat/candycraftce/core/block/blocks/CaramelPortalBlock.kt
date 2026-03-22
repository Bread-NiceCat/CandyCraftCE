package cn.breadnicecat.candycraftce.core.block.blocks

import cn.breadnicecat.candycraftce.core.block.CBlocks
import cn.breadnicecat.candycraftce.core.block.CBlocks.caramel_block
import cn.breadnicecat.candycraftce.core.block.CBlocks.caramel_portal
import cn.breadnicecat.candycraftce.core.level.CLevels
import cn.breadnicecat.candycraftce.core.particle.CParticles
import cn.breadnicecat.candycraftce.core.rule.CGameRules
import cn.breadnicecat.candycraftce.core.tag.CTags
import cn.breadnicecat.candycraftce.multiblock.caramel_portal.CaramelPortalSearcher
import cn.breadnicecat.candycraftce.multiblock.caramel_portal.PortalConfig
import cn.breadnicecat.candycraftce.multiblock.caramel_portal.PortalPlacer
import cn.breadnicecat.candycraftce.utils.AxisSet
import cn.breadnicecat.candycraftce.utils.CUtils.get
import cn.breadnicecat.candycraftce.utils.CUtils.instance
import cn.breadnicecat.candycraftce.utils.MCTimeUnit.Companion.second
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks.AIR
import net.minecraft.world.level.block.Blocks.LAVA
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

/**
 * Created in 2023/12/31 9:43
 * Project: candycraftce
 *
 * @author <a href=https://github.com/Bread-Nicecat>Bread_NiceCat</a>
 *
 *
 */
class CaramelPortalBlock(properties: Properties) : Block(properties) {
    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(X, true)
                .setValue(Y, false)
                .setValue(Z, false)
        )
    }

    private fun getShapeIndex(state: BlockState): Int {
        var flag = 0
        if (state.getValue(X)) flag = flag or 1
        if (state.getValue(Y)) flag = flag or 2
        if (state.getValue(Z)) flag = flag or 4
        return flag
    }

    @Deprecated("Deprecated in Java")
    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext,
    ): VoxelShape {
        return shapes[getShapeIndex(state)]
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos,
    ): BlockState {
        //如果旁边的也是传送门方块，
        if (!neighborState.`is`(this)) {
            //两个方块之间未连接
            val axes = AxisSet(direction.axis).not()
            for (ax in axes) {
                if (state.getValue(ax.property)) return AIR.defaultBlockState()
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos)
    }

    @Deprecated("Deprecated in Java")
    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (level.isClientSide()) return
        if (entity.isAlive && !entity.isPassenger && !entity.isVehicle && entity.canChangeDimensions()) {
            //传送
            getDestination(level, entity)?.also { destination ->
                level.server?.also { server ->
                    server.getLevel(destination)?.also { cl ->
                        if (entity is LivingEntity) {
                            //cn.breadnicecat.candycraftce.mixin.cn.breadnicecat.candycraftce.mixin.portal.MixinEntity#findDimensionEntryPoint
                            if (entity.changeDimension(cl) is LivingEntity) {
                                MobEffects.DAMAGE_RESISTANCE.instance(
                                    16.second, 10,
                                    ambient = false,
                                    visible = false,
                                    showIcon = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound(
                pos.x.toDouble() + 0.5,
                pos.y.toDouble() + 0.5,
                pos.z.toDouble() + 0.5,
                SoundEvents.PORTAL_AMBIENT,
                SoundSource.BLOCKS,
                0.5f,
                random.nextFloat() * 0.4f + 0.8f,
                false
            )
        }
        for (i in 0..3) {
            var x = pos.x.toDouble() + random.nextDouble()
            val y = pos.y.toDouble() + random.nextDouble()
            var z = pos.z.toDouble() + random.nextDouble()
            var xSpeed = (random.nextFloat().toDouble() - 0.5) * 0.5
            val ySpeed = (random.nextFloat().toDouble() - 0.5) * 0.5
            var zSpeed = (random.nextFloat().toDouble() - 0.5) * 0.5
            val factor: Int = random.nextInt(2) * 2 - 1
            if (level.getBlockState(pos.west()).`is`(this) || level.getBlockState(pos.east()).`is`(this)) {
                z = pos.z + 0.5 + 0.25 * factor
                zSpeed = random.nextFloat() * 2.0 * factor
            } else {
                x = pos.x + 0.5 + 0.25 * factor
                xSpeed = random.nextFloat() * 2.0 * factor
            }
            level.addParticle(CParticles.caramel_portal_particle_type, x, y, z, xSpeed, ySpeed, zSpeed)
        }
    }

    /**
     * @return null, 如果无法传送
     */
    fun getDestination(level: Level, entity: Entity): ResourceKey<Level>? {
        val work = CGameRules.doCaramelPortalWorks.get(level)
        val ori: ResourceKey<Level> = level.dimension()
        if (!work) return null
        return when (ori) {
            Level.OVERWORLD -> CLevels.candyland
            CLevels.candyland -> Level.OVERWORLD
            else -> null
        }
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(X, Y, Z)
    }


    companion object {
        val X: BooleanProperty = BooleanProperty.create("x")
        val Y: BooleanProperty = BooleanProperty.create("y")
        val Z: BooleanProperty = BooleanProperty.create("z")

        private val Direction.Axis.property: BooleanProperty
            get() = when (this) {
                Direction.Axis.X -> X
                Direction.Axis.Y -> Y
                Direction.Axis.Z -> Z
            }
        val searcher = CaramelPortalSearcher(
            PortalConfig(
                2, 21, 3, 21,
                enableHorizontal = true, enableCompound = true,
                isEmpty = { b -> b.isAir ||  /*b.is(CARAMEL_LIQUID.get()) ||*/b.`is`(LAVA) || b.`is`(caramel_portal.block) },
                isFrame = { b -> b.`is`(CTags.CBlockTags.caramel_portal_frame) })

        )
        val placer = PortalPlacer { axes, old ->
            if (old.`is`(caramel_portal.block)) {
                old.setValue(X, axes.hasX() || old.getValue(X))
                    .setValue(Y, axes.hasY() || old.getValue(Y))
                    .setValue(Z, axes.hasZ() || old.getValue(Z))
            } else {
                caramel_portal.defaultBlockState()
                    .setValue(X, axes.hasX())
                    .setValue(Y, axes.hasY())
                    .setValue(Z, axes.hasZ())
            }
        }

        private val DEFAULT = Shapes.empty()
        private val X_AABB = box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0)
        private val Y_AABB = box(0.0, 6.0, 0.0, 16.0, 10.0, 16.0)
        private val Z_AABB = box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0)
        private val XY_AABB = Shapes.or(X_AABB, Y_AABB)
        private val XZ_AABB = Shapes.or(X_AABB, Z_AABB)
        private val YZ_AABB = Shapes.or(Y_AABB, Z_AABB)
        private val XYZ_AABB = Shapes.or(X_AABB, Y_AABB, Z_AABB)

        /**
         * bin : zyx
         */
        private val shapes: Array<VoxelShape> =
            arrayOf<VoxelShape>(DEFAULT, X_AABB, Y_AABB, XY_AABB, Z_AABB, XZ_AABB, YZ_AABB, XYZ_AABB)

        //Mixin
        @JvmStatic
        fun onLavaPlace(level: Level, pos: BlockPos) {
            searcher.find(level, pos)?.also { portal ->
                for (frame in portal.getAllFrames()) {
                    val state: BlockState = level.getBlockState(frame)
                    if (state.`is`(CBlocks.sugar_sand.block) || state.`is`(CBlocks.sugar_block.block)) {
                        level.setBlockAndUpdate(frame, caramel_block.defaultBlockState())
                    }
                }
                portal.build(level, placer)
            }
        }
    }
}
