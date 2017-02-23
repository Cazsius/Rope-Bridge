package com.mrtrollnugnug.ropebridge.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BridgeSlab1 extends BridgeSlab {

	public BridgeSlab1(String unlocalizedName) {
		super(unlocalizedName, 0.1F, 0.0F, 0.1F, 0.9F, slabHeight, 0.9F);
	}

	@Override
	    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	    {
	        return BridgeSlabTest.AABB_BOTTOM_HALF;
	    }
	
}
