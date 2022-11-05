package yesman.epicfight.skill;

public abstract class PassiveSkill extends Skill {
	public static Skill.Builder<PassiveSkill> createPassiveBuilder() {
		return (new Skill.Builder<PassiveSkill>()).setCategory(SkillCategories.PASSIVE).setResource(Resource.NONE);
	}
	
	public PassiveSkill(Builder<? extends Skill> builder) {
		super(builder);
	}
}