package yesman.epicfight.api.client.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.apache.commons.compress.utils.Lists;

import com.google.common.collect.Maps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.model.BipedAnimatedModel;
import yesman.epicfight.main.EpicFightMod;

@OnlyIn(Dist.CLIENT)
public class Models implements PreparableReloadListener {
	@FunctionalInterface
	public static interface ModelContructor<T extends AnimatedModel> {
		public T constructor(float[] positions, float[] noramls, float[] uvs, int[] animationIndices, float[] weights, int[] vCounts, Map<String, ModelPart> parts);
	}
	
	private static Map<ResourceLocation, ModelContructor> BUILDERS = Maps.newHashMap();
	
	public static BipedAnimatedModel ALEX;
	public static BipedAnimatedModel BIPED;
	public static BipedAnimatedModel BIPED_OLD_TEX;
	public static AnimatedModel VILLAGER_ZOMBIE;
	public static AnimatedModel CREEPER;
	public static AnimatedModel ENDERMAN;
	public static AnimatedModel SKELETON;
	public static AnimatedModel SPIDER;
	public static AnimatedModel IRON_GOLEM;
	public static AnimatedModel ILLAGER;
	public static AnimatedModel WITCH;
	public static AnimatedModel RAVAGER;
	public static AnimatedModel VEX;
	public static AnimatedModel PIGLIN;
	public static AnimatedModel HOGLIN;
	public static AnimatedModel DRAGON;
	public static AnimatedModel WITHER;
	public static AnimatedModel FORCE_FIELD;
	public static AnimatedModel LASER;
	
	public static void registerModels() {
		register(BipedAnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/biped_slim_arm"));
		register(BipedAnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/biped"));
		register(BipedAnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/biped_old_texture"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/zombie_villager"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/creeper"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/enderman"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/skeleton"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/spider"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/iron_golem"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/illager"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/witch"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/ravager"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/vex"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/piglin"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/hoglin"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/dragon"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "entity/wither"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "particle/force_field"));
		register(AnimatedModel::new, new ResourceLocation(EpicFightMod.MODID, "particle/laser"));
	}
	
	public static <M extends AnimatedModel> void register(ModelContructor<M> contructor, ResourceLocation rl) {
		BUILDERS.put(rl, contructor);
	}
	
	/** Entities **/
	//public ClientModel playerFirstPerson;
	//public ClientModel playerFirstPersonAlex;
	//public ClientModel drownedOuterLayer;
	/** Armors **/
	//public ClientModel helmet;
	//public ClientModel chestplate;
	//public ClientModel leggins;
	//public ClientModel boots;
	/** Particles **/
	
	//public Mesh tsunamiSwirl = register(new ResourceLocation(EpicFightMod.MODID, "particle/tsunami_swirl"));
	
	
	
	public void register(ResourceLocation rl, ClientModel model) {
		this.meshes.put(rl, model);
	}
	
	public void loadModels(ResourceManager resourceManager) {
		List<ResourceLocation> emptyResourceLocations = Lists.newArrayList();
		
		this.models.entrySet().forEach((entry) -> {
			if (!entry.getValue().loadMeshAndProperties(resourceManager)) {
				emptyResourceLocations.add(entry.getKey());
			}
		});
		
		emptyResourceLocations.forEach(this.models::remove);
	}
	
	@Override
	public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
		return CompletableFuture.runAsync(() -> {
			this.loadModels(resourceManager);
		}, gameExecutor).thenCompose(stage::wait);
	}
}