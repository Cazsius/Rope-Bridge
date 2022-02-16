package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.handler.ConfigHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class ItemBuilder extends Item {

	public ItemBuilder(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		if (hand == Hand.MAIN_HAND) {
			playerIn.setActiveHand(hand);
		}
		return super.onItemRightClick(worldIn, playerIn, hand);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public static RayTraceResult trace(PlayerEntity player) {
		return player.pick(ConfigHandler.getMaxBridgeDistance(), 0, false);
	}
}
