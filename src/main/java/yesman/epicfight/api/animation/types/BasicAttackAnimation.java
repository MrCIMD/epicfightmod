package yesman.epicfight.api.animation.types;

import java.util.Locale;

import javax.annotation.Nullable;

import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class BasicAttackAnimation extends AttackAnimation {
	public BasicAttackAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
		this(convertTime, antic, antic, contact, recovery, collider, colliderJoint, path, armature);
	}
	
	public BasicAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
		super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
		this.addProperty(AttackAnimationProperty.ROTATE_X, true);
		this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
	}
	
	public BasicAttackAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
		super(convertTime, antic, antic, contact, recovery, hand, collider, colliderJoint, path, armature);
		this.addProperty(AttackAnimationProperty.ROTATE_X, true);
		this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
	}
	
	@Override
	protected void bindPhaseState(Phase phase) {
		float preDelay = phase.preDelay;
		
		if (preDelay == 0.0F) {
			preDelay += 0.01F;
		}
		
		this.stateSpectrumBlueprint
			.newTimePair(phase.start, preDelay)
			.addState(EntityState.PHASE_LEVEL, 1)
			.newTimePair(phase.start, phase.contact + 0.01F)
			.addState(EntityState.CAN_SKILL_EXECUTION, false)
			.newTimePair(phase.start, phase.recovery)
			.addState(EntityState.MOVEMENT_LOCKED, true)
			.addState(EntityState.CAN_BASIC_ATTACK, false)
			.newTimePair(phase.start, phase.end)
			.addState(EntityState.INACTION, true)
			.newTimePair(preDelay, phase.contact + 0.01F)
			.addState(EntityState.ATTACKING, true)
			.addState(EntityState.PHASE_LEVEL, 2)
			.newTimePair(phase.contact + 0.01F, phase.end)
			.addState(EntityState.PHASE_LEVEL, 3)
			.addState(EntityState.TURNING_LOCKED, true);
	}
	
	@Override
	public void setLinkAnimation(Pose pose1, float timeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest) {
		float extTime = Math.max(this.convertTime + timeModifier, 0);
		
		if (entitypatch instanceof PlayerPatch<?>) {
			PlayerPatch<?> playerpatch = (PlayerPatch<?>)entitypatch;
			Phase phase = this.getPhaseByTime(playerpatch.getAnimator().getPlayerFor(this).getElapsedTime());
			extTime *= (float)(this.totalTime * playerpatch.getAttackSpeed(phase.hand));
		}
		
		extTime = Math.max(extTime - this.convertTime, 0);
		super.setLinkAnimation(pose1, extTime, entitypatch, dest);
	}
	
	@Override
	protected void onLoaded() {
		super.onLoaded();
		
		if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
			float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.2f", (1.0F / this.totalTime)));
			this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
		}
	}
	
	@Override
	protected Vec3f getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
		Vec3f vec3 = super.getCoordVector(entitypatch, dynamicAnimation);
		
		if (entitypatch.shouldBlockMoving() && this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
			vec3.scale(0.0F);
		}
		
		return vec3;
	}
	
	@Override
	public boolean isBasicAttackAnimation() {
		return true;
	}
}