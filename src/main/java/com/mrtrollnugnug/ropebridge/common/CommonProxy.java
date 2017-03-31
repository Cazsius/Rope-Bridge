package com.mrtrollnugnug.ropebridge.common;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{

    public void preInit(FMLPreInitializationEvent e)
    {
        ContentHandler.initBlocks();
        ContentHandler.initItems();
        ContentHandler.initRecipes();
    }

    public EntityPlayer getPlayer()
    {

        return null;
    }
/*
    public float getFov()
    {
        return ItemBuilder.getFov();
   	}
*/
}