package yesman.epicfight.skill;

import net.minecraft.resources.ResourceLocation;

public abstract class PassiveSkill extends Skill {
	public static Skill.Builder<PassiveSkill> createBuilder(ResourceLocation resourceLocation) {
		return (new Skill.Builder<PassiveSkill>(resourceLocation)).setCategory(SkillCategory.PASSIVE).setConsumption(0.0F).setMaxStack(0).setResource(Resource.NONE).setRequiredXp(5);
	}
	
	public PassiveSkill(Builder<? extends Skill> builder) {
		super(builder);
	}
}