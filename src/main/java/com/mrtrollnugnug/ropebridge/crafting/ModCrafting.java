package com.mrtrollnugnug.ropebridge.crafting;

import com.mrtrollnugnug.ropebridge.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {

	public static void initCrafting() {
		GameRegistry.addRecipe(new ItemStack(ModItems.bridgeBuilderHook), new Object[] {"i  ", "iii", "i  ", 'i', Items.IRON_INGOT});
		GameRegistry.addRecipe(new ItemStack(ModItems.bridgeBuilderBarrel), new Object[] {"iii", "sss", "iii", 'i', Items.IRON_INGOT, 's', Items.STRING});
		GameRegistry.addRecipe(new ItemStack(ModItems.bridgeBuilderHandle), new Object[] {"i f", "sg ", "iww", 'i', Items.IRON_INGOT, 'f', Items.FLINT_AND_STEEL, 's', Items.STRING, 'g', Items.GUNPOWDER, 'w', Blocks.PLANKS});
		GameRegistry.addRecipe(new ItemStack(ModItems.bridgeBuilder), new Object[] {"tbh", 't', ModItems.bridgeBuilderHook, 'b', ModItems.bridgeBuilderBarrel, 'h', ModItems.bridgeBuilderHandle});
		GameRegistry.addRecipe(new ItemStack(Items.STRING, 4), new Object[] {"w", 'w', Blocks.WOOL});
	}

}
