package yesman.epicfight.client.mesh;

import java.util.Map;

import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.ModelPart;

public class DragonMesh extends AnimatedMesh {
	public final ModelPart head;
	public final ModelPart neck;
	public final ModelPart torso;
	public final ModelPart leftLegFront;
	public final ModelPart rightLegFront;
	public final ModelPart leftLegBack;
	public final ModelPart rightLegBack;
	public final ModelPart leftWing;
	public final ModelPart rightWing;
	public final ModelPart tail;
	
	public DragonMesh(float[] positions, float[] noramls, float[] uvs, float[] weights, AnimatedMesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		super(positions, noramls, uvs, weights, parent, properties, parts);
		
		this.head = this.getOrLogException(parts, "head");
		this.neck = this.getOrLogException(parts, "neck");
		this.torso = this.getOrLogException(parts, "torso");
		this.leftLegFront = this.getOrLogException(parts, "leftLegFront");
		this.rightLegFront = this.getOrLogException(parts, "rightLegFront");
		this.leftLegBack = this.getOrLogException(parts, "leftLegBack");
		this.rightLegBack = this.getOrLogException(parts, "rightLegBack");
		this.leftWing = this.getOrLogException(parts, "leftWing");
		this.rightWing = this.getOrLogException(parts, "rightWing");
		this.tail = this.getOrLogException(parts, "tail");
	}
}