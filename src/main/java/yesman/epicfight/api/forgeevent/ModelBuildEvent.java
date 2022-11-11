package yesman.epicfight.api.forgeevent;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import yesman.epicfight.api.client.model.AnimatedModel;
import yesman.epicfight.api.client.model.AnimatedModels.AnimatedModelContructor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.model.JsonModelLoader;

public abstract class ModelBuildEvent<T> extends Event implements IModBusEvent {
	protected final ResourceManager resourceManager;
	protected final Map<ResourceLocation, T> registerMap = Maps.newHashMap();
	
	public ModelBuildEvent(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}
	
	public Map<ResourceLocation, T> getRegisterMap() {
		return this.registerMap;
	}
	
	public static class Armatures extends ModelBuildEvent<Armature> {
		
		public Armatures(ResourceManager resourceManager) {
			super(resourceManager);
		}
		
		public Armature get(String modid, String path) {
			ResourceLocation rl = new ResourceLocation(modid, path);
			JsonModelLoader modelLoader = new JsonModelLoader(this.resourceManager, rl);
			Armature armature = modelLoader.getArmature();
			
			this.registerMap.put(rl, armature);
			
			return armature;
		}
	}
	
	public static class AnimatedModels extends ModelBuildEvent<AnimatedModel> {
		
		public AnimatedModels(ResourceManager resourceManager) {
			super(resourceManager);
		}
		
		public <T extends AnimatedModel> T get(String modid, String path, AnimatedModelContructor<T> builder) {
			ResourceLocation rl = new ResourceLocation(modid, path);
			JsonModelLoader modelLoader = new JsonModelLoader(this.resourceManager, rl);
			T animatedModel = modelLoader.loadAnimatedModel(builder);
			this.registerMap.put(rl, animatedModel);
			
			return animatedModel;
		}
	}
}