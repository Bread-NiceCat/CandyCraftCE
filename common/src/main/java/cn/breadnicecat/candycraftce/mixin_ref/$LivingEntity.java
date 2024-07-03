package cn.breadnicecat.candycraftce.mixin_ref;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;

/**
 * Created in 2024/7/3 上午1:17
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class $LivingEntity {
	/**
	 * For Mixin
	 */
	public static final EntityDataAccessor<Integer> LivingEntity$DATA_CARAMEL_ARROW_COUNT_ID = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
}
