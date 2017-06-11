package com.mrtrollnugnug.ropebridge.item;

import java.math.BigDecimal;

import com.mrtrollnugnug.ropebridge.handler.ConfigurationHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class ItemBuilder extends Item {

	public ItemBuilder() {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (hand == EnumHand.MAIN_HAND) {
            playerIn.setActiveHand(hand);
        }
        return super.onItemRightClick(worldIn, playerIn, hand);
    }

    @Override
    public abstract void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft);

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    public static boolean equalsZero(double d) {
        return BigDecimal.valueOf(d).equals(BigDecimal.ZERO);
    }

    public static RayTraceResult trace(EntityPlayer player) {
        return player.rayTrace(ConfigurationHandler.getMaxBridgeDistance(), 1.0f);
    }
}
