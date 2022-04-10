package yesman.epicfight.world.capabilities.entitypatch.mob;

import java.util.Optional;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.game.ExtendedDamageSource.StunType;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.MobCombatBehaviors;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.entity.ai.brain.BrainRemodeler;
import yesman.epicfight.world.entity.ai.brain.task.AttackPatternBehavior;

public class HoglinPatch extends MobPatch<Hoglin> {
	@Override
	public void onJoinWorld(Hoglin entityIn, EntityJoinWorldEvent event) {
		super.onJoinWorld(entityIn, event);
		BrainRemodeler.replaceBehavior(this.original.getBrain(), Activity.FIGHT, 13, RunIf.class, new AttackPatternBehavior(this, MobCombatBehaviors.HOGLIN_HEADBUTT, 0.0D, 1.5D));
		BrainRemodeler.removeBehavior(this.original.getBrain(), Activity.FIGHT, 14, RunIf.class);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void initAnimator(ClientAnimator clientAnimator) {
		clientAnimator.addLivingMotion(LivingMotion.IDLE, Animations.HOGLIN_IDLE);
		clientAnimator.addLivingMotion(LivingMotion.WALK, Animations.HOGLIN_WALK);
		clientAnimator.addLivingMotion(LivingMotion.DEATH, Animations.HOGLIN_DEATH);
	}
	
	@Override
	protected void initAttributes() {
		super.initAttributes();
		this.original.getAttribute(EpicFightAttributes.MAX_STRIKES.get()).setBaseValue(4.0F);
		this.original.getAttribute(EpicFightAttributes.IMPACT.get()).setBaseValue(5.0F);
	}
	
	@Override
	public void updateMotion(boolean considerInaction) {
		super.humanoidEntityUpdateMotion(considerInaction);
	}
	
	@Override
	public <M extends Model> M getEntityModel(Models<M> modelDB) {
		return modelDB.hoglin;
	}
	
	@Override
	public StaticAnimation getHitAnimation(StunType stunType) {
		return null;
	}
	
	@Override
	public SoundEvent getWeaponHitSound(InteractionHand hand) {
		return this.original.isBaby() ? EpicFightSounds.BLUNT_HIT : EpicFightSounds.BLUNT_HIT_HARD;
	}
	
	@Override
	public SoundEvent getSwingSound(InteractionHand hand) {
		return this.original.isBaby() ? EpicFightSounds.WHOOSH : EpicFightSounds.WHOOSH_BIG;
	}
	
	@Override
	public LivingEntity getAttackTarget() {
		Optional<LivingEntity> opt = this.original.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
		return opt.orElse(null);
	}
}