package yesman.epicfight.api.animation.types;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EntityDimensions;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.client.CPRotateEntityModelYRot;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class DodgeAnimation extends ActionAnimation {
	public DodgeAnimation(float convertTime, String path, float width, float height, Model model) {
		this(convertTime, 0.0F, path, width, height, model);
	}
	
	public DodgeAnimation(float convertTime, float delayTime, String path, float width, float height, Model model) {
		super(convertTime, delayTime, path, model);
		
		this.stateSpectrumBlueprint.clear()
			.newTimePair(0.0F, 10.0F)
			.addState(EntityState.TURNING_LOCKED, true)
			.addState(EntityState.MOVEMENT_LOCKED, true)
			.addState(EntityState.CAN_BASIC_ATTACK, false)
			.addState(EntityState.CAN_SKILL_EXECUTION, false)
			.addState(EntityState.INACTION, true)
			.newTimePair(delayTime, Float.MAX_VALUE)
			.addState(EntityState.INVULNERABILITY_PREDICATE, (damagesource) -> {
				if (damagesource instanceof EntityDamageSource && !damagesource.isExplosion() && !damagesource.isMagic() && !damagesource.isBypassInvul()) {
					return true;
				}
				return false;
			});
		
		this.addProperty(ActionAnimationProperty.AFFECT_SPEED, true);
		this.addEvents(StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.create(Animations.ReuseableEvents.RESTORE_BOUNDING_BOX, AnimationEvent.Side.BOTH));
		this.addEvents(StaticAnimationProperty.EVENTS, AnimationEvent.create(Animations.ReuseableEvents.RESIZE_BOUNDING_BOX, AnimationEvent.Side.BOTH).params(EntityDimensions.scalable(width, height)));
	}
	
	@Override
	public void end(LivingEntityPatch<?> entitypatch, boolean isEnd) {
		super.end(entitypatch, isEnd);
		
		if (entitypatch.isLogicalClient() && entitypatch instanceof LocalPlayerPatch) {
			((LocalPlayerPatch)entitypatch).changeModelYRot(0);
			EpicFightNetworkManager.sendToServer(new CPRotateEntityModelYRot(0));
		}
	}
}