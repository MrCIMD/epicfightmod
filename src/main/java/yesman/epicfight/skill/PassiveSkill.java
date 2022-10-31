package yesman.epicfight.skill;

import net.minecraft.nbt.CompoundTag;

public abstract class PassiveSkill extends Skill {
	public static Skill.Builder<PassiveSkill> createPassiveBuilder() {
		return (new Skill.Builder<PassiveSkill>()).setCategory(SkillCategories.PASSIVE).setResource(Resource.NONE);
	}
	
	public PassiveSkill(Builder<? extends Skill> builder, CompoundTag parameters) {
		super(builder, parameters);
	}
}