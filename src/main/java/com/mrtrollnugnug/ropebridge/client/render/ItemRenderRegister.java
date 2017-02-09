package com.mrtrollnugnug.ropebridge.client.render;

import com.mrtrollnugnug.ropebridge.Main;
import com.mrtrollnugnug.ropebridge.items.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public class ItemRenderRegister {
	public static String modid = Main.MODID;
	
	public static void registerItemRenderer() {
		reg(ModItems.bridgeBuilder);
		reg(ModItems.bridgeBuilderHook);
		reg(ModItems.bridgeBuilderBarrel);
		reg(ModItems.bridgeBuilderHandle);
	}
	public static void reg(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
