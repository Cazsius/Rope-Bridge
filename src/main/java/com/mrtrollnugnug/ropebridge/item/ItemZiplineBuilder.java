package com.mrtrollnugnug.ropebridge.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemZiplineBuilder extends ItemBuilder {

    public ItemZiplineBuilder()
    {
        super();
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		super.onUsingTick(stack, player, count);
		if (player.world.isRemote && player instanceof EntityPlayer) {
	    	final EntityPlayer p = (EntityPlayer) player;
	  	ItemBuilder.rotatePlayerTowards(p, ItemBuilder.getNearestYaw(p));
	    }
	}
}
