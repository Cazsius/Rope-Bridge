package com.mrtrollnugnug.ropebridge.item;

import java.math.BigDecimal;

import com.mrtrollnugnug.ropebridge.RopeBridge;
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
    private static float fov = 0;

    public ItemBuilder()
    {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
        if (ConfigurationHandler.isZoomOnAim())
            setFov(RopeBridge.getProxy().getFov());
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
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        if (player.world.isRemote && player instanceof EntityPlayer) {
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
}
