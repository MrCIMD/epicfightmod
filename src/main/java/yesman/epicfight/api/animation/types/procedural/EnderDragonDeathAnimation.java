package yesman.epicfight.api.animation.types.procedural;

import net.minecraft.server.packs.resources.ResourceManager;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.model.ModelOld;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class EnderDragonDeathAnimation extends LongHitAnimation {
	public EnderDragonDeathAnimation(float convertTime, String path, ModelOld model) {
		super(convertTime, path, model);
	}
	
	@Override
	public void loadAnimation(ResourceManager resourceManager) {
		loadBothSide(resourceManager, this);
		this.onLoaded();
	}
	
	@Override
	protected void modifyPose(Pose pose, LivingEntityPatch<?> entitypatch, float time) {
		
	}
}