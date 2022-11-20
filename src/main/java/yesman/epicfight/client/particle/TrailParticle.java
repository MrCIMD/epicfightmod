package yesman.epicfight.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class TrailParticle extends Particle {
	
	protected TrailParticle(ClientLevel level, LivingEntityPatch<?> entitypatch, StaticAnimation animation) {
		super(level, 0, 0, 0);
		
		
		
		
		
	}
	
	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
		
	}
	
	@Override
	public ParticleRenderType getRenderType() {
		return null;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;
		
		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}
		
		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			int eid = (int)Double.doubleToLongBits(x);
			int modid = (int)Double.doubleToLongBits(y);
			int animid = (int)Double.doubleToLongBits(z);
			
			Entity entity = level.getEntity(eid);
			LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
			
			return new TrailParticle(level, entitypatch, EpicFightMod.getInstance().animationManager.findAnimationById(modid, animid));
		}
	}
}