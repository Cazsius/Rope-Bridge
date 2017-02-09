package com.mrtrollnugnug.ropebridge;

import com.mrtrollnugnug.ropebridge.blocks.ModBlocks;
import com.mrtrollnugnug.ropebridge.crafting.ModCrafting;
import com.mrtrollnugnug.ropebridge.items.ModItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
    	ModItems.createItems();
    	ModBlocks.createBlocks();
    }

    public void init(FMLInitializationEvent e) {
    	ModCrafting.initCrafting();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}