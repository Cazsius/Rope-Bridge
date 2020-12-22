package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.handler.LadderBuildingHandler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemLadderBuilder extends ItemBuilder {
	
    public ItemLadderBuilder(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity && !world.isRemote) {
            final PlayerEntity player = (PlayerEntity) entityLiving;
            if (this.getUseDuration(stack) - timeLeft > 5) {
                final RayTraceResult hit = trace(player);
                if (hit instanceof BlockRayTraceResult) {
                    final BlockPos from = ((BlockRayTraceResult) hit).getPos();
                    Direction side = ((BlockRayTraceResult) hit).getFace();
                    LadderBuildingHandler.newLadder(from, player, player.getEntityWorld(), side, player.getHeldItemMainhand());
                }
            }
        }
    }
}
