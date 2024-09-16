package cn.breadnicecat.candycraftce.level.generator;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Redstone;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static net.minecraft.core.Direction.*;
import static net.minecraft.world.level.block.Blocks.*;

public class JellyDungeonGenerator {

    public static final int GENERATED_Y = 10;
    public static final BlockState wall = JAWBREAKER_BRICKS.defaultBlockState();
    public static final BlockState wall_lantern = JAWBREAKER_LIGHT.defaultBlockState();
    public static final BlockState licorice_block = LICORICE_BLOCK.defaultBlockState();
    public static final BlockState licorice_brick = LICORICE_BRICKS.defaultBlockState();
    public static final BlockState licorice_slab = LICORICE_SLAB.defaultBlockState();

    public static final BlockState water = Fluids.WATER.defaultFluidState().createLegacyBlock();

    public static final BlockState honeycomb_lamp = HONEYCOMB_LAMP.defaultBlockState();
    public static final BlockState green_jelly = TRAMPOJELLY.defaultBlockState();
    public static final BlockState red_jelly = RED_TRAMPOJELLY.defaultBlockState();
    public static final BlockState soft_jelly = SOFT_TRAMPOJELLY.defaultBlockState();
    public static final BlockState blue_jelly = JELLY_SHOCK_ABSORBER.defaultBlockState();
    public static final BlockState sensitive_jelly = SENSITIVE_JELLY.defaultBlockState();

    //红石线会自动更新
    public static final BlockState redstone_wire = REDSTONE_WIRE.defaultBlockState();
    public static final BlockState redstone_lamp = REDSTONE_LAMP.defaultBlockState();

    public static final Random random = new Random();
    public final Logger logger = CLogUtils.getModLogger();
    private final Map<BlockPos, Placer> blocks = new HashMap<>();

    public Placer litableWallBlock(int denominator) {
        return CommonUtils.probability(random, denominator)
                ? simpleBlock(wall_lantern)
                : simpleBlock(wall);
    }


    public static Placer simpleBlock(BlockState state) {
        return (l, p) -> l.setBlock(p, state, 2 | 16);
    }

    public static Placer simpleBlockWithUpdate(BlockState state) {
        return (l, p) -> l.setBlockAndUpdate(p, state);
    }

    public void addBlock(BlockPos zero, int x, int y, int z, Placer placer) {
        blocks.put(zero.offset(x, y, z), Objects.requireNonNull(placer));
    }

    public void removeBlock(BlockPos zero, int x, int y, int z) {
        blocks.remove(zero.offset(x, y, z));
    }

    BlockPos genTunnel2x3Z(BlockPos zero, int length) {
        //对称
        for (int x = -1; x < 1; x++) {
            for (int y = -1; y < 4; y++) {
                for (int z = 0; z < length; z++) {
                    if (x == -1 || y == -1 || y == 3) {
                        addBlock(zero, x, y, z, simpleBlock(wall));
                        addBlock(zero, 1 - x, y, z, simpleBlock(wall));
                    }
                }
            }
        }
        return zero.offset(0, 0, length);
    }

    BlockPos genInitialRoom(BlockPos zero) {
        long pstt = System.currentTimeMillis();
        //外壳
        for (int z = -2; z < 3; z++) {
            for (int y = -1; y < 4; y++) {
                if (y == -1 || y == 3) {
                    //地板和天花板
                    for (int x = -2; x < 10; x++) {
                        addBlock(zero, x, y, z, simpleBlock(wall));
                    }
                } else {
                    //墙
                    if (z == -2 || z == 2) {
                        //z墙
                        for (int x = -2; x < 10; x++) {
                            addBlock(zero, x, y, z, litableWallBlock(20));
                        }
                    } else {
                        //x墙
                        addBlock(zero, -2, y, z, simpleBlock(wall));
                        addBlock(zero, 9, y, z, simpleBlock(wall));
                    }
                }
            }
        }
        //立柱
        for (int y = 0; y < 3; y++) {
            addBlock(zero, 5, y, 0, simpleBlock(wall));
        }

        //果冻灯
        //z墙
        for (int x = 0; x < 7; x += 3) {
            for (int zm : new int[]{-1, 1}) {
                addBlock(zero, x, 1, 2 * zm, simpleBlock(green_jelly));
                //灯
                addBlock(zero, x, 1, (2 + 1) * zm, simpleBlock(wall_lantern));
            }
        }
        //x墙
        addBlock(zero, -2, 1, 0, simpleBlock(green_jelly));
        addBlock(zero, -2 - 1, 1, 0, simpleBlock(wall_lantern));
        addBlock(zero, 9, 1, 0, simpleBlock(green_jelly));
        addBlock(zero, 9 + 1, 1, 0, simpleBlock(wall_lantern));

        //入口下面的灯
        addBlock(zero, 0, -1, 0, simpleBlock(HONEYCOMB_LAMP.defaultBlockState()));

        //出口
        for (int x = 7; x < 9; x++) {
            for (int y = 0; y < 3; y++) {
                removeBlock(zero, x, y, 2);
            }
        }
        logger.info("genInitialRoom: {} ms.", System.currentTimeMillis() - pstt);
        return zero.offset(7, 0, 3);
    }

    public BlockPos genBridgeRoom(BlockPos zero) {
        long pstt = System.currentTimeMillis();

        zero = genTunnel2x3Z(zero, 3);
        //活塞门
        genTunnel2x3Z(zero, 2);
        for (int c = 0; c < 2; c++) {
            //镜像操作: x,y,z->1-x,y,z
            boolean mirror = c == 1;
            Direction faceTunnel = mirror ? WEST : EAST;
            Direction oppositeTunnel = faceTunnel.getOpposite();
            //红色果冻装饰
            for (int y = 0; y < 3; y++) {
                addBlock(zero, mirror ? 2 : -1, y, 0, simpleBlock(red_jelly));
                addBlock(zero, mirror ? 3 : -2, y, 0, simpleBlock(wall));
            }
            addBlock(zero, mirror ? 1 : 0, 3, 0, simpleBlock(blue_jelly));
            addBlock(zero, mirror ? 1 : 0, 4, 0, simpleBlock(wall_lantern));

            //活塞门中方块
            addBlock(zero, mirror ? 1 : 0, 0, 1, simpleBlock(LICORICE_SLAB.defaultBlockState()));
            addBlock(zero, mirror ? 1 : 0, 1, 1, simpleBlock(LICORICE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, oppositeTunnel)));
            addBlock(zero, mirror ? 1 : 0, 2, 1, simpleBlock(LICORICE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, oppositeTunnel).setValue(StairBlock.HALF, Half.TOP)));
            //触发果冻
            addBlock(zero, mirror ? 1 : 0, -1, 0, simpleBlock(sensitive_jelly));//
            //红石

            //触发果冻下面的红石
            for (int x = -2; x < 1; x++) {
                addBlock(zero, mirror ? 1 - x : x, -3, 0, simpleBlock(wall));
                addBlock(zero, mirror ? 1 - x : x, -2, 0, simpleBlock(redstone_wire));

                //防止透过触发果冻漏光
                addBlock(zero, mirror ? 1 - x : x, -2, 1, simpleBlock(wall));
                addBlock(zero, mirror ? 1 - x : x, -2, -1, simpleBlock(wall));
            }
            //从下面的红石传递过来
            for (int x = -5; x < -2; x++) {
                addBlock(zero, mirror ? 1 - x : x, -2, 0, simpleBlock(wall));
                addBlock(zero, mirror ? 1 - x : x, -1, 0, simpleBlock(redstone_wire));
            }
            //压线
            addBlock(zero, mirror ? 5 : -4, 0, 0, simpleBlock(wall));
            //回头去给火把充能
            for (int x = -5; x < -3; x++) {
                addBlock(zero, mirror ? 1 - x : x, -1, 1, simpleBlock(wall));
                addBlock(zero, mirror ? 1 - x : x, 0, 1, simpleBlock(redstone_wire));
            }
            //火把
            addBlock(zero, mirror ? 4 : -3, 0, 1, simpleBlock(wall));
            addBlock(zero, mirror ? 4 : -3, 1, 1, simpleBlockWithUpdate(REDSTONE_TORCH.defaultBlockState()));
            addBlock(zero, mirror ? 4 : -3, 2, 1, simpleBlock(wall));

            //活塞
            for (int y = 1; y < 3; y++) {
                addBlock(zero, mirror ? 3 : -2, y, 1, simpleBlockWithUpdate(STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, faceTunnel)));
                removeBlock(zero, mirror ? 2 : -1, y, 1);
            }
            //活塞上面的方块,用来堵光
            addBlock(zero, mirror ? 3 : -2, 3, 1, simpleBlock(wall));

        }
        zero = zero.offset(0, 0, 2);

        //post road
        genTunnel2x3Z(zero, 4);
        //红色果冻
        for (int y = 0; y < 3; y++) {
            addBlock(zero, -1, y, 0, simpleBlock(red_jelly));
            addBlock(zero, -2, y, 0, simpleBlock(wall));
            addBlock(zero, 2, y, 0, simpleBlock(red_jelly));
            addBlock(zero, 3, y, 0, simpleBlock(wall));
            addBlock(zero, -1, y, 2, simpleBlock(soft_jelly));
            addBlock(zero, -2, y, 2, simpleBlock(wall));
            addBlock(zero, 2, y, 2, simpleBlock(soft_jelly));
            addBlock(zero, 3, y, 2, simpleBlock(wall));
        }
        zero = zero.offset(0, 0, 4);

        logger.info("genBridge: {} ms.", System.currentTimeMillis() - pstt);
        return zero;
    }

    BlockPos genGravesRoom(BlockPos zero) {
        long pstt = System.currentTimeMillis();
        //外壳
        for (int x = -11; x < 13; x++) {
            for (int y = -1; y < 6; y++) {
                if (x == -11 || x == 12) {
                    //x墙
                    for (int z = 0; z < 56; z++) {
                        addBlock(zero, x, y, z, simpleBlock(wall));
                    }
                } else if (y == 5 || y == -1) {
                    //天花板 地板
                    for (int z = 0; z < 56; z++) {
                        addBlock(zero, x, y, z, simpleBlock(CommonUtils.probability(random, 4) ? licorice_block : wall));
                    }
                }
                //z墙
                addBlock(zero, x, y, 0, simpleBlock(wall));
                addBlock(zero, x, y, 55, simpleBlock(wall));
            }
        }

        //红石总线
        int zcc = Redstone.SIGNAL_MAX;
        for (int z = 6; z < 53; z++) {
            boolean needRepeater = false;
            if (--zcc < 0) {
                needRepeater = true;
                zcc = Redstone.SIGNAL_MAX;
            }
            for (int c = 0; c < 2; c++) {
                boolean mirror = c == 0;
                addBlock(zero, mirror ? -3 : 4, -4, z, simpleBlock(wall));
                addBlock(zero, mirror ? -3 : 4, -3, z, simpleBlock(needRepeater ?
                        REPEATER.defaultBlockState()
                        : redstone_wire));
            }
        }
        for (int c = 0; c < 2; c++) {
            boolean mirror = c == 0;
            addBlock(zero, mirror ? -3 : 4, -3, 52, simpleBlock(wall));
            addBlock(zero, mirror ? -3 : 4, -3, 53, simpleBlock(REPEATER.defaultBlockState()));
            //总线汇总
            for (int x = 1; x < 5; x++) {
                addBlock(zero, mirror ? 1 - x : x, -4, 53, simpleBlock(wall));
                addBlock(zero, mirror ? 1 - x : x, -3, 53, simpleBlock(redstone_wire));
            }
            addBlock(zero, mirror ? 0 : 1, -4, 54, simpleBlock(wall));
            addBlock(zero, mirror ? 0 : 1, -3, 54, simpleBlock(REPEATER.defaultBlockState()));

            addBlock(zero, mirror ? -3 : 4, -4, 52, simpleBlock(wall));
            addBlock(zero, mirror ? -3 : 4, -3, 52, simpleBlock(REPEATER.defaultBlockState()));
            //门下
            addBlock(zero, mirror ? 0 : 1, -3, 55, simpleBlock(wall));
            addBlock(zero, mirror ? 0 : 1, -2, 55, simpleBlock(REDSTONE_TORCH.defaultBlockState()));
        }

        //坟墓
        for (int z = 6; z < 50; z += 6) {
            for (int c = 0; c < 2; c++) {
                boolean mirror = c == 1;
                Direction facingCenter = mirror ? EAST : WEST;
                //棺材
                for (int x = 8; x < 12; x++) {
                    addBlock(zero, mirror ? 1 - x : x, 0, z + 1, simpleBlock(LICORICE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, NORTH)));
                    addBlock(zero, mirror ? 1 - x : x, 1, z + 1, simpleBlock(LICORICE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, NORTH).setValue(StairBlock.HALF, Half.TOP)));

                    addBlock(zero, mirror ? 1 - x : x, 0, z - 1, simpleBlock(LICORICE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, SOUTH)));
                    addBlock(zero, mirror ? 1 - x : x, 1, z - 1, simpleBlock(LICORICE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, SOUTH).setValue(StairBlock.HALF, Half.TOP)));

                    //封盖
                    addBlock(zero, mirror ? 1 - x : x, 2, z, simpleBlock(LICORICE_SLAB.defaultBlockState()));
                }
                //柱子
                for (int y = 0; y < 6; y++) {
                    addBlock(zero, mirror ? -7 : 8, y, z, simpleBlock(licorice_block));
                }
                //拉杆
                addBlock(zero, mirror ? -6 : 7, 0, z, simpleBlock(LEVER.defaultBlockState().setValue(LeverBlock.FACING, facingCenter)));
                //拉杆上的灯
                addBlock(zero, mirror ? -7 : 8, 1, z, simpleBlock(redstone_lamp));
                //拉杆两边和下面
                addBlock(zero, mirror ? -6 : 7, 0, z - 1, simpleBlock(LICORICE_SLAB.defaultBlockState()));
                addBlock(zero, mirror ? -6 : 7, 0, z + 1, simpleBlock(LICORICE_SLAB.defaultBlockState()));
                addBlock(zero, mirror ? -6 : 7, -1, z, simpleBlock(LICORICE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP)));

                //接入总线
//            addBlock(zero, mirror ? -7 : 8, -1, z, simpleBlock(REPEATER.defaultBlockState().setValue(RepeaterBlock.FACING, oppositeCenter)));
                addBlock(zero, mirror ? -7 : 8, -1, z, simpleBlock(redstone_wire));
                addBlock(zero, mirror ? -7 : 8, -2, z, simpleBlock(wall));
                for (int x = 6; x < 8; x++) {
                    addBlock(zero, mirror ? 1 - x : x, -2, z, simpleBlock(redstone_wire));
                    addBlock(zero, mirror ? 1 - x : x, -3, z, simpleBlock(wall));
                }
                addBlock(zero, mirror ? -4 : 5, -4, z, simpleBlock(wall));
                addBlock(zero, mirror ? -4 : 5, -3, z, simpleBlock(REDSTONE_WALL_TORCH.defaultBlockState().setValue(RedstoneWallTorchBlock.FACING, facingCenter)));
            }
        }

        //入口
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 3; y++) {
                removeBlock(zero, x, y, 0);
            }
        }

        //出口
        addBlock(zero, 0, 0, 55, simpleBlock(IRON_DOOR.defaultBlockState().setValue(DoorBlock.HINGE, DoorHingeSide.RIGHT).setValue(DoorBlock.FACING, SOUTH)));
        addBlock(zero, 0, 1, 55, simpleBlock(IRON_DOOR.defaultBlockState().setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER).setValue(DoorBlock.HINGE, DoorHingeSide.RIGHT).setValue(DoorBlock.FACING, SOUTH)));
        addBlock(zero, 1, 0, 55, simpleBlock(IRON_DOOR.defaultBlockState().setValue(DoorBlock.FACING, SOUTH)));
        addBlock(zero, 1, 1, 55, simpleBlock(IRON_DOOR.defaultBlockState().setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER).setValue(DoorBlock.FACING, SOUTH)));
        logger.info("genGravesRoom: {} ms.", System.currentTimeMillis() - pstt);
        return zero.offset(0, 0, 56);
    }

    BlockPos genWaterRoom(BlockPos zero) {
        long pstt = System.currentTimeMillis();

        for (int x = -11; x < 13; x++) {
            for (int z = 0; z < 23; z++) {
                if (x == -11 || x == 12 || z == 0 || z == 22) {
                    //墙
                    for (int y = -1; y < 22; y++) {
                        addBlock(zero, x, y, z, litableWallBlock(50));
                    }
                } else {
                    //天花板 地板
                    addBlock(zero, x, -1, z, simpleBlock(wall));
                    Placer pp = null;
                    switch (random.nextInt(3)) {
                        case 0:
                            pp = simpleBlock(licorice_block);
                            break;
                        case 1:
                            pp = simpleBlock(licorice_brick);
                            break;
                        case 2:
                            pp = simpleBlock(licorice_slab);
                            break;
                    }
                    addBlock(zero, x, 0, z, pp);
                    addBlock(zero, x, 21, z, litableWallBlock(50));
                    //水
                    for (int y = 3; y < 18; y += 2) {
                        addBlock(zero, x, y, z, simpleBlock(water));
                    }
                }
            }
        }
        addBlock(zero, 0, 0, 1, simpleBlock(licorice_slab));
        addBlock(zero, 1, 0, 1, simpleBlock(licorice_slab));
        BlockPos old_zero = zero;
        zero = zero.offset(0, 0, 23);
        //JUMP
        for (int x = -1; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                if (x == -1 || x == 2 || z == 2) {
                    for (int y = -1; y < 22; y++) {
                        addBlock(zero, x, y, z, simpleBlock(wall));
                    }
                } else {
                    addBlock(zero, x, -2, z, simpleBlock(wall_lantern));
                    addBlock(zero, x, -1, z, simpleBlock(blue_jelly));
                    addBlock(zero, x, 21, z, simpleBlock(wall));
                }
            }
        }

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 3; y++) {
                //入口
                removeBlock(old_zero, x, y, 0);
                //JUMP
                removeBlock(old_zero, x, y + 18, 22);
                //出口
                removeBlock(zero, x, y, 2);
            }
        }


        logger.info("genWaterRoom: {} ms.", System.currentTimeMillis() - pstt);
        return zero.offset(0, 0, 3);

    }

    BlockPos genParkourRoom(BlockPos zero) {
        long pstt = System.currentTimeMillis();
        for (int x = -3; x < 5; x++) {
            for (int z = 0; z < 56; z++) {
                if (x == -3 || x == 4) {
                    //X墙,附蓝果冻灯
                    for (int y = -5; y < 30; y++) {
                        if ((z + y) % 2 == 0) {
                            addBlock(zero, x, y, z, simpleBlock(blue_jelly));
                            addBlock(zero, x == -3 ? x - 1 : x + 1, y, z, simpleBlock(wall_lantern));
                        } else {
                            addBlock(zero, x, y, z, simpleBlock(wall));
                        }
                    }
                } else if (z == 0 || z == 55) {
                    for (int y = -5; y < 30; y++) {
                        addBlock(zero, x, y, z, simpleBlock(wall));
                    }
                } else {
                    //天花板
                    addBlock(zero, x, 29, z, simpleBlock(wall));
                    //水
                    addBlock(zero, x, -4, z, simpleBlock(water));
                    addBlock(zero, x, -3, z, simpleBlock(water));
                    //地板
                    addBlock(zero, x, -5, z, simpleBlock(wall));
                }
            }
        }
        //跳板
        for (int x = -2; x < 4; x++) {
            for (int y = -4; y < 0; y++) {
                addBlock(zero, x, y, 1, simpleBlock(licorice_block));
                addBlock(zero, x, y, 2, simpleBlock(MARSHMALLOW_LADDER.defaultBlockState()
                        .setValue(LadderBlock.FACING, SOUTH)
                        .setValue(LadderBlock.WATERLOGGED, y < -2)
                ));
            }
        }

        //果冻
        addBlock(zero, random.nextInt(5) - 2, 0, 3, simpleBlock(soft_jelly));
        for (int x = 0, y = -1, mode = 0, z = 3 + 7; z < 50; ) {
            if (mode == 0) {
                x = random.nextInt(5) - 2;
                y = random.nextInt(6);
                addBlock(zero, x, y, z, simpleBlock(soft_jelly));
                z += 6;
                if (z > 25 && (z > 40 || random.nextInt(10) == 0)) {
                    mode = 1;
                    y += 15;
                    z -= 6;
                }
            } else {
                int dz = random.nextInt(2) + 3;
                z += dz;
                //防止出现 斜4格
                if (dz < 4) x = Mth.clamp(x + random.nextInt(3) - 1, -2, 3);

                int dy = random.nextInt(3) - 1;
                y += dy;
                //防止出现 上4格
                if (dy > 0 && dz > 3) z--;

                addBlock(zero, x, y, z, simpleBlock(honeycomb_lamp));
            }
        }
        addBlock(zero, 0, 15, 54, simpleBlock(honeycomb_lamp));
        addBlock(zero, 1, 13, 54, simpleBlock(LEVER.defaultBlockState()));
        addBlock(zero, 1, 13, 55, simpleBlock(redstone_lamp));
        addBlock(zero, 0, -1, 55, simpleBlock(licorice_slab));
        addBlock(zero, 1, -1, 55, simpleBlock(licorice_slab));
        //入口 出口
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 3; y++) {
                removeBlock(zero, x, y, 0);
                removeBlock(zero, x, y, 55);
            }
        }


        logger.info("genParkourRoom: {} ms.", System.currentTimeMillis() - pstt);
        return genTunnel2x3Z(zero.offset(0, 0, 56), 2);
    }

    public void generate(Level level, BlockPos pos) {
        long stt = System.currentTimeMillis();
        logger.info("Generating...");
        final BlockPos[] cur = {pos.mutable()};
        CompletableFuture<Void> async = CompletableFuture.runAsync(() -> {
            cur[0] = genInitialRoom(cur[0]);
            cur[0] = genBridgeRoom(cur[0]);
            cur[0] = genGravesRoom(cur[0]);
            cur[0] = genBridgeRoom(cur[0]);
            cur[0] = genWaterRoom(cur[0]);
            cur[0] = genBridgeRoom(cur[0]);
            cur[0] = genParkourRoom(cur[0]);
            cur[0] = genBridgeRoom(cur[0]);

        });
        try {
            async.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        //debug 获取当前zero位置
        addBlock(cur[0], 0, 0, 0, simpleBlock(honeycomb_lamp));
        blocks.forEach((pos2, placer) -> {
            placer.addBlock(level, pos2);
        });
        logger.info("total : {} ms.", System.currentTimeMillis() - stt);
    }

}