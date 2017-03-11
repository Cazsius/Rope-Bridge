package com.mrtrollnugnug.ropebridge.item;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import com.mrtrollnugnug.ropebridge.Messages;
import com.mrtrollnugnug.ropebridge.RopeBridge;
import com.mrtrollnugnug.ropebridge.block.BridgeSlab;
import com.mrtrollnugnug.ropebridge.handler.ConfigurationHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import com.mrtrollnugnug.ropebridge.network.BridgeMessage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBridgeBuilder extends Item
{
    private static float fov = 0;

    public ItemBridgeBuilder()
    {
        super();
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
        if (ConfigurationHandler.isZoomOnAim())
            setFov(RopeBridge.getProxy().getFov());
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        playerIn.addStat(RopeBridge.getCraftAchievement());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (hand == EnumHand.MAIN_HAND) {
            playerIn.setActiveHand(hand);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer && world.isRemote) {
            final EntityPlayer player = (EntityPlayer) entityLiving;
            if (this.getMaxItemUseDuration(stack) - timeLeft > 10) {
                if (!player.onGround) {
                    ModUtils.tellPlayer(player, Messages.NOT_ON_GROUND);
                }
                else {
                    final RayTraceResult hit = trace(player);
                    if (hit.typeOfHit == Type.BLOCK) {
                        final BlockPos floored = new BlockPos(Math.floor(player.posX), Math.floor(player.posY) - 1, Math.floor(player.posZ));
                        // Vector offsets
                        double xOffset = 0.0D;
                        double yOffset = 0.0D;
                        double zOffset = 0.0D;
                        if (equalsZero(hit.hitVec.xCoord % 1) && hit.hitVec.xCoord < floored.getX()) {
                            xOffset = -0.8D;
                        }
                        if (equalsZero(hit.hitVec.zCoord % 1) && hit.hitVec.zCoord < floored.getZ()) {
                            zOffset = -0.8D;
                        }
                        if (equalsZero(hit.hitVec.yCoord % 1)) {
                            if (player.rotationPitch > 0) { // Looking from top
                                yOffset = -0.8D;
                            }
                        }
                        RopeBridge.getSnw().sendToServer(new BridgeMessage(floored, new BlockPos(hit.hitVec.xCoord + xOffset, hit.hitVec.yCoord + yOffset, hit.hitVec.zCoord + zOffset)));
                    }
                }
            }
        }
    }

    private static boolean equalsZero(double d)
    {
        return BigDecimal.valueOf(d).equals(BigDecimal.ZERO);
    }

    private static RayTraceResult trace(EntityPlayer player)
    {
        return player.rayTrace(ConfigurationHandler.getMaxBridgeDistance(), 1.0f);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        if (!player.world.isRemote && count % 10 == 0) {
        }
        else if (player.world.isRemote && player instanceof EntityPlayer) {
            final EntityPlayer p = (EntityPlayer) player;
            rotatePlayerTowards(p, getNearestYaw(p));
            zoomTowards(30);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (entityIn instanceof EntityPlayer && worldIn.isRemote) {
            final EntityPlayer p = (EntityPlayer) entityIn;
            if (!isSelected || p.getActiveItemStack() != stack) {
                zoomTowards(getFov());
            }
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
    {
        final IBlockState state = player.world.getBlockState(pos);
        final Block block = state.getBlock();
        if (!player.world.isRemote && player.isSneaking() && isBridgeBlock(player.world.getBlockState(pos).getBlock())) {
            breakBridge(player, player.world, pos, block.getMetaFromState(state));
        }
        return false;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            if (RopeBridge.getProxy().getPlayer().isSneaking() && isBridgeBlock(state.getBlock())) {
                ModUtils.tellPlayer(RopeBridge.getProxy().getPlayer(), Messages.WARNING_BREAKING);
                return 0.3F;
            }
        }
        return super.getStrVsBlock(stack, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add("- Hold right-click to build");
        tooltip.add("- Sneak to break whole bridge");
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    private static boolean isBridgeBlock(Block blockIn)
    {

        return blockIn == ContentHandler.getBlockBridgeSlab1() || blockIn == ContentHandler.getBlockBridgeSlab2() || blockIn == ContentHandler.getBlockBridgeSlab3() || blockIn == ContentHandler.getBlockBridgeSlab4();
    }

    private static float getNearestYaw(EntityPlayer player)
    {

        float yaw = player.rotationYaw % 360;
        if (yaw < 0) {
            yaw += 360;
        }
        if (yaw < 45)
            return 0F;
        if (yaw > 45 && yaw <= 135)
            return 90F;
        else if (yaw > 135 && yaw <= 225)
            return 180F;
        else if (yaw > 225 && yaw <= 315)
            return 270F;
        else
            return 360F;
    }

    private static void rotatePlayerTowards(EntityPlayer player, float target)
    {
        float yaw = player.rotationYaw % 360;
        if (yaw < 0) {
            yaw += 360;
        }
        rotatePlayerTo(player, yaw + (target - yaw) / 4);
    }

    private static void rotatePlayerTo(EntityPlayer player, float yaw)
    {
        final float original = player.rotationYaw;
        player.rotationYaw = yaw;
        player.prevRotationYaw += player.rotationYaw - original;
    }

    private static void zoomTowards(float toFov)
    {
        if (ConfigurationHandler.isZoomOnAim() && toFov > 0) {
            final float currentFov = Minecraft.getMinecraft().gameSettings.fovSetting;
            if (Math.abs(currentFov - toFov) > 0.00001) {
                float change = (toFov - currentFov) / 4;
                if (change < 0.1 && change > -0.1)
                    zoomTo(toFov);
                else
                    zoomTo(currentFov + change);
            }
        }
    }

    private static void zoomTo(float toFov)
    {
        if (ConfigurationHandler.isZoomOnAim() && toFov > 0) {
            Minecraft.getMinecraft().gameSettings.fovSetting = toFov;
        }
    }

    /**
     * Breaks block at position posIn and recursively spreads to in-line
     * neighbors
     *
     * @param posIn
     *            the position of the block to start breaking bridge from
     */
    private static void breakBridge(final EntityPlayer player, final World worldIn, final BlockPos posIn, final int meta)
    {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
            int xRange = 0;
            int zRange = 0;
            if (meta % 2 == 0) {
                xRange = 1;
            }
            else {
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
                            }
                            else {
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

    public static float getFov()
    {
        return fov;
    }

    public static void setFov(float fov)
    {
        ItemBridgeBuilder.fov = fov;
    }

    private static class BreakTask extends TimerTask
    {
        private final Queue<BlockPos> queue;
        private final World world;
        private final Timer timer;
        private final boolean drop;

        public BreakTask(Queue<BlockPos> queue, World world, Timer timer, boolean drop)
        {
            super();
            this.queue = queue;
            this.world = world;
            this.timer = timer;
            this.drop = drop;
        }

        @Override
        public void run()
        {
            BlockPos pos = queue.remove();
            if (world.getBlockState(pos).getBlock() instanceof BridgeSlab)
                FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> world.destroyBlock(pos, drop));
            if (queue.isEmpty())
                timer.cancel();
        }
    }
}
