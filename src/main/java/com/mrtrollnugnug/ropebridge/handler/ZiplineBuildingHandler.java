package com.mrtrollnugnug.ropebridge.handler;

import java.util.Timer;
import java.util.TimerTask;

import com.mrtrollnugnug.ropebridge.Messages;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZiplineBuildingHandler {
	public static void newZipline(EntityPlayer player, ItemStack stack, int inputType, BlockPos pos1, BlockPos pos2) {
		  int x1;
	        int x2;
	        int y1;
	        int y2;
	        int z1;
	        int z2;
	        //Controls rotation of slabs
	            x1 = pos1.getX();
	            y1 = pos1.getY();
	            z1 = pos1.getZ();
	            x2 = pos2.getX();
	            y2 = pos2.getY();
	            z2 = pos2.getZ();
		int distInt;
	    distInt = Math.abs(x2 - x1);
	    if (distInt < 2)
            return;
	    
        if (!player.capabilities.isCreativeMode && !hasMaterials(player, distInt - 1))
            return;
	}
	
	private static boolean hasMaterials(EntityPlayer player, int dist) {
		if (player.capabilities.isCreativeMode)
			return true;
		
		final int ropeNeeded = dist;
		int ropeHad = 0;
		
		//TODO Potentially wrong.
		//Where did 36 come from?
		//Appears to work smoother?
		//for (int i = 0; i < 36; i++) {
        for (int i = 0; i < ConfigurationHandler.getBridgeDroopFactor(); i++) {
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
		  boolean isClear;
		  BlockPos pos;
	      if (rotate) {
	    	  pos = new BlockPos(z, y, x);
	      }
	      else {
	    	  pos = new BlockPos(x, y, z);
	      }
	      isClear = ConfigurationHandler.isBreakThroughBlocks() || world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos);
	        return isClear;
	    }
	  
	  private static void buildZipline(final World world, final int type) {
          new Timer().schedule(new TimerTask() {
              @Override
              public void run() {
            	  buildZipline(world, type);
              }
          }, 100); 
	    }
}
