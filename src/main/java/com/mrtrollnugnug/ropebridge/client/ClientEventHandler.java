package com.mrtrollnugnug.ropebridge.client;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(value = { Side.CLIENT })
public final class ClientEventHandler
{
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent event)
    {
        //if (event.getModID().equals(Constants.MOD_ID) && (ConfigurationHandler.isZoomOnAim()))
            //ItemBuilder.setFov(Minecraft.getMinecraft().gameSettings.fovSetting);
    }
}
