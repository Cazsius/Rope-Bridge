package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.Messages;
import com.mrtrollnugnug.ropebridge.RopeBridge;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import com.mrtrollnugnug.ropebridge.network.ZiplineMessage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ItemZiplineBuilder extends ItemBuilder {

    public ItemZiplineBuilder()
    {
        super();
    }

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		super.onUsingTick(stack, player, count);
		if (player.world.isRemote && player instanceof EntityPlayer) {
	    	final EntityPlayer p = (EntityPlayer) player;
	  	ItemBuilder.rotatePlayerTowards(p, ItemBuilder.getNearestYaw(p));
	    }
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
		 if (entityLiving instanceof EntityPlayer && world.isRemote) {
	            final EntityPlayer player = (EntityPlayer) entityLiving;
	            if (this.getMaxItemUseDuration(stack) - timeLeft > 10) {
	            if (!player.onGround) {
                    ModUtils.tellPlayer(player, Messages.NOT_ON_GROUND);
                } 
	            
	            else {
	            	//Potentially? hit - Controls where the player is relative to where it is going to.
                    final RayTraceResult hit = trace(player);
                    if (hit.typeOfHit == Type.BLOCK) {
                    	//floored- Controls making it level spawn at the players feet and not above head
                       final BlockPos floored = new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ)).down();
                       //target - Controls where the rope bridge is going to.
                       BlockPos target = hit.getBlockPos();
                        //TODO How does this make it send rope bridge stuff?
                        RopeBridge.getSnw().sendToServer(new ZiplineMessage(floored, target));
                    }
	            }
	            }
		}	
	}
}
	