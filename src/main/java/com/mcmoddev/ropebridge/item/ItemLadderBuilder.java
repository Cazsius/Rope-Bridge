package com.mcmoddev.ropebridge.item;

import com.mcmoddev.ropebridge.RopeBridge;
import com.mcmoddev.ropebridge.network.LadderMessage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ItemLadderBuilder extends ItemBuilder {

	public ItemLadderBuilder() {
		super();
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
		if (entityLiving instanceof EntityPlayer && world.isRemote) {
			final EntityPlayer player = (EntityPlayer) entityLiving;
			if (this.getMaxItemUseDuration(stack) - timeLeft > 10) {
				final RayTraceResult hit = trace(player);
				if (hit.typeOfHit == Type.BLOCK) {
					final BlockPos start = hit.getBlockPos();
					RopeBridge.getSnw().sendToServer(new LadderMessage(start.offset(hit.sideHit), hit.sideHit));
				}
			}
		}
	}
}
