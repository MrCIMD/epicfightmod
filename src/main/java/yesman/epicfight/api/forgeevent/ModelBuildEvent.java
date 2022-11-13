package yesman.epicfight.api.forgeevent;

import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.client.model.Meshes.AnimatedMeshContructor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.Armatures.ArmatureContructor;

public abstract class ModelBuildEvent<T> extends Event implements IModBusEvent {
	protected final ResourceManager resourceManager;
	protected final Map<ResourceLocation, T> registerMap;
	
	public ModelBuildEvent(ResourceManager resourceManager, Map<ResourceLocation, T> registerMap) {
		this.resourceManager = resourceManager;
		this.registerMap = registerMap;
	}
	
	public static class ArmatureBuild extends ModelBuildEvent<Armature> {
		
		public ArmatureBuild(ResourceManager resourceManager, Map<ResourceLocation, Armature> registerMap) {
			super(resourceManager, registerMap);
		}
		
		public <T extends Armature> T get(String modid, String path, ArmatureContructor<T> constructor) {
			ResourceLocation rl = new ResourceLocation(modid, "animmodels/" + path + ".json");
			return Armatures.getOrCreateArmature(this.resourceManager, rl, constructor);
		}
	}
	
	public static class MeshBuild extends ModelBuildEvent<AnimatedMesh> {
		
		public MeshBuild(ResourceManager resourceManager, Map<ResourceLocation, AnimatedMesh> registerMap) {
			super(resourceManager, registerMap);
		}
		
		public <T extends AnimatedMesh> T get(String modid, String path, AnimatedMeshContructor<T> constructor) {
			ResourceLocation rl = new ResourceLocation(modid, "animmodels/" + path + ".json");
			return Meshes.getOrCreateMesh(this.resourceManager, rl, constructor);
		}
	}
}