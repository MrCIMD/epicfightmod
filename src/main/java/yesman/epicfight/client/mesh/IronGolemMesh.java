package yesman.epicfight.client.mesh;

import java.util.Map;

import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.ModelPart;

public class IronGolemMesh extends AnimatedMesh {
	public final ModelPart head;
	public final ModelPart chest;
	public final ModelPart core;
	public final ModelPart leftArm;
	public final ModelPart rightArm;
	public final ModelPart leftLeg;
	public final ModelPart rightLeg;
	
	public IronGolemMesh(float[] positions, float[] noramls, float[] uvs, float[] weights, AnimatedMesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		super(positions, noramls, uvs, weights, parent, properties, parts);
		
		this.head = this.getOrLogException(parts, "head");
		this.chest = this.getOrLogException(parts, "chest");
		this.core = this.getOrLogException(parts, "core");
		this.leftArm = this.getOrLogException(parts, "leftArm");
		this.rightArm = this.getOrLogException(parts, "rightArm");
		this.leftLeg = this.getOrLogException(parts, "leftLeg");
		this.rightLeg = this.getOrLogException(parts, "rightLeg");
	}
}