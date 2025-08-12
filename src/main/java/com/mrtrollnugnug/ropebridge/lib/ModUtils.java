package com.mrtrollnugnug.ropebridge.lib;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class ModUtils {

	private ModUtils() {
	}

	public static final Map<Block, Pair<DeferredBlock<? extends Block>, DeferredBlock<? extends Block>>> map = new HashMap<>();

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
		sender.displayClientMessage(Component.translatable(message, params), false);
	}

	public static void initMap() {
		map.put(Blocks.OAK_SLAB, Pair.of(ContentHandler.oak_bridge, ContentHandler.oak_rope_ladder));
		map.put(Blocks.BIRCH_SLAB, Pair.of(ContentHandler.birch_bridge, ContentHandler.birch_rope_ladder));
		map.put(Blocks.JUNGLE_SLAB, Pair.of(ContentHandler.jungle_bridge, ContentHandler.jungle_rope_ladder));
		map.put(Blocks.SPRUCE_SLAB, Pair.of(ContentHandler.spruce_bridge, ContentHandler.spruce_rope_ladder));
		map.put(Blocks.ACACIA_SLAB, Pair.of(ContentHandler.acacia_bridge, ContentHandler.acacia_rope_ladder));
		map.put(Blocks.CHERRY_SLAB, Pair.of(ContentHandler.cherry_bridge, ContentHandler.cherry_rope_ladder));
		map.put(Blocks.DARK_OAK_SLAB, Pair.of(ContentHandler.dark_oak_bridge, ContentHandler.dark_oak_rope_ladder));
		map.put(Blocks.MANGROVE_SLAB, Pair.of(ContentHandler.mangrove_bridge, ContentHandler.mangrove_rope_ladder));
		map.put(Blocks.BAMBOO_SLAB, Pair.of(ContentHandler.bamboo_bridge, ContentHandler.bamboo_rope_ladder));
		map.put(Blocks.CRIMSON_SLAB, Pair.of(ContentHandler.crimson_bridge, ContentHandler.crimson_rope_ladder));
		map.put(Blocks.WARPED_SLAB, Pair.of(ContentHandler.warped_bridge, ContentHandler.warped_rope_ladder));
	}

	public static void unlockAdvancement(Player player, ResourceLocation advancementId) {
		if (player instanceof ServerPlayer serverPlayer) {
			MinecraftServer server = serverPlayer.getServer();
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
