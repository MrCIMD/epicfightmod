package yesman.epicfight.api.animation.property;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;

public class TrailInfo {
	public final Vec3 start;
	public final Vec3 end;
	public final Joint joint;
	public final SimpleParticleType particle;
	
	private TrailInfo(Vec3 start, Vec3 end, Joint joint, SimpleParticleType particle) {
		this.start = start;
		this.end = end;
		this.joint = joint;
		this.particle = particle;
	}
	
	public static TrailInfo create(Vec3 start, Vec3 end, Joint joint, SimpleParticleType particle) {
		return new TrailInfo(start, end, joint, particle);
	}
}