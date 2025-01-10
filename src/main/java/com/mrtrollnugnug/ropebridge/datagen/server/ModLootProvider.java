package com.mrtrollnugnug.ropebridge.datagen.server;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootProvider extends LootTableProvider {
	public ModLootProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, Set.of(), List.of(
			new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)
		), lookupProvider);
	}

	private static class ModBlockLoot extends BlockLootSubProvider {

		protected ModBlockLoot(HolderLookup.Provider provider) {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
		}

		@Override
		protected void generate() {
			for(DeferredHolder<Block, ? extends Block> blockObject : ContentHandler.BLOCKS.getEntries()) {
				this.add(blockObject.get(), noDrop());
			}
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return (Iterable<Block>) ContentHandler.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get())::iterator;
		}
	}
}
