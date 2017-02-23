package com.mrtrollnugnug.ropebridge.client.render;


import net.minecraft.item.Item;



public final class BlockRenderRegister {

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
