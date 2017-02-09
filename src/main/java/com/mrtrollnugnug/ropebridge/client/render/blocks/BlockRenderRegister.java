package com.mrtrollnugnug.ropebridge.client.render.blocks;

import com.mrtrollnugnug.ropebridge.Main;
import com.mrtrollnugnug.ropebridge.blocks.ModBlocks;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;



public final class BlockRenderRegister {
	public static String modid = Main.MODID;

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
