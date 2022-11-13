package yesman.epicfight.client.mesh;

import java.util.Map;

import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.ModelPart;

public class SpiderMesh extends AnimatedMesh {
	public final ModelPart head;
	public final ModelPart middleStomach;
	public final ModelPart bottomStomach;
	public final ModelPart leftLeg1;
	public final ModelPart leftLeg2;
	public final ModelPart leftLeg3;
	public final ModelPart leftLeg4;
	public final ModelPart rightLeg1;
	public final ModelPart rightLeg2;
	public final ModelPart rightLeg3;
	public final ModelPart rightLeg4;
	
	public SpiderMesh(float[] positions, float[] noramls, float[] uvs, float[] weights, AnimatedMesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		super(positions, noramls, uvs, weights, parent, properties, parts);
		
		this.head = this.getOrLogException(parts, "head");
		this.middleStomach = this.getOrLogException(parts, "middleStomach");
		this.bottomStomach = this.getOrLogException(parts, "bottomStomach");
		this.leftLeg1 = this.getOrLogException(parts, "leftLeg1");
		this.leftLeg2 = this.getOrLogException(parts, "leftLeg2");
		this.leftLeg3 = this.getOrLogException(parts, "leftLeg3");
		this.leftLeg4 = this.getOrLogException(parts, "leftLeg4");
		this.rightLeg1 = this.getOrLogException(parts, "rightLeg1");
		this.rightLeg2 = this.getOrLogException(parts, "rightLeg2");
		this.rightLeg3 = this.getOrLogException(parts, "rightLeg3");
		this.rightLeg4 = this.getOrLogException(parts, "rightLeg4");
	}
}