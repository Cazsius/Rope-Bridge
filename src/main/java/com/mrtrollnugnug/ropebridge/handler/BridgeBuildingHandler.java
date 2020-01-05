package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.lib.Constants.Messages;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BridgeBuildingHandler {
    private static String woodSlab = "tile.woodSlab";

    public static void newBridge(PlayerEntity player, ItemStack stack, BlockPos pos1, BlockPos pos2) {
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
        if (!ConfigHandler.isIgnoreSlopeWarnings() && Math.abs(m) > 0.2) {
            ModUtils.tellPlayer(player, Messages.SLOPE_GREAT);
            return;
        }
        b = y1 - m * x1;
        distance = Math.abs(x2 - x1);
        distInt = Math.abs(x2 - x1);
        if (distInt < 2) {
            return;
        }
        if (!player.abilities.isCreativeMode && !hasMaterials(player, distInt - 1)) {
            return;
        }
        for (int x = Math.min(x1, x2) + 1; x <= Math.max(x1, x2) - 1; x++) {
            for (int y = Math.max(y1, y2); y >= Math.min(y1, y2) - distInt / 8 - 1; y--) {
                final double funcVal = m * x + b - distance / 1000 * Math.sin((x - Math.min(x1, x2)) * (Math.PI / distance)) * ConfigHandler.getBridgeDroopFactor() + ConfigHandler.getBridgeYOffset();
                if (y + 0.5 > funcVal && y - 0.5 <= funcVal) {
                    int level;
                    if (funcVal >= y) {
                        if (funcVal >= y + 0.25) {
                            level = 3;
                        } else {
                            level = 2;
                        }
                    } else {
                        if (funcVal >= y - 0.25) {
                            level = 1;
                        } else {
                            level = 0;
                        }
                    }
                    allClear = addSlab(player.world, bridge, x, y + 1, z1, level, rotate) && allClear;
                }
            }
        }

        if (allClear) {
            final Block slab = getSlabs(player);

            if (slab != null && !player.abilities.isCreativeMode) {
                takeMaterials(player, distInt - 1);
                stack.damageItem(ConfigHandler.getBridgeDamage(), player, playerEntity ->
                        playerEntity.sendBreakAnimation(player.getActiveHand()));
            }
            buildBridge(player.world, bridge, slab,0,rotate);
        } else {
            ModUtils.tellPlayer(player, Messages.OBSTRUCTED);
        }
    }

    private static boolean getRotate(BlockPos p1, BlockPos p2) {
        return Math.abs(p1.getX() - p2.getX()) <= Math.abs(p1.getZ() - p2.getZ());
    }

    private static boolean hasMaterials(PlayerEntity player, int dist) {
        boolean noCost = ConfigHandler.getSlabsPerBridge() == 0 && ConfigHandler.getRopePerBridge() == 0;
        if (player.abilities.isCreativeMode || noCost)
            return true;
        final int stringNeeded = 1 + dist / 2;
        int slabsHad = 0;
        int stringHad = 0;

        for (int i = 0; i < 36; i++) {
            final ItemStack stack = player.inventory.mainInventory.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            final Item item = stack.getItem();
            if (item == ContentHandler.rope) {
                stringHad += stack.getCount();
            }
            if (item.isIn(ItemTags.WOODEN_SLABS)) {
                slabsHad += stack.getCount();
            }
        }
        if (slabsHad >= dist && stringHad >= stringNeeded) {
            return true;
        } else {
            ModUtils.tellPlayer(player, Messages.UNDERFUNDED_BRIDGE, dist, stringNeeded);
            return false;
        }
    }

    private static void takeMaterials(PlayerEntity player, int dist) {
        boolean noCost = ConfigHandler.getSlabsPerBridge() == 0 && ConfigHandler.getRopePerBridge() == 0;
        if (player.abilities.isCreativeMode || noCost) {
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
            if (item == ContentHandler.rope) {
                if (stack.getCount() > stringNeeded) {
                	stack.shrink(stringNeeded);
                    stringNeeded = 0;
                }
            } else if (item.isIn(ItemTags.WOODEN_SLABS)) {
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

        isClear = ConfigHandler.isBreakThroughBlocks() || world.isAirBlock(pos) || LadderBuildingHandler.isReplaceable(world, pos,world.getBlockState(pos));
        list.add(new SlabPosHandler(pos, level, rotate));
        if (!isClear) {
            spawnSmoke(world, pos, 15);
        }
        return isClear;
    }

    // Controls if blocks are in the way
    private static void spawnSmoke(World world, BlockPos pos, int times) {

        if (times > 0) {
            world.addParticle(ParticleTypes.EXPLOSION,false, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
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

	private static void buildBridge(final World world, final List<SlabPosHandler> bridge, final Block slabBlock, int index, boolean rotated) {

        SlabPosHandler slab;
        if (index < bridge.size()) {
            slab = bridge.get(index);
            int backLevel = index > 0 ? bridge.get(index-1).getLevel() : 0;
            BlockState state = ModUtils.map.get(slabBlock).getLeft().getDefaultState()
                    .with(RopeBridgeBlock.PROPERTY_HEIGHT, slab.getLevel())
                    .with(RopeBridgeBlock.PROPERTY_BACK,backLevel)
                    .with(RopeBridgeBlock.ROTATED,rotated);

            world.setBlockState(slab.getBlockPos(),state);
            spawnSmoke(world, new BlockPos(slab.getBlockPos().getX(), slab.getBlockPos().getY(), slab.getBlockPos().getZ()), 1);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    buildBridge(world, bridge, slabBlock,index+1,rotated);
                }
            }, 100);
        }
    }

    //todo, fix
    @Nullable
    private static Block getSlabs(PlayerEntity player) {
        for (final ItemStack stack : player.inventory.mainInventory) {
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.getItem().isIn(ItemTags.WOODEN_SLABS))
                return Block.getBlockFromItem(stack.getItem());
        }
        if (player.abilities.isCreativeMode){
            return Blocks.OAK_SLAB;
        }
        return null;
    }
}
