package com.mrtrollnugnug.ropebridge.block;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class RopeLadder extends BlockLadder {
	
    protected static final AxisAlignedBB ROPE_LADDER_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.2D, 1.0D, 1.0D);
    protected static final AxisAlignedBB ROPE_LADDER_WEST_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB ROPE_LADDER_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.2D);
    protected static final AxisAlignedBB ROPE_LADDER_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.1D, 1.0D, 1.0D, 1.0D);
	
	public RopeLadder() {
		super();
        setSoundType(SoundType.WOOD);
        setCreativeTab(CreativeTabs.COMBAT);
	}

	public int getMetadata(int damage) {

	        return damage;
	    }
	
	@Override
	 public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch ((EnumFacing)state.getValue(FACING))
        {
            case NORTH:
                return ROPE_LADDER_NORTH_AABB;
            case SOUTH:
                return ROPE_LADDER_SOUTH_AABB;
            case WEST:
                return ROPE_LADDER_WEST_AABB;
            case EAST:
            default:
                return ROPE_LADDER_EAST_AABB;
        }
    }
}
