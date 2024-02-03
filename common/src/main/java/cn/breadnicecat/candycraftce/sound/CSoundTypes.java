package cn.breadnicecat.candycraftce.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.sound.CSoundEvents.JELLY_DIG;

/**
 * Created in 2024/1/28 16:55
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CSoundTypes {
	public static final SoundType JELLY_FOOTSTEP = create(0.7F, 0.6F, JELLY_DIG);

	public static SoundType create(float volumeIn, float pitchIn, SoundEntry general) {
		return create(volumeIn, pitchIn, general, general, general, general, general);
	}

	public static SoundType create(float volumeIn, float pitchIn, SoundEntry breakSoundIn, SoundEntry stepSoundIn, SoundEntry placeSoundIn, SoundEntry hitSoundIn, SoundEntry fallSoundIn) {
		return new LateinitSoundType(volumeIn, pitchIn, breakSoundIn, stepSoundIn, placeSoundIn, hitSoundIn, fallSoundIn);
	}

	private static class LateinitSoundType extends SoundType {
		private final SoundEntry breakSound;
		private final SoundEntry stepSound;
		private final SoundEntry placeSound;
		private final SoundEntry hitSound;
		private final SoundEntry fallSound;

		/**
		 * @param volumeIn 响度
		 * @param pitchIn  音调
		 */
		@SuppressWarnings("DataFlowIssue")
		private LateinitSoundType(float volumeIn, float pitchIn, SoundEntry breakSoundIn, SoundEntry stepSoundIn, SoundEntry placeSoundIn, SoundEntry hitSoundIn, SoundEntry fallSoundIn) {
			super(volumeIn, pitchIn, null, null, null, null, null);
			this.breakSound = breakSoundIn;
			this.stepSound = stepSoundIn;
			this.placeSound = placeSoundIn;
			this.hitSound = hitSoundIn;
			this.fallSound = fallSoundIn;
		}

		@NotNull
		@Override
		public SoundEvent getBreakSound() {
			return breakSound.getSound();
		}

		@NotNull
		@Override
		public SoundEvent getStepSound() {
			return stepSound.getSound();
		}

		@NotNull
		@Override
		public SoundEvent getPlaceSound() {
			return placeSound.getSound();
		}

		@NotNull
		@Override
		public SoundEvent getHitSound() {
			return hitSound.getSound();
		}

		@NotNull
		@Override
		public SoundEvent getFallSound() {
			return fallSound.getSound();
		}
	}

}
