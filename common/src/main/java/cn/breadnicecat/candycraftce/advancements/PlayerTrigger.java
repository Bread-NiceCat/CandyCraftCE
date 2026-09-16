package cn.breadnicecat.candycraftce.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import java.util.Optional;

public class PlayerTrigger extends SimpleCriterionTrigger<PlayerTrigger.Instance> {

	public void trigger(ServerPlayer player) {
		super.trigger(player, instance -> true);
	}

	@Override
	public Codec<Instance> codec() {
		return Instance.CODEC;
	}

	public record Instance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(builder -> builder.group(
			ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Instance::player)
		).apply(builder, Instance::new));

		@Override
		public Optional<ContextAwarePredicate> player() {
			return player;
		}
	}
}