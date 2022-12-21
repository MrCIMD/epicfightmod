package yesman.epicfight.api.animation.property;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;

public class TrailInfo {
	public final Vec3 start;
	public final Vec3 end;
	public final SimpleParticleType particle;
	public final Joint joint;
	public final float startTime;
	public final float endTime;
	public final int interpolateCount;
	public final int trailLifetime;
	
	private TrailInfo(Vec3 start, Vec3 end, float startT, float endT, int lifeTime, int interpolations, Joint joint, SimpleParticleType particle) {
		this.start = start;
		this.end = end;
		this.joint = joint;
		this.particle = particle;
		this.startTime = startT;
		this.endTime = endT;
		this.interpolateCount = interpolations;
		this.trailLifetime = lifeTime;
	}
	
	public static TrailInfo create(double startX, double startY, double startZ, double endX, double endY, double endZ
								 , float startT, float endT, int lifeTime, int interpolations, Joint joint, SimpleParticleType particle) {
		return new TrailInfo(new Vec3(startX, startY, startZ), new Vec3(endX, endY, endZ), startT, endT, lifeTime, interpolations, joint, particle);
	}
}