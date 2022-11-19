package yesman.epicfight.api.client.model;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.google.common.collect.Maps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModLoader;
import yesman.epicfight.api.client.model.Mesh.RenderProperties;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;
import yesman.epicfight.api.model.JsonModelLoader;
import yesman.epicfight.client.mesh.CreeperMesh;
import yesman.epicfight.client.mesh.DragonMesh;
import yesman.epicfight.client.mesh.EndermanMesh;
import yesman.epicfight.client.mesh.HoglinMesh;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.mesh.IronGolemMesh;
import yesman.epicfight.client.mesh.PiglinMesh;
import yesman.epicfight.client.mesh.RavagerMesh;
import yesman.epicfight.client.mesh.SpiderMesh;
import yesman.epicfight.client.mesh.VexMesh;
import yesman.epicfight.client.mesh.VillagerMesh;
import yesman.epicfight.client.mesh.WitherMesh;
import yesman.epicfight.main.EpicFightMod;

@OnlyIn(Dist.CLIENT)
public class Meshes implements PreparableReloadListener {
	
	public static final Meshes INSTANCE = new Meshes();
	
	@FunctionalInterface
	public static interface MeshContructor<M extends Mesh> {
		public M invoke(Map<String, float[]> arrayMap, M parent, RenderProperties properties, Map<String, ModelPart> parts);
	}
		
	private static final Map<ResourceLocation, Mesh> MESHES = Maps.newHashMap();
	
	public static HumanoidMesh ALEX;
	public static HumanoidMesh BIPED;
	public static HumanoidMesh BIPED_OLD_TEX;
	public static HumanoidMesh VILLAGER_ZOMBIE;
	public static CreeperMesh CREEPER;
	public static EndermanMesh ENDERMAN;
	public static HumanoidMesh SKELETON;
	public static SpiderMesh SPIDER;
	public static IronGolemMesh IRON_GOLEM;
	public static HumanoidMesh ILLAGER;
	public static VillagerMesh WITCH;
	public static RavagerMesh RAVAGER;
	public static VexMesh VEX;
	public static PiglinMesh PIGLIN;
	public static HoglinMesh HOGLIN;
	public static DragonMesh DRAGON;
	public static WitherMesh WITHER;
	
	public static AnimatedMesh HELMET;
	public static AnimatedMesh HELMET_PIGLIN;
	public static AnimatedMesh HELMET_VILLAGER;
	public static AnimatedMesh CHESTPLATE;
	public static AnimatedMesh LEGGINS;
	public static AnimatedMesh BOOTS;
	
	public static AnimatedMesh FORCE_FIELD;
	public static AnimatedMesh LASER;
	
	public static void build(ResourceManager resourceManager) {
		MESHES.clear();
		ModelBuildEvent.MeshBuild event = new ModelBuildEvent.MeshBuild(resourceManager, MESHES);
		
		ALEX = event.get(EpicFightMod.MODID, "entity/biped_slim_arm", HumanoidMesh::new);
		BIPED = event.get(EpicFightMod.MODID, "entity/biped", HumanoidMesh::new);
		BIPED_OLD_TEX = event.get(EpicFightMod.MODID, "entity/biped_old_texture", HumanoidMesh::new);
		VILLAGER_ZOMBIE = event.get(EpicFightMod.MODID, "entity/zombie_villager", VillagerMesh::new);
		CREEPER = event.get(EpicFightMod.MODID, "entity/creeper", CreeperMesh::new);
		ENDERMAN = event.get(EpicFightMod.MODID, "entity/enderman", EndermanMesh::new);
		SKELETON = event.get(EpicFightMod.MODID, "entity/skeleton", HumanoidMesh::new);
		SPIDER = event.get(EpicFightMod.MODID, "entity/spider", SpiderMesh::new);
		IRON_GOLEM = event.get(EpicFightMod.MODID, "entity/iron_golem", IronGolemMesh::new);
		ILLAGER = event.get(EpicFightMod.MODID, "entity/illager", VillagerMesh::new);
		WITCH = event.get(EpicFightMod.MODID, "entity/witch", VillagerMesh::new);
		RAVAGER = event.get(EpicFightMod.MODID, "entity/ravager", RavagerMesh::new);
		VEX = event.get(EpicFightMod.MODID, "entity/vex", VexMesh::new);
		PIGLIN = event.get(EpicFightMod.MODID, "entity/piglin", PiglinMesh::new);
		HOGLIN = event.get(EpicFightMod.MODID, "entity/hoglin", HoglinMesh::new);
		DRAGON = event.get(EpicFightMod.MODID, "entity/dragon", DragonMesh::new);
		WITHER = event.get(EpicFightMod.MODID, "entity/wither", WitherMesh::new);
		FORCE_FIELD = event.get(EpicFightMod.MODID, "particle/force_field", AnimatedMesh::new);
		LASER = event.get(EpicFightMod.MODID, "particle/laser", AnimatedMesh::new);
		
		HELMET = event.get(EpicFightMod.MODID, "armor/helmet", AnimatedMesh::new);
		HELMET_PIGLIN = event.get(EpicFightMod.MODID, "armor/piglin_helmet", AnimatedMesh::new);
		HELMET_VILLAGER = event.get(EpicFightMod.MODID, "armor/villager_helmet", AnimatedMesh::new);
		CHESTPLATE = event.get(EpicFightMod.MODID, "armor/chestplate", AnimatedMesh::new);
		LEGGINS = event.get(EpicFightMod.MODID, "armor/leggins", AnimatedMesh::new);
		BOOTS = event.get(EpicFightMod.MODID, "armor/boots", AnimatedMesh::new);
		
		ModLoader.get().postEvent(event);
	}
	
	@SuppressWarnings("unchecked")
	public static <M extends Mesh> M getOrCreateMesh(ResourceManager rm, ResourceLocation rl, MeshContructor<M> constructor) {
		return (M) MESHES.computeIfAbsent(rl, (key) -> {
			JsonModelLoader jsonModelLoader = new JsonModelLoader(rm, rl);
			return jsonModelLoader.loadAnimatedMesh(constructor);
		});
	}
	
	public static void addMesh(ResourceLocation rl, AnimatedMesh animatedMesh) {
		MESHES.put(rl, animatedMesh);
	}
	
	@Override
	public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
		return CompletableFuture.runAsync(() -> {
			Meshes.build(resourceManager);
		}, gameExecutor).thenCompose(stage::wait);
	}
}