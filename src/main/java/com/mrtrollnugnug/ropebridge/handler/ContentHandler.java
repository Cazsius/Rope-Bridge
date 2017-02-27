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

public class ContentHandler {

    // Blocks
    public static Block blockBridgeSlab1;

    public static Block blockBridgeSlab2;

    public static Block blockBridgeSlab3;

    public static Block blockBridgeSlab4;

    // Items
    public static Item itemBridgeBuilder;

    public static Item itemBridgeMaterial;

    public static void initBlocks () {

        blockBridgeSlab1 = new BridgeSlab(BridgeSlab.AABB_BLOCK_1);
        blockBridgeSlab2 = new BridgeSlab(BridgeSlab.AABB_BLOCK_2);
        blockBridgeSlab3 = new BridgeSlab(BridgeSlab.AABB_BLOCK_3);
        blockBridgeSlab4 = new BridgeSlab(BridgeSlab.AABB_BLOCK_4);

        ModUtils.registerBlock(blockBridgeSlab1, "bridge_block_1");
        ModUtils.registerBlock(blockBridgeSlab2, "bridge_block_2");
        ModUtils.registerBlock(blockBridgeSlab3, "bridge_block_3");
        ModUtils.registerBlock(blockBridgeSlab4, "bridge_block_4");
    }

    public static void initItems () {

        itemBridgeBuilder = new ItemBridgeBuilder();
        itemBridgeMaterial = new ItemBridgeMaterial();

        ModUtils.registerItem(itemBridgeBuilder, "bridge_builder");
        ModUtils.registerItem(itemBridgeMaterial, "bridge_builder_material");

    }

    public static void initRecipes () {

        GameRegistry.addRecipe(new ItemStack(itemBridgeMaterial, 1, 0), new Object[] { "i  ", "iii", "i  ", 'i', Items.IRON_INGOT });
        GameRegistry.addRecipe(new ItemStack(itemBridgeMaterial, 1, 1), new Object[] { "iii", "sss", "iii", 'i', Items.IRON_INGOT, 's', Items.STRING });
        GameRegistry.addRecipe(new ItemStack(itemBridgeMaterial, 1, 2), new Object[] { "i f", "sg ", "iww", 'i', Items.IRON_INGOT, 'f', Items.FLINT_AND_STEEL, 's', Items.STRING, 'g', Items.GUNPOWDER, 'w', Blocks.PLANKS });
        GameRegistry.addRecipe(new ItemStack(itemBridgeBuilder), new Object[] { "tbh", 't', new ItemStack(itemBridgeMaterial, 1, 0), 'b', new ItemStack(itemBridgeMaterial, 1, 1), 'h', new ItemStack(itemBridgeMaterial, 1, 2) });
        GameRegistry.addRecipe(new ItemStack(Items.STRING, 4), new Object[] { "w", 'w', Blocks.WOOL });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientPreInit () {

        ModUtils.registerItemInvModel(itemBridgeBuilder);
        ModUtils.registerItemInvModel(itemBridgeMaterial, "bridge_builder", ItemBridgeMaterial.varients);
    }
}
