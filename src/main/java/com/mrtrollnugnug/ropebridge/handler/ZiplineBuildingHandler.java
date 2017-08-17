package com.mrtrollnugnug.ropebridge.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZiplineBuildingHandler
{
	public static void buildZipline(EntityPlayer player, BlockPos target)
	{
		World world = player.getEntityWorld();
		BlockPos playerPos = player.getPosition();
		
		//TODO Temp from
		BlockPos from = playerPos;
	}
	
}
