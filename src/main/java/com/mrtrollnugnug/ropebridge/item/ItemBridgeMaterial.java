package com.mrtrollnugnug.ropebridge.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBridgeMaterial extends Item {

    private static final String[] varients = new String[] { "hook", "barrel", "handle" };

    public ItemBridgeMaterial () {

        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public String getUnlocalizedName (ItemStack stack) {

        final int meta = stack.getMetadata();

        if (!(meta >= 0 && meta < getVarients().length))
            return super.getUnlocalizedName() + "." + getVarients()[0];

        return super.getUnlocalizedName() + "." + getVarients()[meta];
    }

	public static String[] getVarients() {
		return varients;
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {

        for (int meta = 0; meta < getVarients().length; meta++) {
            subItems.add(new ItemStack(this, 1, meta));
        }
    }
}
