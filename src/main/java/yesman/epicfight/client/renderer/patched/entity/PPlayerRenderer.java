package yesman.epicfight.client.renderer.patched.entity;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.client.renderer.patched.layer.EmptyLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedCapeLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;

@OnlyIn(Dist.CLIENT)
public class PPlayerRenderer extends PHumanoidRenderer<AbstractClientPlayer, AbstractClientPlayerPatch<AbstractClientPlayer>, PlayerModel<AbstractClientPlayer>> {
	public PPlayerRenderer() {
		this.addPatchedLayer(ArrowLayer.class, new EmptyLayer<>());
		this.addPatchedLayer(BeeStingerLayer.class, new EmptyLayer<>());
		this.addPatchedLayer(CapeLayer.class, new PatchedCapeLayer());
		this.addPatchedLayer(PlayerItemInHandLayer.class, new PatchedItemInHandLayer<>());
	}
	
	@Override
	protected void prepareModel(AbstractClientPlayer entity, AbstractClientPlayerPatch<AbstractClientPlayer> entitypatch, ClientModel model) {
		super.prepareModel(entity, entitypatch, model);
		model.getMesh().getPart("hat").hidden = !entity.isModelPartShown(PlayerModelPart.HAT);
		model.getMesh().getPart("jacket").hidden = !entity.isModelPartShown(PlayerModelPart.JACKET);
		model.getMesh().getPart("leftPants").hidden = !entity.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
		model.getMesh().getPart("rightPants").hidden = !entity.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
		model.getMesh().getPart("leftSleeve").hidden = !entity.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
		model.getMesh().getPart("rightSleeve").hidden = !entity.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
	}
}