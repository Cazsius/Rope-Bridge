package com.mrtrollnugnug.ropebridge.block;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;

public class RopeLadder extends BlockLadder {
	
	public RopeLadder() {
		super();
        setSoundType(SoundType.WOOD);
        setCreativeTab(CreativeTabs.COMBAT);
	}

	public int getMetadata(int damage) {

	        return damage;
	    }
}
