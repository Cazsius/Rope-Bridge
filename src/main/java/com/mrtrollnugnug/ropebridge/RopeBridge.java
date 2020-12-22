package com.mrtrollnugnug.ropebridge;

import com.mrtrollnugnug.ropebridge.handler.ConfigHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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

  private void preInit(FMLCommonSetupEvent e) {
    ModUtils.initMap();
  }

}