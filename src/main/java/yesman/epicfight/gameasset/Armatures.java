package yesman.epicfight.gameasset;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.model.ModelOld;
import yesman.epicfight.main.EpicFightMod;

public class Armatures {
	protected final Map<ResourceLocation, Armature> armatures = Maps.newHashMap();
	
	public final Armature biped = register(new ResourceLocation(EpicFightMod.MODID, "entity/biped"));
	//public final Armature bipedOldTexture;
	//public final Armature bipedAlex;
	//public final Armature villagerZombie;
	//public final Armature villagerZombieBody;
	public final Armature creeper = register(new ResourceLocation(EpicFightMod.MODID, "entity/creeper"));
	public final Armature enderman = register(new ResourceLocation(EpicFightMod.MODID, "entity/enderman"));
	public final Armature skeleton = register(new ResourceLocation(EpicFightMod.MODID, "entity/skeleton"));
	public final Armature spider = register(new ResourceLocation(EpicFightMod.MODID, "entity/spider"));
	public final Armature ironGolem = register(new ResourceLocation(EpicFightMod.MODID, "entity/iron_golem"));
	public final Armature illager = register(new ResourceLocation(EpicFightMod.MODID, "entity/illager"));
	public final Armature witch = register(new ResourceLocation(EpicFightMod.MODID, "entity/witch"));
	public final Armature ravager = register(new ResourceLocation(EpicFightMod.MODID, "entity/ravager"));
	public final Armature vex = register(new ResourceLocation(EpicFightMod.MODID, "entity/vex"));
	public final Armature piglin = register(new ResourceLocation(EpicFightMod.MODID, "entity/piglin"));
	public final Armature hoglin = register(new ResourceLocation(EpicFightMod.MODID, "entity/hoglin"));
	public final Armature dragon = register(new ResourceLocation(EpicFightMod.MODID, "entity/dragon"));
	public final Armature wither = register(new ResourceLocation(EpicFightMod.MODID, "entity/wither"));
	
		//this.bipedOldTexture = register(new ResourceLocation(EpicFightMod.MODID, "entity/biped_old_texture"));
		//this.bipedAlex = register(new ResourceLocation(EpicFightMod.MODID, "entity/biped_slim_arm"));
		//this.villagerZombie = register(new ResourceLocation(EpicFightMod.MODID, "entity/zombie_villager"));
		//this.villagerZombieBody = register(new ResourceLocation(EpicFightMod.MODID, "entity/zombie_villager_body"));
	
	public Armature register(ResourceLocation rl) {
		ModelOld model = new ModelOld(rl);
		this.armatures.put(rl, model);
		return model;
	}
	
	public Armature get(ResourceLocation location) {
		return this.armatures.get(location);
	}
	
	public void loadArmatures(ResourceManager resourceManager) {
		this.biped.loadArmatureData(resourceManager);
		this.bipedOldTexture.loadArmatureData(this.biped.getArmature());
		this.bipedAlex.loadArmatureData(this.biped.getArmature());
		this.villagerZombie.loadArmatureData(this.biped.getArmature());
		this.creeper.loadArmatureData(resourceManager);
		this.skeleton.loadArmatureData(resourceManager);
		this.enderman.loadArmatureData(resourceManager);
		this.spider.loadArmatureData(resourceManager);
		this.ironGolem.loadArmatureData(resourceManager);
		this.ravager.loadArmatureData(resourceManager);
		this.vex.loadArmatureData(resourceManager);
		this.piglin.loadArmatureData(resourceManager);
		this.illager.loadArmatureData(this.biped.getArmature());
		this.witch.loadArmatureData(this.biped.getArmature());
		this.hoglin.loadArmatureData(resourceManager);
		this.dragon.loadArmatureData(resourceManager);
		this.wither.loadArmatureData(resourceManager);
	}
	
	//public abstract Armatures<?> getModels(boolean isLogicalClient);
}