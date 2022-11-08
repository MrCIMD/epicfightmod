package yesman.epicfight.world.capabilities.entitypatch.mob;

import net.minecraft.world.entity.PathfinderMob;
import yesman.epicfight.api.model.ModelOld;
import yesman.epicfight.gameasset.Armatures;

public class ZombieVillagerPatch<T extends PathfinderMob> extends ZombiePatch<T> {
	@Override
	public <M extends ModelOld> M getEntityModel(Armatures<M> modelDB) {
		return modelDB.villagerZombie;
	}
}