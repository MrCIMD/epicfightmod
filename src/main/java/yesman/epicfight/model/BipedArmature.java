package yesman.epicfight.model;

import java.util.Map;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;

public class BipedArmature extends Armature {
	public final Joint root;
	public final Joint thighR;
	public final Joint legR;
	public final Joint kneeR;
	public final Joint thighL;
	public final Joint legL;
	public final Joint kneeL;
	public final Joint torso;
	public final Joint chest;
	public final Joint head;
	public final Joint shoulderR;
	public final Joint armR;
	public final Joint handR;
	public final Joint toolR;
	public final Joint elbowR;
	public final Joint shoulderL;
	public final Joint armL;
	public final Joint handL;
	public final Joint toolL;
	public final Joint elbowL;
	
	public BipedArmature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
		super(jointNumber, rootJoint, jointMap);
		this.root = jointMap.get("Root");
		this.thighR = jointMap.get("Thigh_R");
		this.legR = jointMap.get("Leg_R");
		this.kneeR = jointMap.get("Knee_R");
		this.thighL = jointMap.get("Thigh_L");
		this.legL = jointMap.get("Leg_L");
		this.kneeL = jointMap.get("Knee_L");
		this.torso = jointMap.get("Torso");
		this.chest = jointMap.get("Chest");
		this.head = jointMap.get("Head");
		this.shoulderR = jointMap.get("Shoulder_R");
		this.armR = jointMap.get("Arm_R");
		this.handR = jointMap.get("Hand_R");
		this.toolR = jointMap.get("Tool_R");
		this.elbowR = jointMap.get("Elbow_R");
		this.shoulderL = jointMap.get("Shoulder_L");
		this.armL = jointMap.get("Arm_L");
		this.handL = jointMap.get("Hand_L");
		this.toolL = jointMap.get("Tool_L");
		this.elbowL = jointMap.get("Elbow_L");
	}
}