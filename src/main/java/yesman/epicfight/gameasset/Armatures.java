package yesman.epicfight.gameasset;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.model.JsonModelLoader;
import yesman.epicfight.api.model.ModelOld;
import yesman.epicfight.main.EpicFightMod;

@Mod.EventBusSubscriber()
public class Armatures {
	private static final Map<ResourceLocation, Armature> ARMATURES = Maps.newHashMap();
	
	public static Armature BIPED;
	public static Armature CREEPER;
	public static Armature ENDERMAN;
	public static Armature SKELETON;
	public static Armature SPIDER;
	public static Armature IRONGOLEM;
	public static Armature ILLAGER;
	public static Armature WITCH;
	public static Armature RAVAGER;
	public static Armature VEX;
	public static Armature PIGLIN;
	public static Armature HOGLIN;
	public static Armature DRAGON;
	public static Armature WITHER;
	
		//this.bipedOldTexture = register(new ResourceLocation(EpicFightMod.MODID, "entity/biped_old_texture"));
		//this.bipedAlex = register(new ResourceLocation(EpicFightMod.MODID, "entity/biped_slim_arm"));
		//this.villagerZombie = register(new ResourceLocation(EpicFightMod.MODID, "entity/zombie_villager"));
		//this.villagerZombieBody = register(new ResourceLocation(EpicFightMod.MODID, "entity/zombie_villager_body"));
	
	public static void build(ResourceManager resourceManager) {
		ModelBuildEvent.Armatures event = new ModelBuildEvent.Armatures(resourceManager);
		
		BIPED = event.get(EpicFightMod.MODID, "entity/biped");
		CREEPER = event.get(EpicFightMod.MODID, "entity/creeper");
		ENDERMAN = event.get(EpicFightMod.MODID, "entity/enderman");
		SKELETON = event.get(EpicFightMod.MODID, "entity/skeleton");
		SPIDER = event.get(EpicFightMod.MODID, "entity/spider");
		IRONGOLEM = event.get(EpicFightMod.MODID, "entity/iron_golem");
		ILLAGER = event.get(EpicFightMod.MODID, "entity/illager");
		WITCH = event.get(EpicFightMod.MODID, "entity/witch");
		RAVAGER = event.get(EpicFightMod.MODID, "entity/ravager");
		VEX = event.get(EpicFightMod.MODID, "entity/vex");
		PIGLIN = event.get(EpicFightMod.MODID, "entity/piglin");
		HOGLIN = event.get(EpicFightMod.MODID, "entity/hoglin");
		DRAGON = event.get(EpicFightMod.MODID, "entity/dragon");
		WITHER = event.get(EpicFightMod.MODID, "entity/wither");
		
		ModLoader.get().postEvent(event);
		
		ARMATURES.clear();
		ARMATURES.putAll(event.getRegisterMap());		
	}
	
	public Armature register(ResourceLocation rl) {
		
		
		JsonModelLoader modelLoader = new JsonModelLoader(this.resourceManager, rl);
		Armature armature = modelLoader.getArmature();
		
		ARMATURES.put(rl, armature);
		
		return armature;
	}
}