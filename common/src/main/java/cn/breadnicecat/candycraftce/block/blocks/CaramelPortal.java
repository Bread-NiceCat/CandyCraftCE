package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.level.CDims;
import cn.breadnicecat.candycraftce.misc.CGameRules;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.hate;

/**
 * Created in 2023/12/31 9:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CaramelPortal extends NetherPortalBlock {
	public CaramelPortal(Properties properties) {
		super(properties);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		//绕开原版逻辑，直接传送
		if (level.isClientSide()) return;
		if (entity instanceof ItemEntity itemEntity) {
//			ItemStack stack = itemEntity.getItem();
//			itemEntity.setItem(ItemStack.EMPTY);
			//交易
//			Vec3 delta = itemEntity.getDeltaMovement();
//			for (CaramelPortalRecipe r : RecipeHelper.getAllRecipesFor(CCManagerRecipe.caramel_portal_type.get())) {
//				if (r.matches(stack, level)) {
//					ItemStack result = r.assemble(stack);
//					level.addFreshEntity(new ItemEntity(level, pPos.getX(), pPos.getY(), pPos.getZ(), result, -delta.x(), delta.y(), -delta.z()));
//					return;
//				}
//			}
		} else if (entity.isAlive() && !entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
			//传送
			ResourceKey<Level> destination = getDestination(level);
			if (destination != null) {
				MinecraftServer server = level.getServer();
				if (server != null) {
					ServerLevel cl = server.getLevel(destination);
					if (cl != null) {
						if (entity instanceof LivingEntity livE) {
							//cn.breadnicecat.candycraftce.mixin.MixinEntity#findDimensionEntryPoint
							if (livE.changeDimension(cl) instanceof LivingEntity newLivE) {
								newLivE.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 16 * TickUtils.SEC2TICK, 5));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @return null, 如果无法传送
	 */
	protected @Nullable ResourceKey<Level> getDestination(Level level) {
		return level.getGameRules().getBoolean(CGameRules.CARAMEL_PORTAL_WORKS) ?
				hate(level.dimension(), CDims.CANDYLAND, Level.OVERWORLD, null)
				: null;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		//不生成ZombifiedPiglin
	}
}
