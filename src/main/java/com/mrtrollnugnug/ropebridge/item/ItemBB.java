package com.mrtrollnugnug.ropebridge.item;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.mrtrollnugnug.ropebridge.RopeBridge;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.network.BuildMessage;

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
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBB extends Item {

    public ItemBB () {
        super();
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
    }

    @Override
    public void onCreated (ItemStack stack, World worldIn, EntityPlayer playerIn) {

        playerIn.addStat(RopeBridge.craftAchievement);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick (ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        if (hand == EnumHand.MAIN_HAND) {
            playerIn.setActiveHand(hand);
            if (worldIn.isRemote) {
                fov = Minecraft.getMinecraft().gameSettings.fovSetting;
            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public void onUsingTick (ItemStack stack, EntityLivingBase player, int count) {

        if (!player.world.isRemote && count % 10 == 0) {
            // TODO play clicking sound here
            // player.world.playSound(x, y, z, soundIn, category, volume, pitch,
            // distanceDelay);
        }
        else if (player.world.isRemote && player instanceof EntityPlayer) {
            final EntityPlayer p = (EntityPlayer) player;
            rotatePlayerTowards(p, getNearestYaw(p));
            zoomTowards(30);
        }
    }

    @Override
    public void onPlayerStoppedUsing (ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {

        if (entityLiving instanceof EntityPlayer && world.isRemote) {
            final EntityPlayer player = (EntityPlayer) entityLiving;
            if (this.getMaxItemUseDuration(stack) - timeLeft > 10) {
                if (!player.onGround) {
                    tellPlayer(player, "You must be standing on something to build a bridge!");
                }
                else {
                    final RayTraceResult hit = raytrace(player);
                    if (hit.typeOfHit == Type.BLOCK) {
                        final BlockPos floored = new BlockPos(Math.floor(player.posX), Math.floor(player.posY) - 1, Math.floor(player.posZ));
                        // Vector offsets
                        double xOffset = 0.0D;
                        double yOffset = 0.0D;
                        double zOffset = 0.0D;
                        if (hit.hitVec.xCoord % 1 == 0 && hit.hitVec.xCoord < floored.getX()) {
                            xOffset = -0.8D;
                        }
                        if (hit.hitVec.zCoord % 1 == 0 && hit.hitVec.zCoord < floored.getZ()) {
                            zOffset = -0.8D;
                        }
                        if (hit.hitVec.yCoord % 1 == 0) {
                            if (player.rotationPitch > 0) { // Looking from top
                                yOffset = -0.8D;
                            }
                        }
                        RopeBridge.snw.sendToServer(new BuildMessage(floored, new BlockPos(hit.hitVec.xCoord + xOffset, hit.hitVec.yCoord + yOffset, hit.hitVec.zCoord + zOffset)));
                        // BridgeBuildingHandler.newBridge(player, fov, stack,
                        // -1, floored, new BlockPos(hit.hitVec.xCoord +
                        // xOffset, hit.hitVec.yCoord + yOffset,
                        // hit.hitVec.zCoord + zOffset));
                    }
                }
            }
        }
    }

    private static RayTraceResult raytrace (EntityPlayer player) {

        return player.rayTrace(RopeBridge.maxBridgeDistance, 1.0f);
    }

    private static float fov = 0;

    @Override
    public void onUpdate (ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if (entityIn instanceof EntityPlayer && worldIn.isRemote) {
            final EntityPlayer p = (EntityPlayer) entityIn;
            if (!isSelected || p.getActiveItemStack() != stack) {
                zoomTowards(fov);
            }
        }
    }

    @Override
    public boolean onBlockStartBreak (ItemStack itemstack, BlockPos pos, EntityPlayer player) {

        final IBlockState state = player.world.getBlockState(pos);
        final Block block = state.getBlock();
        if (!player.world.isRemote && player.isSneaking() && isBridgeBlock(player.world.getBlockState(pos).getBlock())) {
            breakBridge(player.world, pos, block.getMetaFromState(state));
        }
        return false;
    }

    @Override
    public float getStrVsBlock (ItemStack stack, IBlockState state) {

        if (FMLCommonHandler.instance().getSide().isClient()) {
            if (RopeBridge.proxy.getPlayer().isSneaking() && isBridgeBlock(state.getBlock())) {
                tellPlayer(RopeBridge.proxy.getPlayer(), "WARNING! Breaking whole bridge!");
                return 0.3F;
            }
        }
        return super.getStrVsBlock(stack, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        tooltip.add("- Hold right-click to build");
        tooltip.add("- Sneak to break whole bridge");
    }

    @Override
    public int getMaxItemUseDuration (ItemStack stack) {

        return 72000;
    }

    private static void tellPlayer (EntityPlayer player, String message) {

        player.sendMessage(new TextComponentString("[Rope Bridge]: " + message).setStyle(new Style().setColor(TextFormatting.DARK_AQUA)));
    }

    private static boolean isBridgeBlock (Block blockIn) {

        return blockIn == ContentHandler.blockBridgeSlab1 || blockIn == ContentHandler.blockBridgeSlab2 || blockIn == ContentHandler.blockBridgeSlab3 || blockIn == ContentHandler.blockBridgeSlab4;
    }

    private static float getNearestYaw (EntityPlayer player) {

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

    private static void rotatePlayerTowards (EntityPlayer player, float target) {

        float yaw = player.rotationYaw % 360;
        if (yaw < 0) {
            yaw += 360;
        }
        rotatePlayerTo(player, yaw + (target - yaw) / 4);
    }

    private static void rotatePlayerTo (EntityPlayer player, float yaw) {

        final float original = player.rotationYaw;
        player.rotationYaw = yaw;
        player.prevRotationYaw += player.rotationYaw - original;
    }

    private static void zoomTowards (float toFov) {

        if (RopeBridge.zoomOnAim && toFov != 0) {
            final float currentFov = Minecraft.getMinecraft().gameSettings.fovSetting;
            if (Math.round(currentFov) != toFov) {
                zoomTo(currentFov + (toFov - currentFov) / 4);
            }
        }
    }

    private static void zoomTo (float toFov) {

        if (RopeBridge.zoomOnAim && toFov != 0) {
            Minecraft.getMinecraft().gameSettings.fovSetting = toFov;
        }
    }

    /**
     * Breaks block at position posIn and recursively spreads to in-line neighbors
     *
     * @param posIn the position of the block to start breaking bridge from
     */
    private static void breakBridge (final World worldIn, final BlockPos posIn, final int meta) {

        worldIn.destroyBlock(posIn, true);
        int xRange = 0;
        int zRange = 0;
        if (meta % 2 == 0) {
            xRange = 1;
        }
        else {
            zRange = 1;
        }
        for (int x = posIn.getX() - xRange; x <= posIn.getX() + xRange; x++) {
            for (int y = posIn.getY() - 1; y <= posIn.getY() + 1; y++) {
                for (int z = posIn.getZ() - zRange; z <= posIn.getZ() + zRange; z++) {
                    if (x - posIn.getX() == 0 && z - posIn.getZ() == 0) {
                        // No bridge
                    }
                    else {
                        final BlockPos currentPos = new BlockPos(x, y, z);
                        final IBlockState currentBlockState = worldIn.getBlockState(currentPos);
                        if (isBridgeBlock(currentBlockState.getBlock()) && currentBlockState.getBlock().getMetaFromState(currentBlockState) == meta) {
                            new Timer().schedule(new TimerTask() {

                                @Override
                                public void run () {

                                    breakBridge(worldIn, currentPos, meta);
                                }
                            }, 100);
                        }
                    }
                }
            }
        }
    }
}
