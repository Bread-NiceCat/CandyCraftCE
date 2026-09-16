package cn.breadnicecat.candycraftce.advancements;

import cn.breadnicecat.candycraftce.neoforge.CandyCraftCEImpl;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;

public class CAdvancements {
	public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = CandyCraftCEImpl.getOrCreate(MOD_ID, Registries.TRIGGER_TYPE);

	public static final DeferredHolder<CriterionTrigger<?>, PlayerTrigger> EAT_BLOCK = TRIGGERS.register("eat_block", PlayerTrigger::new);

	public static void bindHooks() {
		Triggers.EAT_BLOCK = (ServerPlayer sp) -> {
			EAT_BLOCK.value().trigger(sp);
		};
	}
}