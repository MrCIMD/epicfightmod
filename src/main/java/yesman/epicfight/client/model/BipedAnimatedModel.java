package yesman.epicfight.client.model;

import java.util.Map;

import yesman.epicfight.api.client.model.AnimatedModel;
import yesman.epicfight.api.client.model.ModelPart;

public class BipedAnimatedModel extends AnimatedModel {
	public final ModelPart head;
	public final ModelPart torso;
	public final ModelPart lefrArm;
	public final ModelPart rightArm;
	public final ModelPart leftLeg;
	public final ModelPart rightLeg;
	public final ModelPart hat;
	public final ModelPart jacket;
	public final ModelPart lefrSleeve;
	public final ModelPart rightSleeve;
	public final ModelPart leftPants;
	public final ModelPart rightPants;
	
	public BipedAnimatedModel(float[] positions, float[] noramls, float[] uvs, int[] animationIndices, float[] weights, int[] vCounts, Map<String, ModelPart> parts) {
		super(positions, noramls, uvs, animationIndices, weights, vCounts, parts);
		
		this.head = parts.get("head");
		this.torso = parts.get("torso");
		this.lefrArm = parts.get("lefrArm");
		this.rightArm = parts.get("rightArm");
		this.leftLeg = parts.get("leftLeg");
		this.rightLeg = parts.get("rightLeg");
		this.hat = parts.get("hat");
		this.jacket = parts.get("jacket");
		this.lefrSleeve = parts.get("lefrSleeve");
		this.rightSleeve = parts.get("rightSleeve");
		this.leftPants = parts.get("leftPants");
		this.rightPants = parts.get("rightPants");
	}
}