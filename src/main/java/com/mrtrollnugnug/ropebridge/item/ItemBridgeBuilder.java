package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.RopeBridge;
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
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;

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
        final BlockState state = player.world.getBlockState(pos);
        final Block block = state.getBlock();
        if (!player.world.isRemote && player.isCrouching() && isBridgeBlock(player.world.getBlockState(pos).getBlock())) {
            ModUtils.tellPlayer(player, Messages.WARNING_BREAKING);
            //breakBridge(player, player.world, pos);
        }
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

    /**
     * Breaks block at position posIn and recursively spreads to in-line
     * neighbors
     *
     * @param posIn
     *            the position of the block to start breaking bridge from
     */
    private static void breakBridge(final PlayerEntity player, final World worldIn, final BlockPos posIn) {
        worldIn.getServer().execute(() -> {
            int xRange = 0;
            int zRange = 0;
            xRange = 1;
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
                                final BlockState currentBlockState = worldIn.getBlockState(currentPos);
                                if (isBridgeBlock(currentBlockState.getBlock())) {
                                    newQueue.add(currentPos);
                                }
                            }
                        }
                    }
                }

                queue.add(pos);
            }
            Timer timer = new Timer();
            TimerTask task = new BreakTask(queue, worldIn, timer, !player.abilities.isCreativeMode);
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
            if (world.getBlockState(pos).getBlock() instanceof RopeBridgeBlock) {
                ServerLifecycleHooks.getCurrentServer().execute(() -> world.destroyBlock(pos, drop));
            }

            if (queue.isEmpty()) {
                timer.cancel();
            }
        }
    }
}
