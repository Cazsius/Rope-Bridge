package com.mrtrollnugnug.ropebridge.handler;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.mrtrollnugnug.ropebridge.lib.Constants.Messages;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BridgeBuildingHandler {
    private static String woodSlab = "tile.woodSlab";

    public static void newBridge(EntityPlayer player, ItemStack stack, int inputType, BlockPos pos1, BlockPos pos2) {
        final LinkedList<SlabPosHandler> bridge = new LinkedList<>();
        boolean allClear = true;
        int x1;
        int x2;
        int y1;
        int y2;
        int z1;
        int z2;
        boolean rotate = getRotate(pos1, pos2);
        if (!rotate) {
            x1 = pos1.getX();
            y1 = pos1.getY();
            z1 = pos1.getZ();
            x2 = pos2.getX();
            y2 = pos2.getY();
            z2 = pos2.getZ();
        } else {
            x1 = pos1.getZ();
            y1 = pos1.getY();
            z1 = pos1.getX();
            x2 = pos2.getZ();
            y2 = pos2.getY();
            z2 = pos2.getX();
        }
        if (Math.abs(z2 - z1) > 3) {
            ModUtils.tellPlayer(player, Messages.NOT_CARDINAL);
            return;
        }
        double m;
        double b;
        double distance;
        int distInt;

        m = (double) (y2 - y1) / (double) (x2 - x1);
        if (!ConfigurationHandler.isIgnoreSlopeWarnings() && Math.abs(m) > 0.2) {
            ModUtils.tellPlayer(player, Messages.SLOPE_GREAT);
            return;
        }
        b = y1 - m * x1;
        distance = Math.abs(x2 - x1);
        distInt = Math.abs(x2 - x1);
        if (distInt < 2) {
            return;
        }
        if (!player.capabilities.isCreativeMode && !hasMaterials(player, distInt - 1)) {
            return;
        }
        for (int x = Math.min(x1, x2) + 1; x <= Math.max(x1, x2) - 1; x++) {
            for (int y = Math.max(y1, y2); y >= Math.min(y1, y2) - distInt / 8 - 1; y--) {
                final double funcVal = m * x + b - distance / 1000 * Math.sin((x - Math.min(x1, x2)) * (Math.PI / distance)) * ConfigurationHandler.getBridgeDroopFactor() + ConfigurationHandler.getBridgeYOffset();
                if (y + 0.5 > funcVal && y - 0.5 <= funcVal) {
                    int level;
                    if (funcVal >= y) {
                        if (funcVal >= y + 0.25) {
                            level = 4;
                        } else {
                            level = 3;
                        }
                    } else {
                        if (funcVal >= y - 0.25) {
                            level = 2;
                        } else {
                            level = 1;
                        }
                    }
                    allClear = !addSlab(player.world, bridge, x, y + 1, z1, level, rotate) ? false : allClear;
                }
            }
        }

        if (allClear) {
            final int type = inputType == -1 ? getWoodType(player) : inputType;
            if (inputType == -1 && !player.capabilities.isCreativeMode) {
                takeMaterials(player, distInt - 1);
                stack.damageItem(ConfigurationHandler.getBridgeDamage(), player);
            }
            buildBridge(player.world, bridge, type);
        } else {
            ModUtils.tellPlayer(player, Messages.OBSTRUCTED);
            return;
        }
    }

    private static boolean getRotate(BlockPos p1, BlockPos p2) {
        return Math.abs(p1.getX() - p2.getX()) <= Math.abs(p1.getZ() - p2.getZ());
    }

    private static boolean hasMaterials(EntityPlayer player, int dist) {
        boolean noCost = ConfigurationHandler.getSlabsPerBlock() == 0 && ConfigurationHandler.getStringPerBlock() == 0;
        if (player.capabilities.isCreativeMode || noCost)
            return true;
        final int slabsNeeded = dist;
        final int stringNeeded = 1 + dist / 2;
        int slabsHad = 0;
        int stringHad = 0;

        for (int i = 0; i < 36; i++) {
            final ItemStack stack = player.inventory.mainInventory.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            final Item item = stack.getItem();
            if (item == ContentHandler.itemRope) {
                stringHad += stack.getCount();
            }
            if (item.getTranslationKey().equals(woodSlab)) {
                slabsHad += stack.getCount();
            }
        }
        if (slabsHad >= slabsNeeded && stringHad >= stringNeeded) {
            return true;
        } else {
            ModUtils.tellPlayer(player, Messages.UNDERFUNDED_BRIDGE, slabsNeeded, stringNeeded);
            return false;
        }
    }

    private static void takeMaterials(EntityPlayer player, int dist) {
        boolean noCost = ConfigurationHandler.getSlabsPerBlock() == 0 && ConfigurationHandler.getStringPerBlock() == 0;
        if (player.capabilities.isCreativeMode || noCost) {
            return;
        }
        int slabsNeeded = dist;
        int stringNeeded = 1 + dist / 2;
        int i = 0;

        for (; i < 36; i++) {
            final ItemStack stack = player.inventory.mainInventory.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            final Item item = stack.getItem();
            final String name = item.getTranslationKey();
            if (item == ContentHandler.itemRope) {
                if (stack.getCount() > stringNeeded) {
                	stack.shrink(stringNeeded);
                    stringNeeded = 0;
                }
            } else if (name.equals(BridgeBuildingHandler.woodSlab)) {
                if (stack.getCount() > slabsNeeded) {
                    stack.shrink(stringNeeded);
                    slabsNeeded = 0;
                }
            }
        }
    }

    private static boolean addSlab(World world, LinkedList<SlabPosHandler> list, int x, int y, int z, int level, boolean rotate) {
        boolean isClear;
        BlockPos pos;
        if (rotate) {
            pos = new BlockPos(z, y, x);
        }
        else {
            pos = new BlockPos(x, y, z);
        }
        isClear = ConfigurationHandler.isBreakThroughBlocks() || world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos);
        list.add(new SlabPosHandler(pos, level, rotate));
        if (!isClear) {
            spawnSmoke(world, pos, 15);
        }
        return isClear;
    }

    // Controls if blocks are in the way
    private static void spawnSmoke(World world, BlockPos pos, int times) {

        if (times > 0) {
            world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D, new int[0]);
            final World finworld = world;
            final BlockPos finPos = pos;
            final int finTimes = times - 1;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    spawnSmoke(finworld, finPos, finTimes);
                }
            }, 1000);
        }
    }

    @SuppressWarnings("deprecation")
	private static void buildBridge(final World world, final LinkedList<SlabPosHandler> bridge, final int type) {

        SlabPosHandler slab;
        if (!bridge.isEmpty()) {
            slab = bridge.pop();
            Block block;
            switch (slab.getLevel()) {
            case 1:
                block = ContentHandler.blockBridgeSlab1;
                break;
            case 2:
                block = ContentHandler.blockBridgeSlab2;
                break;
            case 3:
                block = ContentHandler.blockBridgeSlab3;
                break;
            case 4:
                block = ContentHandler.blockBridgeSlab4;
                break;
            default:
                block = Blocks.AIR;
                break;
            }

            world.setBlockState(slab.getBlockPos(), block.getStateFromMeta((slab.getRotate() ? 1 : 0) + 2 * type), 3);
            spawnSmoke(world, new BlockPos(slab.getBlockPos().getX(), slab.getBlockPos().getY(), slab.getBlockPos().getZ()), 1);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    buildBridge(world, bridge, type);
                }
            }, 100);
        }
    }

    private static int getWoodType(EntityPlayer player) {
        for (final ItemStack stack : player.inventory.mainInventory) {
            if (stack.isEmpty()) {
                continue;
            }
            final String name = stack.getItem().getTranslationKey();
            if (name.equals(BridgeBuildingHandler.woodSlab))
                return stack.getItemDamage();
        }
        return 0;
    }
}
