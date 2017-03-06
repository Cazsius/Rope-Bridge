package com.mrtrollnugnug.ropebridge.common;

import com.mrtrollnugnug.ropebridge.item.ItemBridgeBuilder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{

    public void preInit(FMLPreInitializationEvent e) {
    	//Controls preInit stage in loading game
    }

    public EntityPlayer getPlayer()
    {

        return null;
    }

    public float getFov()
    {
        return ItemBridgeBuilder.getFov();
    }
}