package yesman.epicfight.api.client.model;

import java.util.List;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelPart {
	private final List<VertexIndicator> vertices;
	public boolean hidden;
	
	public ModelPart(List<VertexIndicator> vertices) {
		this.vertices = vertices;
	}
	
	public List<VertexIndicator> getVertices() {
		return this.vertices;
	}
}