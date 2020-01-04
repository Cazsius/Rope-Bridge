package com.mrtrollnugnug.ropebridge;

import com.mrtrollnugnug.ropebridge.handler.ConfigurationHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import com.mrtrollnugnug.ropebridge.network.BridgeMessage;
import com.mrtrollnugnug.ropebridge.network.LadderMessage;
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
  private static SimpleChannel snw;

  private int discriminator = 0;

  public RopeBridge() {
    ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigurationHandler.SERVER_SPEC);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
  }

  private void preInit(FMLCommonSetupEvent e) {
    snw = NetworkRegistry.newSimpleChannel(new ResourceLocation(Constants.MOD_ID,Constants.MOD_ID), () -> "1.0", s -> true, s -> true);
    snw.registerMessage(discriminator++,BridgeMessage.class,BridgeMessage::encode, BridgeMessage::new,BridgeMessage::handle);
    snw.registerMessage(discriminator++,LadderMessage.class, LadderMessage::encode, LadderMessage::new, LadderMessage::handle);
  }

  public static SimpleChannel getSnw() {
    return snw;
  }
}