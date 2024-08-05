package cn.breadnicecat.candycraftce.datagen.forge.providers.loots;

import cn.breadnicecat.candycraftce.entity.CEntities;
import cn.breadnicecat.candycraftce.entity.EntityEntry;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

import static cn.breadnicecat.candycraftce.entity.CEntities.*;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;

/**
 * Created in 2024/8/3 下午6:20
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CEntityLootSubProvider extends EntityLootSubProvider {
	protected static final EntityPredicate.Builder NOT_BABY = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setIsBaby(false).build());
	
	public CEntityLootSubProvider() {
		super(FeatureFlags.REGISTRY.allFlags());
	}
	
	@Override
	public void generate() {
		//NONE
		accept((i) -> add(i, LootTable.lootTable()),
				GINGERBREAD_MAN);
		add(CRANFISH, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1f))
						.add(LootItem.lootTableItem(CItems.CRANFISH)))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1f))
						.add(LootItem.lootTableItem(CItems.CRANBERRY_EMBLEM))
						.when(LootItemRandomChanceCondition.randomChance(0.02f)))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1f))
						.add(LootItem.lootTableItem(CItems.CRANFISH_SCALE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 2f)))
								//每一等级抢夺附魔增加0-1个掉落
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0f, 1f)))
						))
		);
		add(WAFFLE_SHEEP, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0f))
						.add((LootItem.lootTableItem(CItems.WAFFLE_NUGGET)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))
						)
				)
		);
		add(CANDY_CANE_PIG, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0f))
						.add((LootItem.lootTableItem(CItems.CANDY_CANE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))
						)
				)
		);
		add(BUNNY, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0f))
						.add((LootItem.lootTableItem(CItems.GUMMY)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))
						)
						.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, NOT_BABY))
				)
		);
		
	}
	
	protected void add(EntityEntry<?> entityType, LootTable.Builder builder) {
		add(entityType.get(), builder);
	}
	
	protected void add(EntityEntry<?> entityType, ResourceLocation lootTable, LootTable.Builder builder) {
		add(entityType.get(), lootTable, builder);
	}
	
	@Override
	protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
		return CEntities.ENTITIES.values().stream().filter(EntityEntry::isLivingEntity).map(EntityEntry::get);
	}
}
