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

public final class ContentHandler
{

    // Blocks
    private static Block blockBridgeSlab1;

    private static Block blockBridgeSlab2;

    private static Block blockBridgeSlab3;

    private static Block blockBridgeSlab4;

    private static Block blockRopeLadder;

    // Items
    private static Item itemBridgeBuilder, itemLadderBuilder;

    private static Item itemRope, itemBridgeHook, itemBarrel, itemHandle, itemLadderHook;
    
    public static final CreativeTabs RopeBridgeTab = new CreativeTabs("RopeBridgeTab")
	{
	   		public Item getTabIconItem() {
	   			return ContentHandler.itemBridgeBuilder;
	   		}
	 };

    public static void initBlocks()
    {
        setBlockBridgeSlab1(ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_1, 1), "bridge_block_1"));
        setBlockBridgeSlab2(ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_2, 2), "bridge_block_2"));
        setBlockBridgeSlab3(ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_3, 3), "bridge_block_3"));
        setBlockBridgeSlab4(ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_4, 4), "bridge_block_4"));

        setBlockRopeLadder(ModUtils.registerBlockNoItem(new RopeLadder(), "rope_ladder"));
        GameRegistry.registerTileEntity(TileEntityRopeLadder.class, Constants.MOD_ID + ":rope_ladder_te");
    }

    public static void initItems()
    {
        setItemBridgeBuilder(ModUtils.registerItem(new ItemBridgeBuilder(), "bridge_builder"));
        itemLadderBuilder = ModUtils.registerItem(new ItemLadderBuilder(), "ladder_builder");
        itemBridgeHook = ModUtils.registerItem(new Item(), "bridge_builder_material.hook");
        itemBarrel = ModUtils.registerItem(new Item(), "bridge_builder_material.barrel");
        itemHandle = ModUtils.registerItem(new Item(), "bridge_builder_material.handle");
        itemLadderHook = ModUtils.registerItem(new Item(), "ladder_hook");
        setItemRope(ModUtils.registerItem(new Item(), "rope"));
    }

    public static void initRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(itemBridgeHook), new Object[] { "i  ", "iii", "i  ", 'i', Items.IRON_INGOT });
        GameRegistry.addRecipe(new ItemStack(itemLadderHook), new Object[] { "r  ", "iii", "i  ", 'r', Items.IRON_INGOT, 'r', new ItemStack(itemRope) });
        GameRegistry.addRecipe(new ItemStack(itemBarrel), new Object[] { "iii", "sss", "iii", 'i', Items.IRON_INGOT, 's', Items.STRING });
        GameRegistry.addRecipe(new ItemStack(itemHandle), new Object[] { "i f", "sg ", "iww", 'i', Items.IRON_INGOT, 'f', Items.FLINT_AND_STEEL, 's', Items.STRING, 'g', Items.GUNPOWDER, 'w', Blocks.PLANKS });
        GameRegistry.addRecipe(new ItemStack(getItemBridgeBuilder()), new Object[] { "tbh", 't', new ItemStack(itemBridgeHook), 'b', new ItemStack(itemBarrel), 'h', new ItemStack(itemHandle) });
        GameRegistry.addRecipe(new ItemStack(Items.STRING, 4), new Object[] { "w", 'w', Blocks.WOOL });
        GameRegistry.addRecipe(new ItemStack(itemLadderBuilder), new Object[] { "tbh", 't', new ItemStack(itemLadderHook), 'b', new ItemStack(itemBarrel), 'h', new ItemStack(itemHandle) });
        GameRegistry.addRecipe(new ItemStack(itemRope), new Object[] { "sv ", "vs ", "sv ", 's', Items.STRING, 'v', Blocks.VINE} );
        //TODO add rope recipe
    }

    @SideOnly(Side.CLIENT)
    public static void onClientPreInit()
    {
        ModUtils.registerItemInvModel(getItemBridgeBuilder());
        ModUtils.registerItemInvModel(itemBridgeHook, 0, Constants.MOD_ID + ":bridge_builder_hook");
        ModUtils.registerItemInvModel(itemBarrel, 0, Constants.MOD_ID + ":bridge_builder_barrel");
        ModUtils.registerItemInvModel(itemHandle, 0, Constants.MOD_ID + ":bridge_builder_handle");
        ModUtils.registerItemInvModel(itemLadderBuilder);
        ModUtils.registerItemInvModel(itemLadderHook);
        ModUtils.registerItemInvModel(itemRope);
    }

    public static Block getBlockBridgeSlab1()
    {
        return blockBridgeSlab1;
    }

    public static void setBlockBridgeSlab1(Block blockBridgeSlab1)
    {
        ContentHandler.blockBridgeSlab1 = blockBridgeSlab1;
    }

    public static Block getBlockBridgeSlab2()
    {
        return blockBridgeSlab2;
    }

    public static void setBlockBridgeSlab2(Block blockBridgeSlab2)
    {
        ContentHandler.blockBridgeSlab2 = blockBridgeSlab2;
    }

    public static Block getBlockBridgeSlab3()
    {
        return blockBridgeSlab3;
    }

    public static void setBlockBridgeSlab3(Block blockBridgeSlab3)
    {
        ContentHandler.blockBridgeSlab3 = blockBridgeSlab3;
    }

    public static Block getBlockBridgeSlab4()
    {
        return blockBridgeSlab4;
    }

    public static void setBlockBridgeSlab4(Block blockBridgeSlab4)
    {
        ContentHandler.blockBridgeSlab4 = blockBridgeSlab4;
    }

    public static void setBlockRopeLadder(Block blockRopeLadder)
    {
        ContentHandler.blockRopeLadder = blockRopeLadder;
    }

    public static Block getBlockRopeLadder()
    {
        return blockRopeLadder;
    }

    public static Item getItemBridgeBuilder()
    {
        return itemBridgeBuilder;
    }

    public static Item getItemRope()
    {
        return itemRope;
    }

    public static void setItemBridgeBuilder(Item itemBridgeBuilder)
    {
        ContentHandler.itemBridgeBuilder = itemBridgeBuilder;
    }

    public static void setItemRope(Item itemRope)
    {
        ContentHandler.itemRope = itemRope;
    }
}
