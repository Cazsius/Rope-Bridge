package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.block.BridgeSlab;
import com.mrtrollnugnug.ropebridge.block.RopeLadder;
import com.mrtrollnugnug.ropebridge.block.TileEntityRopeLadder;
import com.mrtrollnugnug.ropebridge.item.ItemBridgeBuilder;
import com.mrtrollnugnug.ropebridge.item.ItemLadderBuilder;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class ContentHandler {

    // Blocks
    public static Block blockBridgeSlab1, blockBridgeSlab2, blockBridgeSlab3, blockBridgeSlab4;
    public static Block blockRopeLadder;

    // Items
    public static Item itemBridgeBuilder, itemLadderBuilder;
    public static Item itemRope, itemBridgeHook, itemBarrel, itemHandle, itemLadderHook;
    
    public static final CreativeTabs RopeBridgeTab = new CreativeTabs("RopeBridgeTab") {
	   		public ItemStack createIcon() {
	   			return new ItemStack(ContentHandler.itemBridgeBuilder);
	   		}
	 };

    public static void initBlocks() {
    	blockBridgeSlab1 = ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_1, 1), "bridge_block_1");
    	blockBridgeSlab2 = ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_2, 2), "bridge_block_2");
    	blockBridgeSlab3 = ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_3, 3), "bridge_block_3");
    	blockBridgeSlab4 = ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_4, 4), "bridge_block_4");
    	blockRopeLadder = ModUtils.registerBlockNoItem(new RopeLadder(), "rope_ladder");
        GameRegistry.registerTileEntity(TileEntityRopeLadder.class, Constants.MOD_ID + ":rope_ladder_te");
    }

    public static void initItems() {
        itemLadderBuilder = ModUtils.registerItem(new ItemLadderBuilder(), "ladder_builder");
        itemBridgeHook = ModUtils.registerItem(new Item(), "bridge_builder_material.hook");
        itemBarrel = ModUtils.registerItem(new Item(), "bridge_builder_material.barrel");
        itemHandle = ModUtils.registerItem(new Item(), "bridge_builder_material.handle");
        itemLadderHook = ModUtils.registerItem(new Item(), "ladder_hook");
        itemBridgeBuilder = ModUtils.registerItem(new ItemBridgeBuilder(), "bridge_builder");
        itemRope = ModUtils.registerItem(new Item(), "rope");
    }

    @SideOnly(Side.CLIENT)
    public static void onClientPreInit()
    {
        ModUtils.registerItemInvModel(itemBridgeHook, 0, Constants.MOD_ID + ":bridge_builder_hook");
        ModUtils.registerItemInvModel(itemBarrel, 0, Constants.MOD_ID + ":bridge_builder_barrel");
        ModUtils.registerItemInvModel(itemHandle, 0, Constants.MOD_ID + ":bridge_builder_handle");
        ModUtils.registerItemInvModel(itemBridgeBuilder);
        ModUtils.registerItemInvModel(itemLadderBuilder);
        ModUtils.registerItemInvModel(itemLadderHook);
        ModUtils.registerItemInvModel(itemRope);
    }
}
