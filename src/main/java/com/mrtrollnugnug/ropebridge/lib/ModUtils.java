package com.mrtrollnugnug.ropebridge.lib;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModUtils {

	private ModUtils() {
	}

	/**
	 * Sends a message to a command sender. Can be used for easier message
	 * sending.
	 *
	 * @param sender  The thing to send the message to. This should probably be a
	 *                player.
	 * @param message The message to send. This can be a normal message, however
	 *                translation keys are HIGHLY encouraged!
	 */
	public static void tellPlayer(Player sender, String message, Object... params) {
		sender.sendSystemMessage(Component.translatable(message, params));
	}

	public static Block getSlabToUse(Player player) {
		for (final ItemStack stack : player.getInventory().getNonEquipmentItems()) {
			if (stack.isEmpty()) {
				continue;
			}
			if (stack.is(ItemTags.WOODEN_SLABS)) {
				Block block = Block.byItem(stack.getItem());
				if (isInMap(block)) {
					return block;
				}
			}
		}
		return Blocks.OAK_SLAB;
	}

	public static boolean isInMap(Block block) {
		return block.builtInRegistryHolder().getData(ContentHandler.SLAB_MAP) != null;
	}

	public static void unlockAdvancement(Player player, Identifier advancementId) {
		if (player instanceof ServerPlayer serverPlayer) {
			MinecraftServer server = serverPlayer.level().getServer();
			if (server != null) {
				AdvancementHolder advancementHolder = server.getAdvancements().get(advancementId);
				if (advancementHolder != null) {
					AdvancementProgress advancementprogress = serverPlayer.getAdvancements().getOrStartProgress(advancementHolder);
					if (!advancementprogress.isDone()) {
						for (String s : advancementprogress.getRemainingCriteria()) {
							serverPlayer.getAdvancements().award(advancementHolder, s);
						}
					}
				}
			}
		}
	}
}
