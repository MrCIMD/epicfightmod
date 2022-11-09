package yesman.epicfight.api.client.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.AnimatedModel.RenderProperties;
import yesman.epicfight.api.model.JsonModelLoader;
import yesman.epicfight.api.model.ModelOld;

@OnlyIn(Dist.CLIENT)
public class ClientModel extends ModelOld {
	protected AnimatedModel mesh;
	protected RenderProperties properties;
	
	public ClientModel(ResourceLocation location) {
		this(location, null);
	}
	
	public ClientModel(ResourceLocation location, AnimatedModel mesh) {
		super(location);
		this.mesh = mesh;
		this.properties = RenderProperties.DEFAULT;
	}
	
	public boolean loadMeshAndProperties(ResourceManager resourceManager) {
		JsonModelLoader loader = new JsonModelLoader(resourceManager, this.getLocation());
		
		if (loader.isValidSource()) {
			ResourceLocation parent = loader.getParent();
			
			if (parent == null) {
				this.mesh = loader.loadAnimatedModel();
			} else {
				ClientModel model = AnimatedModels.LOGICAL_CLIENT.get(parent);
				if (model == null) {
					throw new IllegalStateException("the parent location " + parent + " not exists!");
				}
				
				this.mesh = AnimatedModels.LOGICAL_CLIENT.get(parent).loadAnimatedModel();
			}
			
			this.properties = loader.getRenderProperties();
			
			return true;
		}
		
		return false;
	}
	
	public RenderProperties getProperties() {
		return this.properties;
	}
	
	public AnimatedModel getMesh() {
		return this.mesh;
	}
}