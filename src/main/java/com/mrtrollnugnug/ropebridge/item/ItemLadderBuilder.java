package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.handler.LadderBuildingHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ItemLadderBuilder extends ItemBuilder {

	public ItemLadderBuilder(Properties properties) {
		super(properties);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
		if (livingEntity instanceof Player player && !level.isClientSide) {
			if (this.getUseDuration(stack) - timeLeft > 5) {
				final HitResult hit = trace(player);
				if (hit instanceof BlockHitResult blockHitResult) {
					final BlockPos from = blockHitResult.getBlockPos();
					Direction side = blockHitResult.getDirection();
					LadderBuildingHandler.newLadder(from, player, player.getCommandSenderWorld(), side, player.getMainHandItem());
				}
			}
		}
	}
}
