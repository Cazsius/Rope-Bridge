package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.handler.BridgeBuildingHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants.Messages;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class ItemBridgeBuilder extends ItemBuilder {

	public ItemBridgeBuilder(Properties properties) {
		super(properties);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
		super.onUseTick(level, livingEntity, stack, remainingUseDuration);
		if (livingEntity.level().isClientSide && livingEntity instanceof Player player) {
			rotatePlayerTowards(player, getNearestYaw(player));
		}
	}

	private static void rotatePlayerTowards(Player player, float target) {
		float yaw = player.getYRot() % 360;
		if (yaw < 0) {
			yaw += 360;
		}
		rotatePlayerTo(player, yaw + (target - yaw) / 4);
	}

	private static void rotatePlayerTo(Player player, float yaw) {
		final float original = player.getYRot();
		player.setYRot(yaw);
		player.yRotO += player.getYRot() - original;
	}

	private static float getNearestYaw(Player player) {
		float yaw = player.getYRot() % 360;
		if (yaw < 0) {
			yaw += 360;
		}
		if (yaw < 45) {
			return 0F;
		}
		if (yaw > 45 && yaw <= 135) {
			return 90F;
		} else if (yaw > 135 && yaw <= 225) {
			return 180F;
		} else if (yaw > 225 && yaw <= 315) {
			return 270F;
		} else {
			return 360F;
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
		if (livingEntity instanceof Player player && !level.isClientSide) {
			if (this.getUseDuration(stack, livingEntity) - timeCharged > 10) {
				if (!player.onGround()) {
					ModUtils.tellPlayer(player, Messages.NOT_ON_GROUND);
				} else {
					final HitResult hit = trace(player);
					if (hit instanceof BlockHitResult blockHitResult) {
						final BlockPos floored = BlockPos.containing(player.getX(), player.getY() - 1, player.getZ()).below();
						BlockPos target = blockHitResult.getBlockPos();
						BridgeBuildingHandler.newBridge(player, player.getMainHandItem(), floored, target);
						level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ContentHandler.swoosh.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
					}
				}
			}
		}
	}

//	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
//		return false;
//	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (isBridgeBlock(state.getBlock())) {
			return 1F;
		}
		return super.getDestroySpeed(stack, state);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		tooltip.add(Component.literal("- Hold right-click to build"));
		tooltip.add(Component.literal("- Sneak to break whole bridge"));
	}

	private static boolean isBridgeBlock(Block block) {
		return block instanceof RopeBridgeBlock;
	}
}
