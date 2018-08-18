package com.mrtrollnugnug.ropebridge.item;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import com.mrtrollnugnug.ropebridge.RopeBridge;
import com.mrtrollnugnug.ropebridge.block.BridgeSlab;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants.Messages;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import com.mrtrollnugnug.ropebridge.network.BridgeMessage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBridgeBuilder extends ItemBuilder {

	public ItemBridgeBuilder() {
        super();
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        super.onUsingTick(stack, player, count);
        if (player.world.isRemote && player instanceof EntityPlayer) {
            final EntityPlayer p = (EntityPlayer) player;
            rotatePlayerTowards(p, getNearestYaw(p));
        }
    }

    private static void rotatePlayerTowards(EntityPlayer player, float target) {
        float yaw = player.rotationYaw % 360;
        if (yaw < 0) {
            yaw += 360;
        }
        rotatePlayerTo(player, yaw + (target - yaw) / 4);
    }

    private static void rotatePlayerTo(EntityPlayer player, float yaw) {
        final float original = player.rotationYaw;
        player.rotationYaw = yaw;
        player.prevRotationYaw += player.rotationYaw - original;
    }

    private static float getNearestYaw(EntityPlayer player) {
        float yaw = player.rotationYaw % 360;
        if (yaw < 0) {
            yaw += 360;
        } if (yaw < 45) {
            return 0F;
        } if (yaw > 45 && yaw <= 135) {
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
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer && world.isRemote) {
            final EntityPlayer player = (EntityPlayer) entityLiving;
            if (this.getMaxItemUseDuration(stack) - timeLeft > 10) {
                if (!player.onGround) {
                    ModUtils.tellPlayer(player, Messages.NOT_ON_GROUND);
                } else {
                    final RayTraceResult hit = trace(player);
                    if (hit.typeOfHit == Type.BLOCK) {
                        final BlockPos floored = new BlockPos(Math.floor(player.posX), Math.floor(player.posY) - 1, Math.floor(player.posZ)).down();
                        BlockPos target = hit.getBlockPos();
                        RopeBridge.getSnw().sendToServer(new BridgeMessage(floored, target));
                    }
                }
            }
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        final IBlockState state = player.world.getBlockState(pos);
        final Block block = state.getBlock();
        if (!player.world.isRemote && player.isSneaking() && isBridgeBlock(player.world.getBlockState(pos).getBlock())) {
            ModUtils.tellPlayer(RopeBridge.getProxy().getPlayer(), Messages.WARNING_BREAKING);
            breakBridge(player, player.world, pos, block.getMetaFromState(state));
        }
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            if (RopeBridge.getProxy().getPlayer().isSneaking() && isBridgeBlock(state.getBlock())) {
                return 0.3F;
            }
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("- Hold right-click to build");
        tooltip.add("- Sneak to break whole bridge");
    }

    private static boolean isBridgeBlock(Block blockIn) {
        return blockIn == ContentHandler.blockBridgeSlab1 || blockIn == ContentHandler.blockBridgeSlab2 || blockIn == ContentHandler.blockBridgeSlab3 || blockIn == ContentHandler.blockBridgeSlab4;
    }

    /**
     * Breaks block at position posIn and recursively spreads to in-line
     * neighbors
     *
     * @param posIn
     *            the position of the block to start breaking bridge from
     */
    private static void breakBridge(final EntityPlayer player, final World worldIn, final BlockPos posIn, final int meta) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
            int xRange = 0;
            int zRange = 0;
            if (meta % 2 == 0) {
                xRange = 1;
            } else {
                zRange = 1;
            }
            Queue<BlockPos> newQueue = new LinkedList<>();
            newQueue.add(posIn);
            Queue<BlockPos> queue = new LinkedList<>();
            queue.add(posIn);

            while (!newQueue.isEmpty()) {
                BlockPos pos = newQueue.remove();
                for (int x = pos.getX() - xRange; x <= pos.getX() + xRange; x++) {
                    for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++) {
                        for (int z = pos.getZ() - zRange; z <= pos.getZ() + zRange; z++) {
                            final BlockPos currentPos = new BlockPos(x, y, z);
                            if ((x - pos.getX() == 0 && z - pos.getZ() == 0) || queue.contains(currentPos)) {
                            } else {
                                final IBlockState currentBlockState = worldIn.getBlockState(currentPos);
                                if (isBridgeBlock(currentBlockState.getBlock()) && currentBlockState.getBlock().getMetaFromState(currentBlockState) == meta) {
                                    newQueue.add(currentPos);
                                }
                            }
                        }
                    }
                }

                queue.add(pos);
            }
            Timer timer = new Timer();
            TimerTask task = new BreakTask(queue, worldIn, timer, !player.capabilities.isCreativeMode);
            timer.schedule(task, 100, 100);
        });
    }

    private static class BreakTask extends TimerTask {
        private final Queue<BlockPos> queue;
        private final World world;
        private final Timer timer;
        private final boolean drop;

        public BreakTask(Queue<BlockPos> queue, World world, Timer timer, boolean drop) {
            super();
            this.queue = queue;
            this.world = world;
            this.timer = timer;
            this.drop = drop;
        }

        @Override
        public void run() {
            BlockPos pos = queue.remove();
            if (world.getBlockState(pos).getBlock() instanceof BridgeSlab) {
                FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> world.destroyBlock(pos, drop));
            }
            if (queue.isEmpty()) {
                timer.cancel();
            }
        }
    }
}
