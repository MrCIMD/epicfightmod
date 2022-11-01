package yesman.epicfight.api.forgeevent;

import java.util.Map;
import java.util.function.BiFunction;

import com.mojang.datafixers.util.Pair;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import yesman.epicfight.skill.Skill;

public class SkillBuildEvent extends Event implements IModBusEvent {
	protected final Map<ResourceLocation, Pair<? extends Skill.Builder<?>, BiFunction<? extends Skill.Builder<?>, CompoundTag, ? extends Skill>>> builders;
	protected final Map<ResourceLocation, Skill> skills;
	protected final Map<ResourceLocation, Skill> learnableSkills;
	protected final Map<ResourceLocation, CompoundTag> parameters;
	
	public SkillBuildEvent(Map<ResourceLocation, Pair<? extends Skill.Builder<?>, BiFunction<? extends Skill.Builder<?>, CompoundTag, ? extends Skill>>> builders,
			Map<ResourceLocation, Skill> skills, Map<ResourceLocation, Skill> learnableSkills, Map<ResourceLocation, CompoundTag> parameters) {
		this.builders = builders;
		this.skills = skills;
		this.learnableSkills = learnableSkills;
		this.parameters = parameters;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Skill, B extends Skill.Builder<T>> T build(String modid, String name) {
		try {
			ResourceLocation registryName = new ResourceLocation(modid, name);
			Pair<B, BiFunction<B, CompoundTag, T>> pair = (Pair<B, BiFunction<B, CompoundTag, T>>) (Object)this.builders.get(registryName);
			
			if (pair == null) {
				Exception e = new IllegalArgumentException("Can't find the skill " + registryName + " in the registry");
				e.printStackTrace();
			}
			
			CompoundTag parameters = this.parameters.get(new ResourceLocation(modid, name));
			
			if (parameters == null) {
				Exception e = new IllegalArgumentException("Can't find the parameter for the skill " + registryName + " in the registry");
				e.printStackTrace();
				parameters = new CompoundTag();
			}
			
			T skill = pair.getSecond().apply(pair.getFirst(), parameters);
			
			if (skill != null) {
				this.skills.put(registryName, skill);
				
				if (skill.getCategory().learnable()) {
					this.learnableSkills.put(registryName, skill);
				}
			}
			
			return skill;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}