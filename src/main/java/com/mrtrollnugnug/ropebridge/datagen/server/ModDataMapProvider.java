package com.mrtrollnugnug.ropebridge.datagen.server;

import com.mrtrollnugnug.ropebridge.datamap.SlabMap;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
	public ModDataMapProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather(Provider provider) {
		final Builder<SlabMap, Block> slabs = builder(ContentHandler.SLAB_MAP);

		slabs.add(Blocks.OAK_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.oak_bridge.get(), ContentHandler.oak_rope_ladder.get()), false);
		slabs.add(Blocks.BIRCH_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.birch_bridge.get(), ContentHandler.birch_rope_ladder.get()), false);
		slabs.add(Blocks.JUNGLE_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.jungle_bridge.get(), ContentHandler.jungle_rope_ladder.get()), false);
		slabs.add(Blocks.SPRUCE_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.spruce_bridge.get(), ContentHandler.spruce_rope_ladder.get()), false);
		slabs.add(Blocks.ACACIA_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.acacia_bridge.get(), ContentHandler.acacia_rope_ladder.get()), false);
		slabs.add(Blocks.CHERRY_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.cherry_bridge.get(), ContentHandler.cherry_rope_ladder.get()), false);
		slabs.add(Blocks.DARK_OAK_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.dark_oak_bridge.get(), ContentHandler.dark_oak_rope_ladder.get()), false);
		slabs.add(Blocks.MANGROVE_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.mangrove_bridge.get(), ContentHandler.mangrove_rope_ladder.get()), false);
		slabs.add(Blocks.BAMBOO_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.bamboo_bridge.get(), ContentHandler.bamboo_rope_ladder.get()), false);
		slabs.add(Blocks.CRIMSON_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.crimson_bridge.get(), ContentHandler.crimson_rope_ladder.get()), false);
		slabs.add(Blocks.WARPED_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.warped_bridge.get(), ContentHandler.warped_rope_ladder.get()), false);

		slabs.add(Blocks.OAK_SLAB.builtInRegistryHolder(), new SlabMap(ContentHandler.oak_bridge.get(), ContentHandler.oak_rope_ladder.get()), false);
	}
}
