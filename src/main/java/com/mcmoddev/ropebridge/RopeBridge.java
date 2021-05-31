package com.mcmoddev.ropebridge;

import com.mcmoddev.ropebridge.handler.ConfigHandler;
import com.mcmoddev.ropebridge.handler.ContentHandler;
import com.mcmoddev.ropebridge.lib.Constants;
import com.mcmoddev.ropebridge.lib.ModUtils;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class RopeBridge {

	public static final ItemGroup RopeBridgeTab = new ItemGroup("RopeBridgeTab") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ContentHandler.bridge_builder);
		}
	};

	public RopeBridge() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.SERVER_SPEC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
	}

	private void preInit(FMLCommonSetupEvent event) {
		ModUtils.initMap();
	}
}
