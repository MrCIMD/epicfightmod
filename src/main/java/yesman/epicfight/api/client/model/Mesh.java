package yesman.epicfight.api.client.model;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import yesman.epicfight.main.EpicFightMod;

public class Mesh {
	public static class RenderProperties {
		public static final RenderProperties DEFAULT = RenderProperties.builder().build();
		
		boolean isTransparent;
		
		public RenderProperties(Builder builder) {
			this.isTransparent = builder.isTransparent;
		}
		
		public boolean isTransparent() {
			return this.isTransparent;
		}
		
		public static RenderProperties.Builder builder() {
			return new Builder();
		}
		
		public static class Builder {
			boolean isTransparent = false;
			
			public RenderProperties.Builder transparency(boolean isTransparent) {
				this.isTransparent = isTransparent;
				return this;
			}
			
			public RenderProperties build() {
				return new RenderProperties(this);
			}
		}
	}
	
	final float[] positions;
	final float[] uvs;
	final float[] normals;
	
	final int totalVertices;
	final Map<String, ModelPart> parts;
	final RenderProperties properties;
	
	public Mesh(Map<String, float[]> arrayMap, Mesh parent, RenderProperties properties, Map<String, ModelPart> parts) {
		this.positions = (parent == null) ? arrayMap.get("positions") : parent.positions;
		this.normals = (parent == null) ? arrayMap.get("normals") : parent.normals;
		this.uvs = (parent == null) ? arrayMap.get("uvs") : parent.uvs;
		this.parts = (parent == null) ? parts : parent.parts;
		this.properties = properties;
		
		int totalV = 0;
		
		for (ModelPart meshpart : parts.values()) {
			totalV += meshpart.getVertices().size();
		}
		
		this.totalVertices = totalV;
	}
	
	protected ModelPart getOrLogException(Map<String, ModelPart> parts, String name) {
		if (!parts.containsKey(name)) {
			EpicFightMod.LOGGER.info("Cannot find the mesh part named " + name + " in " + this.getClass().getCanonicalName());
			return ModelPart.EMPTY;
		}
		
		return parts.get(name);
	}
	
	public ModelPart getPart(String part) {
		return this.parts.get(part);
	}
	
	public void initialize() {
		this.parts.values().forEach((part) -> part.hidden = false);
	}
	
	public void drawRawModel(PoseStack posetStack, VertexConsumer builder, int packedLightIn, float r, float g, float b, float a, int overlayCoord) {
		Matrix4f matrix4f = posetStack.last().pose();
		Matrix3f matrix3f = posetStack.last().normal();
		
		for (ModelPart part : this.parts.values()) {
			if (!part.hidden) {
				for (VertexIndicator vi : part.getVertices()) {
					int pos = vi.position * 3;
					int norm = vi.normal * 3;
					int uv = vi.uv * 2;
					Vector4f posVec = new Vector4f(this.positions[pos], this.positions[pos + 1], this.positions[pos + 2], 1.0F);
					Vector3f normVec = new Vector3f(this.normals[norm], this.normals[norm + 1], this.normals[norm + 2]);
					posVec.transform(matrix4f);
					normVec.transform(matrix3f);
					builder.vertex(posVec.x(), posVec.y(), posVec.z(), r, g, b, a, this.uvs[uv], this.uvs[uv + 1], overlayCoord, packedLightIn, normVec.x(), normVec.y(), normVec.z());
				}
			}
		}
	}
}