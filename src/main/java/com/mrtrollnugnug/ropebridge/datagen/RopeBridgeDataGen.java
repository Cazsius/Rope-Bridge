package com.mrtrollnugnug.ropebridge.datagen;

import com.mrtrollnugnug.ropebridge.datagen.client.ModBlockstateProvider;
import com.mrtrollnugnug.ropebridge.datagen.client.ModItemModelProvider;
import com.mrtrollnugnug.ropebridge.datagen.client.ModLanguageProvider;
import com.mrtrollnugnug.ropebridge.datagen.client.ModSoundProvider;
import com.mrtrollnugnug.ropebridge.datagen.server.ModAdvancementsProvider;
import com.mrtrollnugnug.ropebridge.datagen.server.ModLootProvider;
import com.mrtrollnugnug.ropebridge.datagen.server.ModRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RopeBridgeDataGen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new ModLootProvider(packOutput));
			generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
			generator.addProvider(event.includeServer(), new ModAdvancementsProvider(packOutput, lookupProvider, helper));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new ModLanguageProvider(packOutput));
			generator.addProvider(event.includeClient(), new ModSoundProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new ModBlockstateProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, helper));
		}
	}

}
