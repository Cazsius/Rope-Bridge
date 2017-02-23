package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.item.BasicItem;
import com.mrtrollnugnug.ropebridge.item.ItemBridgeBuilder;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContentHandler {

	//Blocks
	
	
	//Items
	public static Item itemBridgeBuilder;
	public static Item itemBridgeBuilderHook;
	public static Item itemBridgeBuilderBarrel;
	public static Item itemBridgeBuilderHandle;
	
	public static void initBlocks () {
		
	 }
	
	public static void initItems () {
		itemBridgeBuilder = new ItemBridgeBuilder();
		itemBridgeBuilderHook = new BasicItem();
		itemBridgeBuilderBarrel = new BasicItem();
		itemBridgeBuilderHandle = new BasicItem();
		
		
		ModUtils.registerItem(itemBridgeBuilder, "bridge_builder");
		ModUtils.registerItem(itemBridgeBuilderHook, "bridge_builder_hook");
		ModUtils.registerItem(itemBridgeBuilderBarrel, "bridge_builder_barrel");
		ModUtils.registerItem(itemBridgeBuilderHandle, "bridge_builder_handle");
		
	}
	
	public static void initRecipes () {
		GameRegistry.addRecipe(new ItemStack(itemBridgeBuilderHook), new Object[] {"i  ", "iii", "i  ", 'i', Items.IRON_INGOT});
		GameRegistry.addRecipe(new ItemStack(itemBridgeBuilderBarrel), new Object[] {"iii", "sss", "iii", 'i', Items.IRON_INGOT, 's', Items.STRING});
		GameRegistry.addRecipe(new ItemStack(itemBridgeBuilderHandle), new Object[] {"i f", "sg ", "iww", 'i', Items.IRON_INGOT, 'f', Items.FLINT_AND_STEEL, 's', Items.STRING, 'g', Items.GUNPOWDER, 'w', Blocks.PLANKS});
		GameRegistry.addRecipe(new ItemStack(itemBridgeBuilder), new Object[] {"tbh", 't', itemBridgeBuilderHook, 'b', itemBridgeBuilderBarrel, 'h', itemBridgeBuilderHandle});
		GameRegistry.addRecipe(new ItemStack(Items.STRING, 4), new Object[] {"w", 'w', Blocks.WOOL});		
	}
	

	 @SideOnly(Side.CLIENT)
	    public static void onClientPreInit () {
	 }
}
