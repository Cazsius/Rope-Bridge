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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
	   		public ItemStack getTabIconItem() {
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
        itemBridgeBuilder = ModUtils.registerItem(new ItemBridgeBuilder(), "bridge_builder");
        itemLadderBuilder = ModUtils.registerItem(new ItemLadderBuilder(), "ladder_builder");
        itemBridgeHook = ModUtils.registerItem(new Item(), "bridge_builder_material.hook");
        itemBarrel = ModUtils.registerItem(new Item(), "bridge_builder_material.barrel");
        itemHandle = ModUtils.registerItem(new Item(), "bridge_builder_material.handle");
        itemLadderHook = ModUtils.registerItem(new Item(), "ladder_hook");
        itemRope = ModUtils.registerItem(new Item(), "rope");
    }

    public static void initRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(itemBridgeHook), new Object[] { "i  ", "iii", "i  ", 'i', Items.IRON_INGOT });
        GameRegistry.addRecipe(new ItemStack(itemLadderHook), new Object[] { "r  ", "iii", "i  ", 'r', Items.IRON_INGOT, 'r', new ItemStack(itemRope) });
        GameRegistry.addRecipe(new ItemStack(itemBarrel), new Object[] { "iii", "sss", "iii", 'i', Items.IRON_INGOT, 's', Items.STRING });
        GameRegistry.addRecipe(new ItemStack(itemHandle), new Object[] { "i f", "sg ", "iww", 'i', Items.IRON_INGOT, 'f', Items.FLINT_AND_STEEL, 's', Items.STRING, 'g', Items.GUNPOWDER, 'w', Blocks.PLANKS });
        GameRegistry.addRecipe(new ItemStack(itemBridgeBuilder), new Object[] { "tbh", 't', new ItemStack(itemBridgeHook), 'b', new ItemStack(itemBarrel), 'h', new ItemStack(itemHandle) });
        GameRegistry.addRecipe(new ItemStack(Items.STRING, 4), new Object[] { "w", 'w', Blocks.WOOL });
        GameRegistry.addRecipe(new ItemStack(itemLadderBuilder), new Object[] { "tbh", 't', new ItemStack(itemLadderHook), 'b', new ItemStack(itemBarrel), 'h', new ItemStack(itemHandle) });
        GameRegistry.addRecipe(new ItemStack(itemRope), new Object[] { "sv ", "vs ", "sv ", 's', Items.STRING, 'v', Blocks.VINE} );
        //TODO add rope recipe
    }

    @SideOnly(Side.CLIENT)
    public static void onClientPreInit()
    {
        ModUtils.registerItemInvModel(itemBridgeBuilder);
        ModUtils.registerItemInvModel(itemBridgeHook, 0, Constants.MOD_ID + ":bridge_builder_hook");
        ModUtils.registerItemInvModel(itemBarrel, 0, Constants.MOD_ID + ":bridge_builder_barrel");
        ModUtils.registerItemInvModel(itemHandle, 0, Constants.MOD_ID + ":bridge_builder_handle");
        ModUtils.registerItemInvModel(itemLadderBuilder);
        ModUtils.registerItemInvModel(itemLadderHook);
        ModUtils.registerItemInvModel(itemRope);
    }
}
