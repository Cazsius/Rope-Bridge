package com.mrtrollnugnug.ropebridge.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BridgeSlab2 extends BridgeSlab {

	public BridgeSlab2() {
		super(0.1F, slabHeight, 0.1F, 0.9F, slabHeight*2, 0.9F);
	}

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BridgeSlabTest.AABB_BLOCK_2;
    }
	
}
