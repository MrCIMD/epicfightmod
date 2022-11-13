package yesman.epicfight.client.mesh;

import java.util.Map;

import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.ModelPart;

public class EndermanMesh extends AnimatedMesh {
	public final ModelPart headTop;
	public final ModelPart headBottom;
	public final ModelPart torso;
	public final ModelPart leftArm;
	public final ModelPart rightArm;
	public final ModelPart leftLeg;
	public final ModelPart rightLeg;
	
	public EndermanMesh(float[] positions, float[] noramls, float[] uvs, float[] weights, AnimatedMesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		super(positions, noramls, uvs, weights, parent, properties, parts);
		
		this.headTop = this.getOrLogException(parts, "headTop");
		this.headBottom = this.getOrLogException(parts, "headBottom");
		this.torso = this.getOrLogException(parts, "torso");
		this.leftArm = this.getOrLogException(parts, "leftArm");
		this.rightArm = this.getOrLogException(parts, "rightArm");
		this.leftLeg = this.getOrLogException(parts, "leftLeg");
		this.rightLeg = this.getOrLogException(parts, "rightLeg");
	}
}