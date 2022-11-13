package yesman.epicfight.api.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.main.EpicFightMod;

public class Armature {
	private final Map<Integer, Joint> jointById;
	private final Map<String, Joint> jointByName;
	private final Map<String, Integer> pathIndexMap;
	private final int jointNumber;
	
	private final Joint rootJoint;
	
	public Armature(int jointNumber, Joint rootJoint, Map<String, Joint> jointMap) {
		this.jointNumber = jointNumber;
		this.rootJoint = rootJoint;
		this.jointByName = jointMap;
		this.jointById = Maps.newHashMap();
		this.pathIndexMap = Maps.newHashMap();
		this.jointByName.values().forEach((joint) -> {
			this.jointById.put(joint.getId(), joint);
		});
	}
	
	protected Joint getOrLogException(Map<String, Joint> jointMap, String name) {
		if (!jointMap.containsKey(name)) {
			EpicFightMod.LOGGER.info("Cannot find the joint named " + name + " in " + this.getClass().getCanonicalName());
			return Joint.EMPTY;
		}
		
		return jointMap.get(name);
	}
	
	public OpenMatrix4f[] getJointTransforms() {
		OpenMatrix4f[] jointMatrices = new OpenMatrix4f[this.jointNumber];
		this.jointToTransformMatrixArray(this.rootJoint, jointMatrices);
		return jointMatrices;
	}

	public Joint searchJointById(int id) {
		return this.jointById.get(id);
	}

	public Joint searchJointByName(String name) {
		return this.jointByName.get(name);
	}
	
	public Collection<Joint> getJoints() {
		return this.jointByName.values();
	}
	
	public int searchPathIndex(String joint) {
		if (this.pathIndexMap.containsKey(joint)) {
			return this.pathIndexMap.get(joint);
		} else {
			String pathIndex = this.rootJoint.searchPath(new String(""), joint);
			int pathIndex2Int = 0;
			if (pathIndex == null) {
				throw new IllegalArgumentException("failed to get joint path index for " + joint);
			} else {
				pathIndex2Int = (pathIndex.length() == 0) ? -1 : Integer.parseInt(pathIndex);
				this.pathIndexMap.put(joint, pathIndex2Int);
			}
			return pathIndex2Int;
		}
	}
	
	public void initializeTransform() {
		this.rootJoint.initializeAnimationTransform();
	}

	public int getJointNumber() {
		return this.jointNumber;
	}

	public Joint getRootJoint() {
		return this.rootJoint;
	}

	private void jointToTransformMatrixArray(Joint joint, OpenMatrix4f[] jointMatrices) {
		OpenMatrix4f result = OpenMatrix4f.mul(joint.getAnimatedTransform(), joint.getInversedModelTransform(), null);
		jointMatrices[joint.getId()] = result;
		
		for (Joint childJoint : joint.getSubJoints()) {
			this.jointToTransformMatrixArray(childJoint, jointMatrices);
		}
	}
	
	public Armature deepCopy() {
		Map<String, Joint> oldToNewJoint = Maps.newHashMap();
		oldToNewJoint.put("empty", Joint.EMPTY);
		
		Joint newRoot = this.copyHierarchy(this.rootJoint, oldToNewJoint);
		newRoot.initShortcut(new OpenMatrix4f());
		
		Armature newArmature = null;
		
		try {
			Constructor<? extends Armature> constructor = this.getClass().getConstructor(int.class, Joint.class, Map.class);
			newArmature = constructor.newInstance(this.jointNumber, newRoot, oldToNewJoint);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return newArmature;
	}
	
	private Joint copyHierarchy(Joint joint, Map<String, Joint> oldToNewJoint) {
		if (joint == Joint.EMPTY) {
			return Joint.EMPTY;
		}
		
		Joint newJoint = new Joint(joint.getName(), joint.getId(), joint.getLocalTrasnform());
		oldToNewJoint.put(joint.getName(), newJoint);
		
		for (Joint subJoint : joint.getSubJoints()) {
			newJoint.addSubJoint(this.copyHierarchy(subJoint, oldToNewJoint));
		}
		
		return newJoint;
	}
}