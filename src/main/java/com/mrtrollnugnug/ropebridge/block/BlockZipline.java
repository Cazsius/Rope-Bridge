package com.mrtrollnugnug.ropebridge.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockZipline extends Block
{
	public BlockZipline()
	{
		super(Material.AIR);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState,
			IBlockAccess worldIn, BlockPos pos)
	{
		return Block.NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}
}
