package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.block.BridgeSlab;
import com.mrtrollnugnug.ropebridge.item.ItemBridgeBuilder;
import com.mrtrollnugnug.ropebridge.item.ItemBridgeMaterial;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class ContentHandler {

    // Blocks
    private static Block blockBridgeSlab1;

    private static Block blockBridgeSlab2;

    private static Block blockBridgeSlab3;

    private static Block blockBridgeSlab4;

    // Items
    private static Item itemBridgeBuilder;

    private static Item itemBridgeMaterial;

    public static void initBlocks () {

        setBlockBridgeSlab1(ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_1), "bridge_block_1"));
        setBlockBridgeSlab2(ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_2), "bridge_block_2"));
        setBlockBridgeSlab3(ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_3), "bridge_block_3"));
        setBlockBridgeSlab4(ModUtils.registerBlockNoItem(new BridgeSlab(BridgeSlab.AABB_BLOCK_4), "bridge_block_4"));
    }

    public static void initItems () {

        setItemBridgeBuilder(ModUtils.registerItem(new ItemBridgeBuilder(), "bridge_builder"));
        itemBridgeMaterial = ModUtils.registerItem(new ItemBridgeMaterial(), "bridge_builder_material");
    }

    public static void initRecipes () {

        GameRegistry.addRecipe(new ItemStack(itemBridgeMaterial, 1, 0), new Object[] { "i  ", "iii", "i  ", 'i', Items.IRON_INGOT });
        GameRegistry.addRecipe(new ItemStack(itemBridgeMaterial, 1, 1), new Object[] { "iii", "sss", "iii", 'i', Items.IRON_INGOT, 's', Items.STRING });
        GameRegistry.addRecipe(new ItemStack(itemBridgeMaterial, 1, 2), new Object[] { "i f", "sg ", "iww", 'i', Items.IRON_INGOT, 'f', Items.FLINT_AND_STEEL, 's', Items.STRING, 'g', Items.GUNPOWDER, 'w', Blocks.PLANKS });
        GameRegistry.addRecipe(new ItemStack(getItemBridgeBuilder()), new Object[] { "tbh", 't', new ItemStack(itemBridgeMaterial, 1, 0), 'b', new ItemStack(itemBridgeMaterial, 1, 1), 'h', new ItemStack(itemBridgeMaterial, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(Items.STRING, 4), new Object[] { "w", 'w', Blocks.WOOL });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientPreInit () {

        ModUtils.registerItemInvModel(getItemBridgeBuilder());
        ModUtils.registerItemInvModel(itemBridgeMaterial, "bridge_builder", ItemBridgeMaterial.getVarients());
        
        
    }

	public static Block getBlockBridgeSlab1() {
		return blockBridgeSlab1;
	}

	public static void setBlockBridgeSlab1(Block blockBridgeSlab1) {
		ContentHandler.blockBridgeSlab1 = blockBridgeSlab1;
	}

	public static Block getBlockBridgeSlab2() {
		return blockBridgeSlab2;
	}

	public static void setBlockBridgeSlab2(Block blockBridgeSlab2) {
		ContentHandler.blockBridgeSlab2 = blockBridgeSlab2;
	}

	public static Block getBlockBridgeSlab3() {
		return blockBridgeSlab3;
	}

	public static void setBlockBridgeSlab3(Block blockBridgeSlab3) {
		ContentHandler.blockBridgeSlab3 = blockBridgeSlab3;
	}

	public static Block getBlockBridgeSlab4() {
		return blockBridgeSlab4;
	}

	public static void setBlockBridgeSlab4(Block blockBridgeSlab4) {
		ContentHandler.blockBridgeSlab4 = blockBridgeSlab4;
	}

	public static Item getItemBridgeBuilder() {
		return itemBridgeBuilder;
	}

	public static void setItemBridgeBuilder(Item itemBridgeBuilder) {
		ContentHandler.itemBridgeBuilder = itemBridgeBuilder;
	}
	
	
}
