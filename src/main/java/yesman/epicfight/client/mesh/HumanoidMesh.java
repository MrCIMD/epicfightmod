package yesman.epicfight.client.mesh;

import java.util.Map;

import net.minecraft.world.entity.EquipmentSlot;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.client.model.ModelPart;

public class HumanoidMesh extends AnimatedMesh {
	public final ModelPart head;
	public final ModelPart torso;
	public final ModelPart lefrArm;
	public final ModelPart rightArm;
	public final ModelPart leftLeg;
	public final ModelPart rightLeg;
	public final ModelPart hat;
	public final ModelPart jacket;
	public final ModelPart leftSleeve;
	public final ModelPart rightSleeve;
	public final ModelPart leftPants;
	public final ModelPart rightPants;
	
	public HumanoidMesh(Map<String, float[]> arrayMap, AnimatedMesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		super(arrayMap, parent, properties, parts);
		
		this.head = this.getOrLogException(parts, "head");
		this.torso = this.getOrLogException(parts, "torso");
		this.lefrArm = this.getOrLogException(parts, "leftArm");
		this.rightArm = this.getOrLogException(parts, "rightArm");
		this.leftLeg = this.getOrLogException(parts, "leftLeg");
		this.rightLeg = this.getOrLogException(parts, "rightLeg");
		
		this.hat = this.getOrLogException(parts, "hat");
		this.jacket = this.getOrLogException(parts, "jacket");
		this.leftSleeve = this.getOrLogException(parts, "leftSleeve");
		this.rightSleeve = this.getOrLogException(parts, "rightSleeve");
		this.leftPants = this.getOrLogException(parts, "leftPants");
		this.rightPants = this.getOrLogException(parts, "rightPants");
	}
	
	public AnimatedMesh getArmorModel(EquipmentSlot slot) {
		switch (slot) {
		case HEAD:
			return Meshes.HELMET;
		case CHEST:
			return Meshes.CHESTPLATE;
		case LEGS:
			return Meshes.LEGGINS;
		case FEET:
			return Meshes.BOOTS;
		default:
			return null;
		}
	}
}