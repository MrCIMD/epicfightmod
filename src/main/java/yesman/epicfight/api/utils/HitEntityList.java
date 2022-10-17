package yesman.epicfight.api.utils;

import java.util.List;
import java.util.function.BiFunction;

import com.google.common.collect.Lists;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class HitEntityList {
	private List<Entity> hitEntites;
	private int index;
	
	public HitEntityList(LivingEntityPatch<?> attacker, List<Entity> entities, Priority priority) {
		this.index = -1;
		this.hitEntites = priority.sort(attacker, entities);
	}
	
	public Entity getEntity() {
		return this.hitEntites.get(this.index);
	}
	
	public boolean next() {
		this.index++;
		return this.hitEntites.size() > this.index ? true : false;
	}
	
	public static enum Priority {
		DISTANCE((attacker, list) -> {
			List<Double> distanceToAttacker = Lists.<Double>newArrayList();
			List<Entity> hitEntites = Lists.<Entity>newArrayList();
			
			Outer:
			for (Entity entity : list) {
				double distance = attacker.getOriginal().distanceToSqr(entity);
				int index = 0;
				
				for (; index < hitEntites.size(); index++) {
					if (distance < distanceToAttacker.get(index)) {
						hitEntites.add(index, entity);
						distanceToAttacker.add(index, distance);
						continue Outer;
					}
				}
				
				hitEntites.add(index, entity);
				distanceToAttacker.add(index, distance);
			}
			return hitEntites;
		}),
		
		TARGET((attacker, list) -> {
			List<Double> distanceToAttacker = Lists.<Double>newArrayList();
			List<Entity> hitEntites = Lists.<Entity>newArrayList();
			
			Outer:
			for (Entity entity : list) {
				double distance = attacker.getOriginal().distanceToSqr(entity);
				int index = 0;
				
				if (entity.equals(attacker.getTarget())) {
					hitEntites.add(0, entity);
					distanceToAttacker.add(0, 0.0D);
					continue Outer;
				}
				
				for (; index < hitEntites.size(); index++) {
					if (distance < distanceToAttacker.get(index)) {
						hitEntites.add(index, entity);
						distanceToAttacker.add(index, distance);
						continue Outer;
					}
				}
				
				hitEntites.add(index, entity);
				distanceToAttacker.add(index, distance);
			}
			return hitEntites;
		}),
		HOSTILITY((attacker, list) -> {
			List<Entity> firstTargets = Lists.newArrayList();
			List<Entity> secondTargets = Lists.newArrayList();
			List<Entity> lastTargets = Lists.newArrayList();
			
			Outer:
			for (Entity e : list) {
				if (e instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity)e;
					
					if (attacker.getOriginal().getLastHurtByMob() == livingentity) {
						firstTargets.add(livingentity);
						continue Outer;
					}
				}
				
				if (e instanceof Mob) {
					Mob mob = (Mob)e;
					
					if (attacker.getOriginal().is(mob.getTarget())) {
						firstTargets.add(mob);
						continue Outer;
					} else {
						GoalSelector targetingAi = mob.targetSelector;
						
						for (WrappedGoal goal : targetingAi.getAvailableGoals()) {
							if (goal.getGoal() instanceof NearestAttackableTargetGoal) {
								NearestAttackableTargetGoal<?> targetGoal = (NearestAttackableTargetGoal<?>)goal.getGoal();
								
								if (targetGoal.targetConditions.test(mob, attacker.getOriginal())) {
									secondTargets.add(mob);
									continue Outer;
								}
							}
						}
					}
				}
				
				lastTargets.add(e);
			}
			
			secondTargets.addAll(lastTargets);
			firstTargets.addAll(secondTargets);
			
			return firstTargets;
		});
		
		BiFunction<LivingEntityPatch<?>, List<Entity>, List<Entity>> sortingFunction;
		
		Priority(BiFunction<LivingEntityPatch<?>, List<Entity>, List<Entity>> sortingFunction) {
			this.sortingFunction = sortingFunction;
		}
		
		public List<Entity> sort(LivingEntityPatch<?> attacker, List<Entity> entities) {
			return this.sortingFunction.apply(attacker, entities);
		}
	}
}