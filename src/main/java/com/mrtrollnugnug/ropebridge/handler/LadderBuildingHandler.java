package com.mrtrollnugnug.ropebridge.handler;

import java.util.Timer;
import java.util.TimerTask;

import com.mrtrollnugnug.ropebridge.block.RopeLadderBlock;
import com.mrtrollnugnug.ropebridge.lib.BlockItemUseContextExt;
import com.mrtrollnugnug.ropebridge.lib.Constants.Messages;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class LadderBuildingHandler {
	public static void newLadder(BlockPos start, PlayerEntity player, World world, Direction hitSide,
															 ItemStack builder) {
		if (!hitSide.getAxis().isHorizontal()) {
			ModUtils.tellPlayer(player, Messages.BAD_SIDE,
					hitSide == Direction.UP ? I18n.format(Messages.TOP) : I18n.format(Messages.BOTTOM));
			return;
		}
		if (!RopeLadderBlock.canAttachTo(world,start.offset(hitSide.getOpposite()),hitSide)) {
			ModUtils.tellPlayer(player, Messages.NOT_SOLID);
			return;
		}

		int count = 0;
		BlockPos lower = start;
		BlockState state = world.getBlockState(lower);

		while (isReplaceable(world, lower,state)) {
			count++;
			lower = lower.down();
			state = world.getBlockState(lower);
		}
		if (count <= 0) {
			ModUtils.tellPlayer(player, Messages.OBSTRUCTED);
			return;
		}

		int woodNeeded = count * ConfigHandler.getWoodPerLadder();
		int ropeNeeded = count * ConfigHandler.getRopePerLadder();
		Block slabToUse = getSlabToUse(player);

		if (!player.abilities.isCreativeMode) {
			if (!hasMaterials(player, woodNeeded, ropeNeeded, slabToUse)) {
				ModUtils.tellPlayer(player, Messages.UNDERFUNDED_LADDER, woodNeeded, ropeNeeded);
				return;
			}
		}

		if (!player.abilities.isCreativeMode)
			builder.damageItem(ConfigHandler.getLadderDamage(), player, playerEntity -> playerEntity.sendBreakAnimation(player.getActiveHand()));

		consume(player, woodNeeded, ropeNeeded, slabToUse);
		build(world, start, count, hitSide, slabToUse);
	}

	public static boolean isReplaceable(World world, BlockPos pos, BlockState state){
		BlockItemUseContext blockItemUseContext = new BlockItemUseContextExt(world,null, Hand.MAIN_HAND,ItemStack.EMPTY,
						new BlockRayTraceResult(new Vec3d((double)pos.getX() + 0.5D + (double)Direction.DOWN.getXOffset() * 0.5D, (double)pos.getY() + 0.5D + (double)Direction.DOWN.getYOffset() * 0.5D, (double)pos.getZ() + 0.5D + (double)Direction.DOWN.getZOffset() * 0.5D), Direction.DOWN, pos, false));
		return pos.getY()> 0 && state.isReplaceable(blockItemUseContext);
	}

	private static void build(World world, BlockPos start, int count, final Direction facing, final Block type) {
		build(world, start, count, 0, facing, type);
	}

	private static void build(final World world, final BlockPos start, final int count, final int iterations,
                            final Direction facing, final Block slabToUse) {
		ServerLifecycleHooks.getCurrentServer().execute(() -> {

			BlockState state = ModUtils.map.get(slabToUse).getRight().getDefaultState().with(LadderBlock.FACING, facing);
			world.setBlockState(start.down(iterations), state);
		});
		if (iterations + 1 < count)
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					build(world, start, count, iterations + 1, facing, slabToUse);
				}
			}, 100);
	}

	private static void consume(PlayerEntity player, int woodNeeded, int ropeNeeded,
                              Block woodType) {
		boolean noCost = ConfigHandler.getRopePerLadder() == 0 && ConfigHandler.getWoodPerLadder() == 0;
		if (player.abilities.isCreativeMode || noCost)
			return;
		player.inventory.clearMatchingItems(stack -> stack.getItem() == ContentHandler.rope, ropeNeeded);
		player.inventory.clearMatchingItems(stack -> stack.getItem() == woodType.asItem(), woodNeeded);
	}

	private static Block getSlabToUse(PlayerEntity player) {
		boolean noCost = ConfigHandler.getRopePerLadder() == 0 && ConfigHandler.getWoodPerLadder() == 0;
		if (noCost || player.abilities.isCreativeMode)
			return Blocks.OAK_SLAB;
		return player.inventory.mainInventory.stream().filter(stack -> stack.getItem().isIn(ItemTags.WOODEN_SLABS)).findFirst().map(stack -> Block.getBlockFromItem(stack.getItem())).orElse(null);
	}

	private static boolean hasMaterials(PlayerEntity player, int woodNeeded, int ropeNeeded,
                                      Block toFind) {
		boolean noCost = ConfigHandler.getRopePerLadder() == 0 && ConfigHandler.getWoodPerLadder() == 0;
		if (noCost || player.abilities.isCreativeMode)
			return true;
		for (ItemStack i : player.inventory.mainInventory) {
			if (i.isEmpty())
				continue;
			Item item = i.getItem();
			if (item == ContentHandler.rope) {
				ropeNeeded -= i.getCount();
			} else if (item == toFind.asItem()) {
				woodNeeded -= i.getCount();
			}
		}
		return woodNeeded <= 0 && ropeNeeded <= 0;
	}
}
