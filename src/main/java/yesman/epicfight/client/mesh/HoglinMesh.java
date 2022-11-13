package yesman.epicfight.client.mesh;

import java.util.Map;

import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.ModelPart;

public class HoglinMesh extends AnimatedMesh {
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart leftFrontLeg;
	public final ModelPart rightFrontLeg;
	public final ModelPart leftBackLeg;
	public final ModelPart rightBackLeg;
	
	public HoglinMesh(float[] positions, float[] noramls, float[] uvs, float[] weights, AnimatedMesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		super(positions, noramls, uvs, weights, parent, properties, parts);
		
		this.head = this.getOrLogException(parts, "head");
		this.body = this.getOrLogException(parts, "body");
		this.leftFrontLeg = this.getOrLogException(parts, "leftFrontLeg");
		this.rightFrontLeg = this.getOrLogException(parts, "rightFrontLeg");
		this.leftBackLeg = this.getOrLogException(parts, "leftBackLeg");
		this.rightBackLeg = this.getOrLogException(parts, "rightBackLeg");
	}
}