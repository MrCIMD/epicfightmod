package yesman.epicfight.api.animation.types;

import net.minecraft.resources.ResourceLocation;

public class GuardAnimation extends MainFrameAnimation {
	public GuardAnimation(float convertTime, String path, ResourceLocation armature) {
		this(convertTime, Float.MAX_VALUE, path, armature);
	}
	
	public GuardAnimation(float convertTime, float lockTime, String path, ResourceLocation armature) {
		super(convertTime, path, armature);
		
		this.stateSpectrumBlueprint.clear()
			.newTimePair(0.0F, lockTime)
			.addState(EntityState.TURNING_LOCKED, true)
			.addState(EntityState.MOVEMENT_LOCKED, true)
			.addState(EntityState.CAN_BASIC_ATTACK, false)
			.newTimePair(0.0F, Float.MAX_VALUE)
			.addState(EntityState.INACTION, true);
	}
}