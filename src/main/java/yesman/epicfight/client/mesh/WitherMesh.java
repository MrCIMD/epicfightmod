package yesman.epicfight.client.mesh;

import java.util.Map;

import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.ModelPart;

public class WitherMesh extends AnimatedMesh {
	public final ModelPart centerHead;
	public final ModelPart leftHead;
	public final ModelPart rightHead;
	public final ModelPart ribcage;
	public final ModelPart tail;
	
	public WitherMesh(float[] positions, float[] noramls, float[] uvs, float[] weights, AnimatedMesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		super(positions, noramls, uvs, weights, parent, properties, parts);
		
		this.centerHead = this.getOrLogException(parts, "centerHead");
		this.leftHead = this.getOrLogException(parts, "leftHead");
		this.rightHead = this.getOrLogException(parts, "rightHead");
		this.ribcage = this.getOrLogException(parts, "ribcage");
		this.tail = this.getOrLogException(parts, "tail");
	}
}