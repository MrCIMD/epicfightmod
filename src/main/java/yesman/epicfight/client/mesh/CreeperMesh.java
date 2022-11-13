package yesman.epicfight.client.mesh;

import java.util.Map;

import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.ModelPart;

public class CreeperMesh extends AnimatedMesh {
	public final ModelPart head;
	public final ModelPart torso;
	public final ModelPart legRF;
	public final ModelPart legLF;
	public final ModelPart legRB;
	public final ModelPart legLB;
	
	public CreeperMesh(float[] positions, float[] noramls, float[] uvs, float[] weights, AnimatedMesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		super(positions, noramls, uvs, weights, parent, properties, parts);
		
		this.head = this.getOrLogException(parts, "head");
		this.torso = this.getOrLogException(parts, "torso");
		this.legRF = this.getOrLogException(parts, "legRF");
		this.legLF = this.getOrLogException(parts, "legLF");
		this.legRB = this.getOrLogException(parts, "legRB");
		this.legLB = this.getOrLogException(parts, "legLB");
	}
}