package com.mrtrollnugnug.ropebridge.block;

import com.mrtrollnugnug.ropebridge.handler.ContentHandler;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RopeLadder extends BlockLadder implements ITileEntityProvider {
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    protected static final AxisAlignedBB ROPE_LADDER_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.2D, 1.0D, 1.0D);
    protected static final AxisAlignedBB ROPE_LADDER_WEST_AABB = new AxisAlignedBB(0.8D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB ROPE_LADDER_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.2D);
    protected static final AxisAlignedBB ROPE_LADDER_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8D, 1.0D, 1.0D, 1.0D);

    public RopeLadder() {
        super();
        setSoundType(SoundType.WOOD);
        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.EAST).withProperty(TYPE, EnumType.DARK_OAK));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
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
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (facing.getAxis().isHorizontal()) {
            return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        } else {
            if (worldIn.getBlockState(pos.up()).getBlock() == ContentHandler.blockRopeLadder) {
                return getDefaultState().withProperty(FACING, worldIn.getBlockState(pos.up()).getValue(FACING));
            } else
                return getDefaultState();
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    	return super.canPlaceBlockAt(worldIn, pos) || worldIn.getBlockState(pos.up()).getBlock() == ContentHandler.blockRopeLadder;
    }
    
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState facing)
    {
        if (facing.getBlock() == this) {
        	IBlockState up = worldIn.getBlockState(pos.up());
            IBlockState down = worldIn.getBlockState(pos.down());
            return canBlockStay(worldIn, pos, facing) || (!(down.getBlock() == Blocks.WATER) && up.getBlock() == ContentHandler.blockRopeLadder);
        }
        return true;
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return getExtendedState(state, worldIn, pos);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityRopeLadder te = (TileEntityRopeLadder) world.getTileEntity(pos);
        IBlockState out = state.withProperty(TYPE, te.getType());
        return out;
    }

    public enum EnumType implements IStringSerializable {
        OAK(0), BIRCH(1), SPRUCE(2), JUNGLE(3), DARK_OAK(4), ACACIA(5);
        final int meta;

        EnumType(int meta) {
            this.meta = meta;
        }

        static EnumType fromMeta(int meta) {
            for (EnumType t : values())
                if (meta == t.meta) {
                    return t;
                }
            return null;
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRopeLadder();
    }
}
