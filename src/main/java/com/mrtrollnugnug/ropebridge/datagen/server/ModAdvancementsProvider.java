package com.mrtrollnugnug.ropebridge.datagen.server;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementsProvider extends ForgeAdvancementProvider {
	public ModAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
		super(output, registries, existingFileHelper, List.of(new ModAdvancementGenerator()));
	}

	public static class ModAdvancementGenerator implements AdvancementGenerator {

		@Override
		public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
			//Root advancement
			Advancement root = Advancement.Builder.advancement()
				.display(rootDisplay(ContentHandler.rope.get(), advancementPrefix("root.title"),
					advancementPrefix("root.desc"), new ResourceLocation("textures/block/oak_log_top.png")))
				.addCriterion("air", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.AIR))
				.save(consumer, rootID("root"));

			//Craft Bridge Builder Advancement
			Advancement craftBridge = Advancement.Builder.advancement()
				.parent(root)
				.display(simpleDisplay(ContentHandler.bridge_builder.get(), "craft_bridge_builder"))
				.addCriterion("craft_bridge_builder", InventoryChangeTrigger.TriggerInstance.hasItems(ContentHandler.bridge_builder.get()))
				.save(consumer, rootID("craft_bridge_builder"));

			//Build Bridge Advancement
			Advancement buildBridge = Advancement.Builder.advancement()
				.parent(craftBridge)
				.display(simpleDisplay(ContentHandler.oak_bridge.get(), "build_bridge"))
				.addCriterion("build_bridge", new ImpossibleTrigger.TriggerInstance())
				.save(consumer, rootID("build_bridge"));


			//Craft Bridge Builder Advancement
			Advancement craftLadder = Advancement.Builder.advancement()
				.parent(root)
				.display(simpleDisplay(ContentHandler.ladder_builder.get(), "craft_ladder_builder"))
				.addCriterion("craft_ladder_builder", InventoryChangeTrigger.TriggerInstance.hasItems(ContentHandler.ladder_builder.get()))
				.save(consumer, rootID("craft_ladder_builder"));

			//Build Bridge Advancement
			Advancement buildLadder = Advancement.Builder.advancement()
				.parent(craftLadder)
				.display(simpleDisplay(ContentHandler.oak_rope_ladder.get(), "build_ladder"))
				.addCriterion("build_ladder", new ImpossibleTrigger.TriggerInstance())
				.save(consumer, rootID("build_ladder"));
		}

		/**
		 * Generate a root DisplayInfo object.
		 *
		 * @param icon       The icon to use.
		 * @param titleKey   The title key.
		 * @param descKey    The description key.
		 * @param background The background texture.
		 * @return The DisplayInfo object.
		 */
		protected static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, @Nullable ResourceLocation background) {
			return new DisplayInfo(new ItemStack(icon.asItem()),
				Component.translatable(titleKey),
				Component.translatable(descKey),
				background, FrameType.TASK, true, true, false);
		}

		/**
		 * Generate a simple DisplayInfo object.
		 *
		 * @param icon The icon to use.
		 * @param name The name of the advancement.
		 * @return The DisplayInfo object.
		 */
		protected static DisplayInfo simpleDisplay(ItemLike icon, String name) {
			return new DisplayInfo(new ItemStack(icon.asItem()),
				Component.translatable(advancementPrefix(name + ".title")),
				Component.translatable(advancementPrefix(name + ".desc")),
				null, FrameType.TASK, true, false, false);
		}

		/**
		 * Generate an advancement prefix.
		 *
		 * @param name The name of the advancement.
		 * @return The prefix.
		 */
		private static String advancementPrefix(String name) {
			return "advancement." + Constants.MOD_ID + "." + name;
		}

		/**
		 * Generate a root advancement ID.
		 *
		 * @param name The name of the advancement.
		 * @return The advancement ID.
		 */
		private static String rootID(String name) {
			return Constants.modLoc("main/" + name).toString();
		}
	}
}
