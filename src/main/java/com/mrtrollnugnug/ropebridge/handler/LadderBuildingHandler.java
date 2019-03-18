package com.mrtrollnugnug.ropebridge.handler;

import java.util.Timer;
import java.util.TimerTask;

import com.mrtrollnugnug.ropebridge.block.RopeLadder;
import com.mrtrollnugnug.ropebridge.block.RopeLadder.EnumType;
import com.mrtrollnugnug.ropebridge.block.TileEntityRopeLadder;
import com.mrtrollnugnug.ropebridge.lib.Constants.Messages;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class LadderBuildingHandler {
	public static void newLadder(BlockPos start, EntityPlayer player, World world, EnumFacing hitSide,
			ItemStack builder) {

		if (!hitSide.getAxis().isHorizontal()) {
			ModUtils.tellPlayer(player, Messages.BAD_SIDE,
					hitSide == EnumFacing.UP ? I18n.format(Messages.TOP) : I18n.format(Messages.BOTTOM));
			return;
		}
		if (!world.isSideSolid(start.offset(hitSide.getOpposite()), hitSide)) {
			ModUtils.tellPlayer(player, Messages.NOT_SOLID);
			return;
		}

		int count = 0;
		BlockPos lower = start;
		IBlockState state = world.getBlockState(lower);
		while (state.getBlock().isReplaceable(world, lower)) {
			count++;
			lower = lower.down();
			state = world.getBlockState(lower);
		}
		if (count <= 0) {
			ModUtils.tellPlayer(player, Messages.OBSTRUCTED);
			return;
		}

		int woodNeeded = count * ConfigurationHandler.getWoodPerBlock();
		int ropeNeeded = count * ConfigurationHandler.getRopePerBlock();
		BlockPlanks.EnumType woodType = findType(player);
		EnumType type = convertType(woodType);

		if (!player.capabilities.isCreativeMode) {
			if (type == null || !hasMaterials(player, woodNeeded, ropeNeeded, woodType)) {
				ModUtils.tellPlayer(player, Messages.UNDERFUNDED_LADDER, woodNeeded, ropeNeeded);
				return;
			}
		}

		if (type == null)
			type = EnumType.OAK;

		if (!player.capabilities.isCreativeMode)
			builder.damageItem(ConfigurationHandler.getLadderDamage(), player);

		consume(player, woodNeeded, ropeNeeded, woodType);
		build(world, start, count, hitSide, type);
	}

	private static void build(World world, BlockPos start, int count, final EnumFacing facing, final EnumType type) {
		build(world, start, count, 0, facing, type);
	}

	private static void build(final World world, final BlockPos start, final int count, final int it,
			final EnumFacing facing, final EnumType type) {
		FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
			IBlockState state = ContentHandler.blockRopeLadder.getDefaultState()
					.withProperty(RopeLadder.FACING, facing).withProperty(RopeLadder.TYPE, type);
			world.setBlockState(start.down(it), state);
			world.setTileEntity(start.down(it), new TileEntityRopeLadder(type));
		});
		if (it + 1 < count)
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					build(world, start, count, it + 1, facing, type);
				}
			}, 100);
	}

	private static void consume(EntityPlayer player, int woodNeeded, int ropeNeeded,
			net.minecraft.block.BlockPlanks.EnumType woodType) {
		boolean noCost = ConfigurationHandler.getRopePerBlock() == 0 && ConfigurationHandler.getWoodPerBlock() == 0;
		if (player.capabilities.isCreativeMode || noCost)
			return;
		player.inventory.clearMatchingItems(ContentHandler.itemRope, -1, ropeNeeded, null);
		player.inventory.clearMatchingItems(Item.getItemFromBlock(Blocks.WOODEN_SLAB), woodType.getMetadata(),
				woodNeeded, null);
	}

	private static EnumType convertType(BlockPlanks.EnumType type) {
		if (type == null)
			return null;
		switch (type) {
		case ACACIA:
			return EnumType.ACACIA;
		case BIRCH:
			return EnumType.BIRCH;
		case DARK_OAK:
			return EnumType.DARK_OAK;
		case JUNGLE:
			return EnumType.JUNGLE;
		case OAK:
			return EnumType.OAK;
		case SPRUCE:
			return EnumType.SPRUCE;
		default:
			return null;
		}
	}

	private static BlockPlanks.EnumType findType(EntityPlayer player) {
		boolean noCost = ConfigurationHandler.getRopePerBlock() == 0 && ConfigurationHandler.getWoodPerBlock() == 0;
		if (noCost || player.capabilities.isCreativeMode)
			return BlockPlanks.EnumType.OAK;
		for (ItemStack i : player.inventory.mainInventory) {
			if (i != null && i.getItem() == Item.getItemFromBlock(Blocks.WOODEN_SLAB))
				return (BlockPlanks.EnumType) Blocks.WOODEN_SLAB.getTypeForItem(i);
		}
		return null;
	}

	private static boolean hasMaterials(EntityPlayer player, int woodNeeded, int ropeNeeded,
			BlockPlanks.EnumType toFind) {
		boolean noCost = ConfigurationHandler.getRopePerBlock() == 0 && ConfigurationHandler.getWoodPerBlock() == 0;
		if (noCost || player.capabilities.isCreativeMode)
			return true;
		for (ItemStack i : player.inventory.mainInventory) {
			if (i == null)
				continue;
			Item it = i.getItem();
			if (it == ContentHandler.itemRope) {
				ropeNeeded -= i.getCount();
			} else if (it == Item.getItemFromBlock(Blocks.WOODEN_SLAB)
					&& toFind == Blocks.WOODEN_SLAB.getTypeForItem(i)) {
				woodNeeded -= i.getCount();
			}
		}
		return woodNeeded <= 0 && ropeNeeded <= 0;
	}
}
