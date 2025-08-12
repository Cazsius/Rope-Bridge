package com.mrtrollnugnug.ropebridge.datagen;

import com.mrtrollnugnug.ropebridge.datagen.client.ModLanguageProvider;
import com.mrtrollnugnug.ropebridge.datagen.client.ModModelProvider;
import com.mrtrollnugnug.ropebridge.datagen.client.ModSoundProvider;
import com.mrtrollnugnug.ropebridge.datagen.server.ModAdvancementsProvider;
import com.mrtrollnugnug.ropebridge.datagen.server.ModLootProvider;
import com.mrtrollnugnug.ropebridge.datagen.server.ModRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class RopeBridgeDataGen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new ModLootProvider(packOutput, lookupProvider));
		generator.addProvider(true, new ModRecipeProvider.Runner(packOutput, lookupProvider));
		generator.addProvider(true, new ModAdvancementsProvider(packOutput, lookupProvider));

		generator.addProvider(true, new ModLanguageProvider(packOutput));
		generator.addProvider(true, new ModSoundProvider(packOutput));
		generator.addProvider(true, new ModModelProvider(packOutput));
	}

}
