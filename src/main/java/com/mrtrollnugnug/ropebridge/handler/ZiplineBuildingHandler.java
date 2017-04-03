package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.Messages;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZiplineBuildingHandler {
	public static void newZipline(EntityPlayer player, ItemStack stack, int inputType, BlockPos pos1, BlockPos pos2) {
		
	}
	
	private static boolean hasMaterials(EntityPlayer player, int dist) {
		if (player.capabilities.isCreativeMode)
			return true;
		
		final int ropeNeeded = dist;
		int ropeHad = 0;
		
        for (int i = 0; i < 36; i++) {
            final ItemStack stack = player.inventory.mainInventory[i];
            if (stack == null) {
                continue;
            }
        final Item item = stack.getItem();
        if (item == ContentHandler.getItemRope()) {
        	ropeHad += stack.stackSize;
        	}
        }
        
        if (ropeHad >= ropeNeeded)
            return true;
        else {
            ModUtils.tellPlayer(player, Messages.UNDERFUNDED_ZIPLINE, ropeNeeded);
            return false;
        }	
    }
	
	  private static void takeMaterials(EntityPlayer player, int dist) {
		  if (player.capabilities.isCreativeMode)
	            return;
		  
		  int ropeNeeded = dist * ConfigurationHandler.getStringPerBlock();
		  int i = 0;
		  
		  for (; i < 36; i++) {
	            final ItemStack stack = player.inventory.mainInventory[i];
	            if (stack == null) {
	                continue;
	            }
	            
	            final Item item = stack.getItem();
	            if (item == ContentHandler.getItemRope()) {
	                if (stack.stackSize > ropeNeeded) {
                    stack.stackSize = stack.stackSize - ropeNeeded;
                    ropeNeeded = 0;
                }
	            else {
	            	ropeNeeded -= stack.stackSize;
	            	player.inventory.mainInventory[i] = null;
	            	continue;
	                }
	            }
		  	}
	  }
	  
	  private static boolean addRope(World world, int x, int y, int z, int level, boolean rotate) {  
	        if (rotate) {
	            new BlockPos(z, y, x);
	        }
	        else {
	            new BlockPos(x, y, z);
	        }
	        return true;
	    }
}
