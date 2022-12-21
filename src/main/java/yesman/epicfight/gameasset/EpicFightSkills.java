package yesman.epicfight.gameasset;

import java.util.Set;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.ActiveGuardSkill;
import yesman.epicfight.skill.AirAttack;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.Battojutsu;
import yesman.epicfight.skill.BerserkerSkill;
import yesman.epicfight.skill.BladeRushSkill;
import yesman.epicfight.skill.DodgeSkill;
import yesman.epicfight.skill.EnergizingGuardSkill;
import yesman.epicfight.skill.EviscerateSkill;
import yesman.epicfight.skill.FatalDrawSkill;
import yesman.epicfight.skill.GuardSkill;
import yesman.epicfight.skill.KnockdownWakeupSkill;
import yesman.epicfight.skill.LethalSlicingSkill;
import yesman.epicfight.skill.LiechtenauerSkill;
import yesman.epicfight.skill.PassiveSkill;
import yesman.epicfight.skill.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.Skill.ActivateType;
import yesman.epicfight.skill.Skill.Resource;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.StaminaPillagerSkill;
import yesman.epicfight.skill.StepSkill;
import yesman.epicfight.skill.SwordmasterSkill;
import yesman.epicfight.skill.TechnicianSkill;
import yesman.epicfight.skill.ThunderPunishment;
import yesman.epicfight.skill.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = EpicFightMod.MODID, bus=EventBusSubscriber.Bus.MOD)
public class EpicFightSkills {
	
	/** Default skills **/
	public static Skill BASIC_ATTACK;
	public static Skill AIR_ATTACK;
	public static Skill KNOCKDOWN_WAKEUP;
	/** Dodging skills **/
	public static Skill ROLL;
	public static Skill STEP;
	/** Guard skills **/
	public static Skill GUARD;
	public static Skill ACTIVE_GUARD;
	public static Skill ENERGIZING_GUARD;
	/** Passive skills **/
	public static Skill BERSERKER;
	public static Skill STAMINA_PILLAGER;
	public static Skill SWORD_MASTER;
	public static Skill TECHNICIAN;
	/** Weapon innate skills**/
	public static Skill GUILLOTINE_AXE;
	public static Skill SWEEPING_EDGE;
	public static Skill DANCING_EDGE;
	public static Skill SLAUGHTER_STANCE;
	public static Skill HEARTPIERCER;
	public static Skill GIANT_WHIRLWIND;
	public static Skill FATAL_DRAW;
	public static Skill BATTOJUTSU;
	public static Skill LETHAL_SLICE;
	public static Skill RELENTLESS_COMBO;
	public static Skill LIECHTENAUER;
	public static Skill EVISCERATE;
	public static Skill BLADE_RUSH;
	public static Skill THUNDER_PUNISHMENT;
	public static Skill TSUNAMI;
	/** etc skills **/
	public static Skill CHARGING_JUMP;
	public static Skill GROUND_SLAM;
	
	public static void registerSkills() {
		SkillManager.register(BasicAttack::new, BasicAttack.createBasicAttackBuilder(), EpicFightMod.MODID, "basic_attack");
		SkillManager.register(AirAttack::new, AirAttack.createAirAttackBuilder(), EpicFightMod.MODID, "air_attack");
		SkillManager.register(DodgeSkill::new, DodgeSkill.createDodgeBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/roll_forward"), new ResourceLocation(EpicFightMod.MODID, "biped/skill/roll_backward")), EpicFightMod.MODID, "roll");
		SkillManager.register(StepSkill::new, DodgeSkill.createDodgeBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/step_forward"), new ResourceLocation(EpicFightMod.MODID, "biped/skill/step_backward"), new ResourceLocation(EpicFightMod.MODID, "biped/skill/step_left"), new ResourceLocation(EpicFightMod.MODID, "biped/skill/step_right")), EpicFightMod.MODID, "step");
		SkillManager.register(KnockdownWakeupSkill::new, DodgeSkill.createDodgeBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/knockdown_wakeup_left"), new ResourceLocation(EpicFightMod.MODID, "biped/skill/knockdown_wakeup_right")).setCategory(SkillCategories.KNOCKDOWN_WAKEUP), EpicFightMod.MODID, "knockdown_wakeup");
		
		SkillManager.register(GuardSkill::new, GuardSkill.createGuardBuilder(), EpicFightMod.MODID, "guard");
		SkillManager.register(ActiveGuardSkill::new, ActiveGuardSkill.createActiveGuardBuilder(), EpicFightMod.MODID, "active_guard");
		SkillManager.register(EnergizingGuardSkill::new, EnergizingGuardSkill.createEnergizingGuardBuilder(), EpicFightMod.MODID, "energizing_guard");
		
		SkillManager.register(BerserkerSkill::new, PassiveSkill.createPassiveBuilder(), EpicFightMod.MODID, "berserker");
		SkillManager.register(StaminaPillagerSkill::new, PassiveSkill.createPassiveBuilder(), EpicFightMod.MODID, "stamina_pillager");
		SkillManager.register(SwordmasterSkill::new, PassiveSkill.createPassiveBuilder(), EpicFightMod.MODID, "swordmaster");
		SkillManager.register(TechnicianSkill::new, PassiveSkill.createPassiveBuilder(), EpicFightMod.MODID, "technician");
		
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/sweeping_edge")), EpicFightMod.MODID, "sweeping_edge");
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/dancing_edge")), EpicFightMod.MODID, "dancing_edge");
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/guillotine_axe")), EpicFightMod.MODID, "guillotine_axe");
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/spear_slash")), EpicFightMod.MODID, "slaughter_stance");
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/spear_thrust")), EpicFightMod.MODID, "heartpiercer");
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/giant_whirlwind")), EpicFightMod.MODID, "giant_whirlwind");
		SkillManager.register(FatalDrawSkill::new, WeaponInnateSkill.createWeaponInnateBuilder(), EpicFightMod.MODID, "fatal_draw");
		SkillManager.register(Battojutsu::new, Skill.createBuilder().setCategory(SkillCategories.WEAPON_PASSIVE).setActivateType(ActivateType.ONE_SHOT).setResource(Resource.COOLDOWN), EpicFightMod.MODID, "battojutsu");
		SkillManager.register(LethalSlicingSkill::new, WeaponInnateSkill.createWeaponInnateBuilder(), EpicFightMod.MODID, "lethal_slice");
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/relentless_combo")), EpicFightMod.MODID, "relentless_combo");
		SkillManager.register(LiechtenauerSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.DURATION_INFINITE), EpicFightMod.MODID, "liechtenauer");
		SkillManager.register(EviscerateSkill::new, WeaponInnateSkill.createWeaponInnateBuilder(), EpicFightMod.MODID, "eviscerate");
		SkillManager.register(BladeRushSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.TOGGLE), EpicFightMod.MODID, "blade_rush");
		SkillManager.register(ThunderPunishment::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/thunder_punishment")), EpicFightMod.MODID, "thunder_punishment");
		SkillManager.register(ThunderPunishment::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/tsunami")), EpicFightMod.MODID, "tsunami");
	}
	
	@SubscribeEvent
	public static void buildSkillEvent(SkillBuildEvent onBuild) {
		BASIC_ATTACK = onBuild.build(EpicFightMod.MODID, "basic_attack");
		AIR_ATTACK = onBuild.build(EpicFightMod.MODID, "air_attack");
		ROLL = onBuild.build(EpicFightMod.MODID, "roll");
		STEP = onBuild.build(EpicFightMod.MODID, "step");
		KNOCKDOWN_WAKEUP = onBuild.build(EpicFightMod.MODID, "knockdown_wakeup");
		
		GUARD = onBuild.build(EpicFightMod.MODID, "guard");
		ACTIVE_GUARD = onBuild.build(EpicFightMod.MODID, "active_guard");
		ENERGIZING_GUARD = onBuild.build(EpicFightMod.MODID, "energizing_guard");
		
		BERSERKER = onBuild.build(EpicFightMod.MODID, "berserker");
		STAMINA_PILLAGER = onBuild.build(EpicFightMod.MODID, "stamina_pillager");
		SWORD_MASTER = onBuild.build(EpicFightMod.MODID, "swordmaster");
		TECHNICIAN = onBuild.build(EpicFightMod.MODID, "technician");
		
		WeaponInnateSkill sweepingEdge = onBuild.build(EpicFightMod.MODID, "sweeping_edge");
		sweepingEdge.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1))
					.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F))
					.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.registerPropertiesToAnimation();
		SWEEPING_EDGE = sweepingEdge;
		
		WeaponInnateSkill dancingEdge = onBuild.build(EpicFightMod.MODID, "dancing_edge");
		dancingEdge.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1))
					.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1))
					.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1))
					.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.registerPropertiesToAnimation();
		DANCING_EDGE = dancingEdge;
		
		WeaponInnateSkill guillotineAxe = onBuild.build(EpicFightMod.MODID, "guillotine_axe");
		guillotineAxe.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1))
					.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F))
					.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.registerPropertiesToAnimation();
		GUILLOTINE_AXE = guillotineAxe;
		
		WeaponInnateSkill slaughterStance = onBuild.build(EpicFightMod.MODID, "slaughter_stance");
		slaughterStance.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4))
					.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
					.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.registerPropertiesToAnimation();
		SLAUGHTER_STANCE = slaughterStance;
		
		WeaponInnateSkill heartpiercer = onBuild.build(EpicFightMod.MODID, "heartpiercer");
		heartpiercer.newProperty()
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.registerPropertiesToAnimation();
		HEARTPIERCER = heartpiercer;
		
		WeaponInnateSkill giantWhirlwind = onBuild.build(EpicFightMod.MODID, "giant_whirlwind");
		giantWhirlwind.newProperty()
					.newProperty()
					.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.4F))
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.registerPropertiesToAnimation();
		GIANT_WHIRLWIND = giantWhirlwind;
		
		BATTOJUTSU = onBuild.build(EpicFightMod.MODID, "battojutsu");
		
		WeaponInnateSkill fatalDraw = onBuild.build(EpicFightMod.MODID, "fatal_draw");
		fatalDraw.newProperty()
					.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F))
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(6))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.registerPropertiesToAnimation();
		FATAL_DRAW = fatalDraw;
		
		WeaponInnateSkill lethalSlice = onBuild.build(EpicFightMod.MODID, "lethal_slice");
		lethalSlice.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(2))
					.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.5F))
					.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
					.addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
					.addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
					.newProperty()
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F))
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2))
					.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.7F))
					.addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP)
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.registerPropertiesToAnimation();
		LETHAL_SLICE = lethalSlice;
		
		WeaponInnateSkill relentlessCombo = onBuild.build(EpicFightMod.MODID, "relentless_combo");
		relentlessCombo.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
					.addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.registerPropertiesToAnimation();
		RELENTLESS_COMBO = relentlessCombo;
		
		LIECHTENAUER = onBuild.build(EpicFightMod.MODID, "liechtenauer");
		
		WeaponInnateSkill eviscerate = onBuild.build(EpicFightMod.MODID, "eviscerate");
		eviscerate.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1))
					.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
					.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1))
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create(), ExtraDamageInstance.TARGET_LOST_HEALTH.create(0.5F)))
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.registerPropertiesToAnimation();
		EVISCERATE = eviscerate;
		
		WeaponInnateSkill bladeRush = onBuild.build(EpicFightMod.MODID, "blade_rush");
		bladeRush.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1))
					.newProperty()
					.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F))
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1))
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
					.addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER)
					.addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
				.registerPropertiesToAnimation();
		BLADE_RUSH = bladeRush;
		
		WeaponInnateSkill thunderPunishment = onBuild.build(EpicFightMod.MODID, "thunder_punishment");
		thunderPunishment.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1))
					.newProperty()
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
				.registerPropertiesToAnimation();
		THUNDER_PUNISHMENT = thunderPunishment;
		
		WeaponInnateSkill tsunami = onBuild.build(EpicFightMod.MODID, "tsunami");
		tsunami.newProperty()
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1))
					.newProperty()
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(100.0F))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
				.registerPropertiesToAnimation();
		TSUNAMI = tsunami;
	}
}