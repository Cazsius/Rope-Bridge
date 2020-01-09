package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.RopeBridge;
import com.mrtrollnugnug.ropebridge.network.LadderMessage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ItemLadderBuilder extends ItemBuilder {
	
    public ItemLadderBuilder(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity && world.isRemote) {
            final PlayerEntity player = (PlayerEntity) entityLiving;
            if (this.getUseDuration(stack) - timeLeft > 10) {
                final RayTraceResult hit = trace(player);
                if (hit instanceof BlockRayTraceResult) {
                    final BlockPos start = ((BlockRayTraceResult) hit).getPos();
                    RopeBridge.getSnw().sendToServer(new LadderMessage(start.offset(((BlockRayTraceResult) hit).getFace()),
                            ((BlockRayTraceResult) hit).getFace()));
                }
            }
        }
    }
}
