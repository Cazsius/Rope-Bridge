package com.mrtrollnugnug.ropebridge.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BridgeSlab4 extends BridgeSlab {

	public BridgeSlab4() {
		super(0.1F, slabHeight*3, 0.1F, 0.9F, 1.0F, 0.9F);
	}

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BridgeSlabTest.AABB_BLOCK_4;
    }
}
