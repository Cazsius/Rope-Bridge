package com.mrtrollnugnug.ropebridge.block;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RopeLadder extends BlockLadder implements ITileEntityProvider
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    protected static final AxisAlignedBB ROPE_LADDER_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.2D, 1.0D, 1.0D);
    protected static final AxisAlignedBB ROPE_LADDER_WEST_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB ROPE_LADDER_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.2D);
    protected static final AxisAlignedBB ROPE_LADDER_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.1D, 1.0D, 1.0D, 1.0D);

    public RopeLadder()
    {
        super();
        setSoundType(SoundType.WOOD);
        setCreativeTab(CreativeTabs.COMBAT);
        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, EnumType.OAK));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch ((EnumFacing) state.getValue(FACING)) {
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

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntityRopeLadder te = (TileEntityRopeLadder) world.getTileEntity(pos);
        return state.withProperty(TYPE, te.getType());
    }

    public enum EnumType implements IStringSerializable
    {
        OAK(0), BIRCH(1), SPRUCE(2), JUNGLE(3), DARK_OAK(4), ACACIA(6);
        final int meta;

        EnumType(int meta)
        {
            this.meta = meta;
        }

        static EnumType fromMeta(int meta)
        {
            for (EnumType t : values())
                if (meta == t.meta)
                    return t;
            return null;
        }

        @Override
        public String getName()
        {
            return name().toLowerCase();
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityRopeLadder();
    }
}
