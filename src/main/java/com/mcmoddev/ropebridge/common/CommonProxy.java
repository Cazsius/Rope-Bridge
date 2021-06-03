package com.mcmoddev.ropebridge.common;

import com.mcmoddev.ropebridge.handler.ContentHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		ContentHandler.initBlocks();
		ContentHandler.initItems();
	}

	public EntityPlayer getPlayer() {
		return null;
	}
}
