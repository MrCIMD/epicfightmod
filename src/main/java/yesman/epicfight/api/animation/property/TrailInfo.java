package yesman.epicfight.api.animation.property;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.phys.Vec3;

public class TrailInfo {
	public final Vec3 start;
	public final Vec3 end;
	public final ParticleType<?> particle;
	
	private TrailInfo(Vec3 start, Vec3 end, ParticleType<?> particle) {
		this.start = start;
		this.end = end;
		this.particle = particle;
	}
}