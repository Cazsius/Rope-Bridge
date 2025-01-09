package com.mrtrollnugnug.ropebridge.datagen.server;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModLootProvider extends LootTableProvider {
	public ModLootProvider(PackOutput packOutput) {
		super(packOutput, Set.of(), List.of(
			new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)
		));
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
		map.forEach((name, table) -> table.validate(validationtracker));
	}

	private static class ModBlockLoot extends BlockLootSubProvider {

		protected ModBlockLoot() {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags());
		}

		@Override
		protected void generate() {
			for(RegistryObject<Block> blockObject : ContentHandler.BLOCKS.getEntries()) {
				this.add(blockObject.get(), noDrop());
			}
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return (Iterable<Block>) ContentHandler.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
		}
	}
}
