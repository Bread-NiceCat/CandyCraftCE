package cn.breadnicecat.candycraftce.misc.mixin_ref;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

/**
 * Created in 2024/7/3 上午1:17
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public interface $LivingEntity {
	EntityDataAccessor<Integer> DATA_CARAMEL_ARROW_COUNT_ID = SynchedEntityData.defineId(net.minecraft.world.entity.LivingEntity.class, EntityDataSerializers.INT);
}
