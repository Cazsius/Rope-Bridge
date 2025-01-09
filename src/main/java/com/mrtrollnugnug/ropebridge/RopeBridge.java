package com.mrtrollnugnug.ropebridge;

import com.mrtrollnugnug.ropebridge.handler.ConfigHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class RopeBridge {

	public RopeBridge() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.SERVER_SPEC);
		eventBus.addListener(this::preInit);

		ContentHandler.BLOCKS.register(eventBus);
		ContentHandler.ITEMS.register(eventBus);
		ContentHandler.CREATIVE_MODE_TABS.register(eventBus);
	}

	private void preInit(FMLCommonSetupEvent event) {
		ModUtils.initMap();
	}
}
