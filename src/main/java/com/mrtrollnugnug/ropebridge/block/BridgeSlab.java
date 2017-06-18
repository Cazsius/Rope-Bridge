package com.mrtrollnugnug.ropebridge.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mrtrollnugnug.ropebridge.handler.ConfigurationHandler;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BridgeSlab extends Block {

    public static final AxisAlignedBB AABB_BLOCK_1 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    public static final AxisAlignedBB AABB_BLOCK_2 = new AxisAlignedBB(0.0D, 0.25D, 0.0D, 1.0D, 0.5D, 1.0D);
    public static final AxisAlignedBB AABB_BLOCK_3 = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 0.75D, 1.0D);
    public static final AxisAlignedBB AABB_BLOCK_4 = new AxisAlignedBB(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", BridgeSlab.EnumType.class);
    public static final PropertyBool ROTATED = PropertyBool.create("rotated");
    public static final PropertyInteger FRONT = PropertyInteger.create("front", 0, 3);
    public static final PropertyInteger BACK = PropertyInteger.create("back", 0, 3);
    private final AxisAlignedBB bounds;
    private final int boundIndex;

    public BridgeSlab(AxisAlignedBB bounds, int boundIndex) {
        super(Material.WOOD);
        this.boundIndex = boundIndex;
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.OAK).withProperty(ROTATED, false));
        this.bounds = bounds;
    }

    @Deprecated
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Deprecated
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Deprecated
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.bounds;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> ret = new ArrayList<>();
        int slabMeta = state.getValue(TYPE).ordinal();
        ret.add(new ItemStack(Blocks.WOODEN_SLAB, (int) Math.floor(ConfigurationHandler.getSlabsPerBlock() / 2), slabMeta));
        ret.add(new ItemStack(Items.STRING, (int) Math.ceil(ConfigurationHandler.getStringPerBlock() / 2)));
        return ret;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 20;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 5;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE, ROTATED, FRONT, BACK);
    }

    @Deprecated
    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean rotated = (meta & 1) == 1;
        int typeValue = meta >> 1;
        EnumType type = EnumType.values()[typeValue % EnumType.values().length];
        return getDefaultState().withProperty(TYPE, type).withProperty(ROTATED, rotated);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90)
            return state.cycleProperty(ROTATED);
        return state;
    }

    @SuppressWarnings("deprecation")
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        int frontAmount = 0, backAmount = 0;
        boolean rotated = state.getValue(ROTATED);
        EnumFacing front = rotated ? EnumFacing.NORTH : EnumFacing.WEST;
        EnumFacing back = rotated ? EnumFacing.SOUTH : EnumFacing.EAST;

        IBlockState frontState = worldIn.getBlockState(pos.offset(front));
        if (frontState.getBlock() instanceof BridgeSlab) {
            frontAmount = ((BridgeSlab) frontState.getBlock()).boundIndex - boundIndex;
        } else {
            frontState = worldIn.getBlockState(pos.offset(front).offset(EnumFacing.UP));
            if (frontState.getBlock() instanceof BridgeSlab) {
                frontAmount = ((BridgeSlab) frontState.getBlock()).boundIndex + 4 - boundIndex;
            }
        }

        IBlockState backState = worldIn.getBlockState(pos.offset(back));
        if (backState.getBlock() instanceof BridgeSlab) {
            backAmount = ((BridgeSlab) backState.getBlock()).boundIndex - boundIndex;
    	} else {
            backState = worldIn.getBlockState(pos.offset(back).offset(EnumFacing.UP));
            if (backState.getBlock() instanceof BridgeSlab) {
                backAmount = ((BridgeSlab) backState.getBlock()).boundIndex + 4 - boundIndex;
            }
        }

        if (frontAmount < 0 || frontAmount > 3) {
        	frontAmount = 0;
        }
        if (backAmount < 0 || backAmount > 3) { 
        	backAmount = 0;
        }
        return super.getActualState(state, worldIn, pos).withProperty(FRONT, frontAmount).withProperty(BACK, backAmount);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(TYPE).ordinal() << 1) | (state.getValue(ROTATED) ? 1 : 0);
    }

    public enum EnumType implements IStringSerializable {
        OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, BIG_OAK;

        @Override
        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        @Override
        public String toString() {
            return this.getName();
        }
    }

}
