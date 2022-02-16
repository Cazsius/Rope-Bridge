package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.handler.BridgeBuildingHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants.Messages;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemBridgeBuilder extends ItemBuilder {

	public ItemBridgeBuilder(Properties properties) {
		super(properties);
	}

	@Override
	public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
		super.onUsingTick(stack, player, count);
		if (player.world.isRemote && player instanceof PlayerEntity) {
			final PlayerEntity p = (PlayerEntity) player;
			rotatePlayerTowards(p, getNearestYaw(p));
		}
	}

	private static void rotatePlayerTowards(PlayerEntity player, float target) {
		float yaw = player.rotationYaw % 360;
		if (yaw < 0) {
			yaw += 360;
		}
		rotatePlayerTo(player, yaw + (target - yaw) / 4);
	}

	private static void rotatePlayerTo(PlayerEntity player, float yaw) {
		final float original = player.rotationYaw;
		player.rotationYaw = yaw;
		player.prevRotationYaw += player.rotationYaw - original;
	}

	private static float getNearestYaw(PlayerEntity player) {
		float yaw = player.rotationYaw % 360;
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
	public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity && !world.isRemote) {
			final PlayerEntity player = (PlayerEntity) entityLiving;
			if (this.getUseDuration(stack) - timeLeft > 10) {
				if (!player.isOnGround()) {
					ModUtils.tellPlayer(player, Messages.NOT_ON_GROUND);
				} else {
					final RayTraceResult hit = trace(player);
					if (hit instanceof BlockRayTraceResult) {
						final BlockPos floored = new BlockPos(Math.floor(player.getPosX()), Math.floor(player.getPosY()) - 1, Math.floor(player.getPosZ())).down();
						BlockPos target = ((BlockRayTraceResult) hit).getPos();
						BridgeBuildingHandler.newBridge(player, player.getHeldItemMainhand(), floored, target);
					}
				}
			}
		}
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
		return false;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (isBridgeBlock(state.getBlock())) {
			return 1F;
		}
		return super.getDestroySpeed(stack, state);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent("- Hold right-click to build"));
		tooltip.add(new StringTextComponent("- Sneak to break whole bridge"));
	}

	private static boolean isBridgeBlock(Block block) {
		return block instanceof RopeBridgeBlock;
	}
}
