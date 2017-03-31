package com.mrtrollnugnug.ropebridge.item;

import java.math.BigDecimal;

import com.mrtrollnugnug.ropebridge.handler.ConfigurationHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class ItemBuilder extends Item
{
    public ItemBuilder()
    {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
        /* TODO
        if (ConfigurationHandler.isZoomOnAim())
            setFov(RopeBridge.getProxy().getFov());
            */
    }

    protected static void rotatePlayerTowards(EntityPlayer player, float target)
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

 
    protected static float getNearestYaw(EntityPlayer player)
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
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (hand == EnumHand.MAIN_HAND) {
            playerIn.setActiveHand(hand);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public abstract void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft);

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (entityIn instanceof EntityPlayer && worldIn.isRemote) {
            final EntityPlayer p = (EntityPlayer) entityIn;
            if (!isSelected || p.getActiveItemStack() != stack) {
               // zoomTowards(getFov());
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    public static boolean equalsZero(double d)
    {
        return BigDecimal.valueOf(d).equals(BigDecimal.ZERO);
    }

    public static RayTraceResult trace(EntityPlayer player)
    {
        return player.rayTrace(ConfigurationHandler.getMaxBridgeDistance(), 1.0f);
    }
/* TODO
 
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        if (player.world.isRemote && player instanceof EntityPlayer) {
            zoomTowards(30);
        }
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
    
    public static float getFov()
    {
        return fov;
    }

    public static void setFov(float newFov)
    {
        fov = newFov;
    }
*/
}
