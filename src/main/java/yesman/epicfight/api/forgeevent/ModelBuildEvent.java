package yesman.epicfight.api.forgeevent;

import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import yesman.epicfight.api.client.model.Mesh;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.client.model.Meshes.MeshContructor;
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
	
	@OnlyIn(Dist.CLIENT)
	public static class MeshBuild extends ModelBuildEvent<Mesh> {
		
		public MeshBuild(ResourceManager resourceManager, Map<ResourceLocation, Mesh> registerMap) {
			super(resourceManager, registerMap);
		}
		
		public <T extends Mesh> T get(String modid, String path, MeshContructor<T> constructor) {
			ResourceLocation rl = new ResourceLocation(modid, "animmodels/" + path + ".json");
			return Meshes.getOrCreateMesh(this.resourceManager, rl, constructor);
		}
	}
}