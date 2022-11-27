package yesman.epicfight.client.particle;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector4f;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.property.TrailInfo;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.CubicBezierCurve;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class TrailParticle extends TextureSheetParticle {
	private final Joint joint;
	private final StaticAnimation animation;
	private final LivingEntityPatch<?> entitypatch;
	private final List<TrailEdge> invisibleTrailEdges;
	private final List<TrailEdge> visibleTrailEdges;
	private final int interpolateCount;
	private final int trailLifetime;
	private boolean animationEnd;
	
	protected TrailParticle(ClientLevel level, LivingEntityPatch<?> entitypatch, Joint joint, StaticAnimation animation, SpriteSet spriteSet) {
		super(level, 0, 0, 0);
		
		this.joint = joint;
		this.entitypatch = entitypatch;
		this.animation = animation;
		this.invisibleTrailEdges = Lists.newLinkedList();
		this.visibleTrailEdges = Lists.newLinkedList();
		this.hasPhysics = false;
		this.interpolateCount = 5;
		this.trailLifetime = 2;
		
		Vec3 entityPos = entitypatch.getOriginal().position();
		
		this.setSize(10.0F, 10.0F);
		this.move(entityPos.x, entityPos.y + entitypatch.getOriginal().getEyeHeight(), entityPos.z);
		this.setSpriteFromAge(spriteSet);
	}
	
	@Override
	public void tick() {
		AnimationPlayer animPlayer = this.entitypatch.getAnimator().getPlayerFor(this.animation);
		boolean playingSameAnimation = (this.animation == animPlayer.getAnimation().getRealAnimation());
		
		if (!playingSameAnimation) {
			this.animationEnd = true;
		}
		
		this.visibleTrailEdges.removeIf(v -> !v.isAlive());
		
		if (this.animationEnd) {
			if (this.visibleTrailEdges.size() == 0) {
				this.remove();
			}
		} else {
			if (this.animation != animPlayer.getAnimation().getRealAnimation() || animPlayer.getElapsedTime() > 0.15F) {
				return;
			}
			
			TrailInfo trailInfo = this.animation.getProperty(StaticAnimationProperty.TRAIL_EFFECT).get();
			Pose prevPose = this.entitypatch.getArmature().getPrevPose();
			Pose middlePose = this.entitypatch.getArmature().getPose(0.5F);
			Pose currentPose = this.entitypatch.getArmature().getCurrentPose();
			Vec3 position = this.entitypatch.getOriginal().position();
			OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float)position.x, (float)position.y, (float)position.z)
											.mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
											.mulBack(this.entitypatch.getModelMatrix(1.0F)));
			OpenMatrix4f prevJointTf = this.entitypatch.getArmature().getBindedTransformFor(prevPose, this.joint).mulFront(modelTf);
			OpenMatrix4f middleJointTf = this.entitypatch.getArmature().getBindedTransformFor(middlePose, this.joint).mulFront(modelTf);
			OpenMatrix4f currentJointTf = this.entitypatch.getArmature().getBindedTransformFor(currentPose, this.joint).mulFront(modelTf);
			Vec3 prevStartPos = OpenMatrix4f.transform(prevJointTf, trailInfo.start);
			Vec3 prevEndPos = OpenMatrix4f.transform(prevJointTf, trailInfo.end);
			Vec3 middleStartPos = OpenMatrix4f.transform(middleJointTf, trailInfo.start);
			Vec3 middleEndPos = OpenMatrix4f.transform(middleJointTf, trailInfo.end);
			Vec3 currentStartPos = OpenMatrix4f.transform(currentJointTf, trailInfo.start);
			Vec3 currentEndPos = OpenMatrix4f.transform(currentJointTf, trailInfo.end);
			
			List<Vec3> finalStartPositions;
			
			List<Vec3> finalEndPositions;
			boolean visibleTrail;
			
			if (animPlayer.getAnimation() instanceof LinkAnimation || animPlayer.getElapsedTime() < 0.05F) {
				finalStartPositions = Lists.newArrayList();
				finalEndPositions = Lists.newArrayList();
				
				finalStartPositions.add(prevStartPos);
				finalStartPositions.add(currentStartPos);
				finalEndPositions.add(prevEndPos);
				finalEndPositions.add(currentEndPos);
				
				if (this.visibleTrailEdges.size() > 0) {
					this.visibleTrailEdges.clear();
				}
				
				visibleTrail = false;
			} else {
				List<Vec3> startPosList = Lists.newArrayList();
				List<Vec3> endPosList = Lists.newArrayList();
				TrailEdge edge1;
				TrailEdge edge2;
				
				if (this.visibleTrailEdges.size() == 0) {
					int lastIdx = this.invisibleTrailEdges.size() - 1;
					
					edge1 = this.invisibleTrailEdges.get(lastIdx - 1);
					edge2 = this.invisibleTrailEdges.get(lastIdx);
				} else {
					edge1 = this.visibleTrailEdges.get(this.visibleTrailEdges.size() - this.interpolateCount - 1);
					edge2 = this.visibleTrailEdges.get(this.visibleTrailEdges.size() - 1);
				}
				
				startPosList.add(edge1.start);
				endPosList.add(edge1.end);
				startPosList.add(edge2.start);
				endPosList.add(edge2.end);
				startPosList.add(middleStartPos);
				endPosList.add(middleEndPos);
				
				startPosList.add(currentStartPos);
				endPosList.add(currentEndPos);
				
				finalStartPositions = CubicBezierCurve.getBezierInterpolatedPoints(startPosList, 1, 3, this.interpolateCount);
				finalEndPositions = CubicBezierCurve.getBezierInterpolatedPoints(endPosList, 1, 3, this.interpolateCount);
				
				visibleTrail = true;
			}
			
			makeTrailEdges(finalStartPositions, finalEndPositions, visibleTrail ? this.visibleTrailEdges : this.invisibleTrailEdges);
		}
	}
	
	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
		if (this.visibleTrailEdges.size() < 1) {
			return;
		}
		
		PoseStack poseStack = new PoseStack();
		int light = this.getLightColor(partialTick);
		this.setupPoseStack(poseStack, camera, partialTick);
		Matrix4f matrix4f = poseStack.last().pose();
		
		int edges = this.visibleTrailEdges.size();
		int parts = (this.interpolateCount + 1) * 2;
		float bigInterval = (1.0F / (float)edges) * parts;
		
		float startU = this.visibleTrailEdges.get(0).lifetime > 1 ? 0.0F : - bigInterval * partialTick;
		float endU = this.visibleTrailEdges.get(edges - 1).lifetime < this.trailLifetime ? 1.0F : 1.0F + bigInterval * (1 - partialTick);
		
		float interval = (endU - startU) / (edges - 1);
		
		//System.out.println( (startU + interval * (edges - 1)) +" "+ endU );
		
		//System.out.println();
		
		for (int i = 0; i < edges - 1; i++) {
			TrailEdge e1 = this.visibleTrailEdges.get(i);
			TrailEdge e2 = this.visibleTrailEdges.get(i + 1);
			Vector4f pos1 = new Vector4f((float)e1.start.x, (float)e1.start.y, (float)e1.start.z, 1.0F);
			Vector4f pos2 = new Vector4f((float)e1.end.x, (float)e1.end.y, (float)e1.end.z, 1.0F);
			Vector4f pos3 = new Vector4f((float)e2.end.x, (float)e2.end.y, (float)e2.end.z, 1.0F);
			Vector4f pos4 = new Vector4f((float)e2.start.x, (float)e2.start.y, (float)e2.start.z, 1.0F);
			
			pos1.transform(matrix4f);
			pos2.transform(matrix4f);
			pos3.transform(matrix4f);
			pos4.transform(matrix4f);
			
			float from = startU + interval * i;
			float to = startU + interval * (i+1);
			
			vertexConsumer.vertex(pos1.x(), pos1.y(), pos1.z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(from, 1.0F).uv2(light).endVertex();
			vertexConsumer.vertex(pos2.x(), pos2.y(), pos2.z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(from, 0.0F).uv2(light).endVertex();
			vertexConsumer.vertex(pos3.x(), pos3.y(), pos3.z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(to, 0.0F).uv2(light).endVertex();
			vertexConsumer.vertex(pos4.x(), pos4.y(), pos4.z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(to, 1.0F).uv2(light).endVertex();
			
			/**
			
			vertexConsumer.vertex(pos1.x(), pos1.y(), pos1.z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(-1, 1.0F).uv2(light).endVertex();
			vertexConsumer.vertex(pos2.x(), pos2.y(), pos2.z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(-1, -1.0F).uv2(light).endVertex();
			vertexConsumer.vertex(pos3.x(), pos3.y(), pos3.z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(1, -1.0F).uv2(light).endVertex();
			vertexConsumer.vertex(pos4.x(), pos4.y(), pos4.z()).color(this.rCol, this.gCol, this.bCol, this.alpha).uv(1, 1.0F).uv2(light).endVertex();
			
			**/
			
			//System.out.println(startU +" "+from+" "+to +" "+ endU);
		}
	}
	
	@Override
	public ParticleRenderType getRenderType() {
		return EpicFightParticleRenderTypes.TRAIL;
	}
	
	protected void setupPoseStack(PoseStack poseStack, Camera camera, float partialTicks) {
		Quaternion rotation = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
		Vec3 vec3 = camera.getPosition();
		float x = (float)-vec3.x();
		float y = (float)-vec3.y();
		float z = (float)-vec3.z();
		
		poseStack.translate(x, y, z);
		poseStack.mulPose(rotation);
	}
	
	private void makeTrailEdges(List<Vec3> startPositions, List<Vec3> endPositions, List<TrailEdge> dest) {
		for (int i = 0; i < startPositions.size(); i++) {
			dest.add(new TrailEdge(startPositions.get(i), endPositions.get(i), this.trailLifetime));
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;
		
		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}
		
		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			int eid = (int)Double.doubleToLongBits(x);
			int modid = (int)Double.doubleToLongBits(y);
			int animid = (int)Double.doubleToLongBits(z);
			int jointId = (int)Double.doubleToLongBits(xSpeed);
			Entity entity = level.getEntity(eid);
			
			if (entity != null) {
				LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
				StaticAnimation animation = EpicFightMod.getInstance().animationManager.findAnimationById(modid, animid);
				
				if (entitypatch != null && animation != null && animation.getProperty(StaticAnimationProperty.TRAIL_EFFECT).isPresent()) {
					return new TrailParticle(level, entitypatch, entitypatch.getArmature().searchJointById(jointId), animation, this.spriteSet);
				}
			}
			
			return null;
		}
	}
	
	private static class TrailEdge {
		final Vec3 start;
		final Vec3 end;
		int lifetime;
		
		public TrailEdge(Vec3 start, Vec3 end, int lifetime) {
			this.start = start;
			this.end = end;
			this.lifetime = lifetime;
		}
		
		boolean isAlive() {
			return --this.lifetime > 0;
		}
	}
}