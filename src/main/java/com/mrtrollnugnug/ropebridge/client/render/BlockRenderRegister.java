package com.mrtrollnugnug.ropebridge.client.render;

import com.mrtrollnugnug.ropebridge.block.ModBlocks;
import com.mrtrollnugnug.ropebridge.lib.Constants;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;



public final class BlockRenderRegister {
	public static String modid = Constants.MOD_ID;

	public static void registerBlockRenderer() {
		reg(ModBlocks.bridgeBlock1);
		reg(ModBlocks.bridgeBlock2);
		reg(ModBlocks.bridgeBlock3);
		reg(ModBlocks.bridgeBlock4);
	}

	public static void reg(Block block) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
