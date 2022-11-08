package yesman.epicfight.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class TrailParticle extends Particle {
	
	protected TrailParticle(ClientLevel level, double x, double y, double z, double entityId) {
		super(level, x, y, z);
		
		int eid = (int)Double.doubleToLongBits(entityId);
		Entity entity = level.getEntity(eid);
		LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
		
		
	}
	
	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
		
	}
	
	@Override
	public ParticleRenderType getRenderType() {
		return null;
	}
}