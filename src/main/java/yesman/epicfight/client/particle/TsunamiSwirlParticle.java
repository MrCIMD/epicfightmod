package yesman.epicfight.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.Mesh.RawMesh;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class TsunamiSwirlParticle extends CustomModelParticle<RawMesh> {
	private LivingEntityPatch<?> caster;
	
	public TsunamiSwirlParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, RawMesh particleMesh) {
		super(level, x, y, z, xd, yd, zd, particleMesh);
		this.lifetime = 16;
		this.hasPhysics = false;
		this.rCol = 0.0F;
		this.gCol = 162.0F / 255.0F;
		this.bCol = 232.0F / 255.0F;
		
		Entity entity = level.getEntity((int)Double.doubleToLongBits(yd));
		
		if (entity != null) {
			this.caster = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
			
			float yaw = 180.0F - entity.getYRot();
			this.yawO = yaw;
			this.yaw = yaw;
		}
		
		this.setBoundingBox(new AABB(x - 3.0D, y - 3.0D, z - 3.0D, x + 3.0D, y + 3.0D, z + 3.0D));
	}
	
	@Override
	public ParticleRenderType getRenderType() {
		return EpicFightParticleRenderTypes.TRANSLUCENT_GLOWING;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (this.caster != null) {
			Vec3 casterPosition = this.caster.getOriginal().position();
			this.xo = this.x;
			this.yo = this.y;
			this.zo = this.z;
			this.x = casterPosition.x;
			this.y = casterPosition.y + 0.75F;
			this.z = casterPosition.z;
		}
		
		this.roll -= 52.0F * ((25 - this.age) / 20.0F);
		
		for (int x = -1; x <= 1; x += 2) {
			for (int z = -1; z <= 1; z += 2) {
				Vec3 rand = new Vec3(Math.random() * x, Math.random(), Math.random() * z).normalize().scale(2.0D);
				this.level.addParticle(EpicFightParticles.TSUNAMI_SPLASH.get(), this.x + rand.x, this.y + rand.y - 1.0D, this.z + rand.z, rand.x * 0.1D, rand.y * 0.1D, rand.z * 0.1D);
			}
		}
	}
	
	@Override
	protected void setupPoseStack(PoseStack poseStack, Camera camera, float partialTicks) {
		super.setupPoseStack(poseStack, camera, partialTicks);
	}
	
	@Override
	public int getLightColor(float light) {
		return super.getLightColor(light);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new TsunamiSwirlParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, null);
		}
	}
}