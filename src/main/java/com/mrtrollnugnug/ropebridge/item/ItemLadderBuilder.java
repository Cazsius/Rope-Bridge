package com.mrtrollnugnug.ropebridge.item;

import com.mrtrollnugnug.ropebridge.handler.LadderBuildingHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLadderBuilder extends Item
{
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        LadderBuildingHandler.newLadder(pos.offset(facing), playerIn, worldIn, facing, stack);
        return EnumActionResult.SUCCESS;
    }
}
