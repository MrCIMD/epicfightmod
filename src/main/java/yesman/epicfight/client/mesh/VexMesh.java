package yesman.epicfight.client.mesh;

import java.util.Map;

import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.ModelPart;

public class VexMesh extends HumanoidMesh {
	public final ModelPart leftWing;
	public final ModelPart rightWing;
	
	public VexMesh(Map<String, float[]> arrayMap, AnimatedMesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		super(arrayMap, parent, properties, parts);
		
		this.leftWing = this.getOrLogException(parts, "leftWing");
		this.rightWing = this.getOrLogException(parts, "rightWing");
	}
}