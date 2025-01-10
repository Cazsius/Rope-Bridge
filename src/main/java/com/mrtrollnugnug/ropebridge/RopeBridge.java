package com.mrtrollnugnug.ropebridge;

import com.mrtrollnugnug.ropebridge.handler.ConfigHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(Constants.MOD_ID)
public class RopeBridge {

	public RopeBridge(IEventBus eventBus, ModContainer container, Dist dist) {
		container.registerConfig(ModConfig.Type.SERVER, ConfigHandler.SERVER_SPEC);
		eventBus.addListener(this::preInit);

		ContentHandler.BLOCKS.register(eventBus);
		ContentHandler.ITEMS.register(eventBus);
		ContentHandler.CREATIVE_MODE_TABS.register(eventBus);
		ContentHandler.SOUND_EVENTS.register(eventBus);

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}

	private void preInit(FMLCommonSetupEvent event) {
		ModUtils.initMap();
	}
}
