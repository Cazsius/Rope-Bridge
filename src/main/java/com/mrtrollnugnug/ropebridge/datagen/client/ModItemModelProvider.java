package com.mrtrollnugnug.ropebridge.datagen.client;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Constants.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		withRopeParent(ContentHandler.oak_rope_ladder.getId());
		withRopeParent(ContentHandler.birch_rope_ladder.getId());
		withRopeParent(ContentHandler.jungle_rope_ladder.getId());
		withRopeParent(ContentHandler.spruce_rope_ladder.getId());
		withRopeParent(ContentHandler.acacia_rope_ladder.getId());
		withRopeParent(ContentHandler.cherry_rope_ladder.getId());
		withRopeParent(ContentHandler.dark_oak_rope_ladder.getId());
		withRopeParent(ContentHandler.mangrove_rope_ladder.getId());
		withRopeParent(ContentHandler.bamboo_rope_ladder.getId());
		withRopeParent(ContentHandler.crimson_rope_ladder.getId());
		withRopeParent(ContentHandler.warped_rope_ladder.getId());

		withBridgeParent(ContentHandler.oak_bridge.getId());
		withBridgeParent(ContentHandler.birch_bridge.getId());
		withBridgeParent(ContentHandler.jungle_bridge.getId());
		withBridgeParent(ContentHandler.spruce_bridge.getId());
		withBridgeParent(ContentHandler.acacia_bridge.getId());
		withBridgeParent(ContentHandler.cherry_bridge.getId());
		withBridgeParent(ContentHandler.dark_oak_bridge.getId());
		withBridgeParent(ContentHandler.mangrove_bridge.getId());
		withBridgeParent(ContentHandler.bamboo_bridge.getId());
		withBridgeParent(ContentHandler.crimson_bridge.getId());
		withBridgeParent(ContentHandler.warped_bridge.getId());
	}

	private void withBridgeParent(ResourceLocation location) {
		withExistingParent(location.getPath(), modLoc("block/" + location.getPath() + "_block_0"));
	}

	private void withRopeParent(ResourceLocation location) {
		withExistingParent(location.getPath(), modLoc("block/" + location.getPath()));
	}
}
