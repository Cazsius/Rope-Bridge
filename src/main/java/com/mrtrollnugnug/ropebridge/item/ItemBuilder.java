package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.handler.ConfigHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public abstract class ItemBuilder extends Item {

	public ItemBuilder(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (hand == InteractionHand.MAIN_HAND) {
			player.startUsingItem(hand);
		}
		return super.use(level, player, hand);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public static HitResult trace(Player player) {
		return player.pick(ConfigHandler.getMaxBridgeDistance(), 0, false);
	}
}
