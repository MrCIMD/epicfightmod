package yesman.epicfight.data.loot;

import java.util.List;
import java.util.Random;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.config.ConfigManager;
import yesman.epicfight.world.item.EpicFightItems;
import yesman.epicfight.world.item.SkillBookItem;

public class SkillBookModifier extends LootModifier {
	protected SkillBookModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}
	
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
		int dropChanceModifier = 100 + ConfigManager.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
		int dropChanceAntiModifier = 100 - ConfigManager.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
		float dropChance = dropChanceAntiModifier > 0.0F ? (float)(dropChanceModifier) / (float)(dropChanceAntiModifier * 40) : 1.0F;
		
		if (entity instanceof Monster) {
			Random random = new Random();
			
			if (random.nextFloat() < dropChance) {
				ItemStack skillBook = new ItemStack(EpicFightItems.SKILLBOOK.get());
				SkillBookItem.setContainingSkill(SkillManager.getRandomLearnableSkillName(), skillBook);
				generatedLoot.add(skillBook);
			}
		}
		
		return generatedLoot;
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<SkillBookModifier> {
        @Override
        public SkillBookModifier read(ResourceLocation name, JsonObject object, LootItemCondition[] conditionsIn) {
            return new SkillBookModifier(conditionsIn);
        }
        
        @Override
        public JsonObject write(SkillBookModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            return json;
        }
    }
}