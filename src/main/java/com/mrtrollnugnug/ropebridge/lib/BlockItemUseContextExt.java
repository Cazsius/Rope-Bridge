package com.mrtrollnugnug.ropebridge.lib;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockItemUseContextExt extends BlockPlaceContext {
	public BlockItemUseContextExt(Level level, @Nullable Player player, InteractionHand hand, ItemStack stack, BlockHitResult blockHitResult) {
		super(level, player, hand, stack, blockHitResult);
	}
}
