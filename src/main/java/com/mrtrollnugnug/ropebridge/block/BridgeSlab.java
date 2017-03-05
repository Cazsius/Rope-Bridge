package com.mrtrollnugnug.ropebridge.block;

import java.util.List;

import com.mrtrollnugnug.ropebridge.handler.ConfigurationHandler;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BridgeSlab extends Block
{

    public static final float slabHeight = 4.0F / 16.0F;

    public static final AxisAlignedBB AABB_BLOCK_1 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

    public static final AxisAlignedBB AABB_BLOCK_2 = new AxisAlignedBB(0.0D, 0.25D, 0.0D, 1.0D, 0.5D, 1.0D);

    public static final AxisAlignedBB AABB_BLOCK_3 = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 0.75D, 1.0D);

    public static final AxisAlignedBB AABB_BLOCK_4 = new AxisAlignedBB(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);

    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", BridgeSlab.EnumType.class);

    private final AxisAlignedBB bounds;

    public BridgeSlab(AxisAlignedBB bounds)
    {
        super(Material.WOOD);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.OAK));
        this.bounds = bounds;
    }

    @Deprecated
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Deprecated
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {

        return false;
    }

    @Deprecated
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {

        return this.bounds;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {

        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {

        final List<ItemStack> ret = new java.util.ArrayList<>();
        final int meta = this.getMetaFromState(state);
        final int slabMeta = (meta - meta % 2) / 2;
        ret.add(new ItemStack(Blocks.WOODEN_SLAB, (int) Math.floor(ConfigurationHandler.slabsPerBlock / 2), slabMeta));
        ret.add(new ItemStack(Items.STRING, (int) Math.ceil(ConfigurationHandler.stringPerBlock / 2)));
        return ret;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {

        return 20;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
    {

        return true;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
    {

        return 5;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {

        return new BlockStateContainer(this, new IProperty[] { TYPE });
    }

    @Deprecated
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        switch (meta) {
        case 0: {
            return this.getDefaultState().withProperty(TYPE, EnumType.OAK);
        }
        case 1: {
            return this.getDefaultState().withProperty(TYPE, EnumType.OAK_R);
        }
        case 2: {
            return this.getDefaultState().withProperty(TYPE, EnumType.SPRUCE);
        }
        case 3: {
            return this.getDefaultState().withProperty(TYPE, EnumType.SPRUCE_R);
        }
        case 4: {
            return this.getDefaultState().withProperty(TYPE, EnumType.BIRCH);
        }
        case 5: {
            return this.getDefaultState().withProperty(TYPE, EnumType.BIRCH_R);
        }
        case 6: {
            return this.getDefaultState().withProperty(TYPE, EnumType.JUNGLE);
        }
        case 7: {
            return this.getDefaultState().withProperty(TYPE, EnumType.JUNGLE_R);
        }
        case 8: {
            return this.getDefaultState().withProperty(TYPE, EnumType.ACACIA);
        }
        case 9: {
            return this.getDefaultState().withProperty(TYPE, EnumType.ACACIA_R);
        }
        case 10: {
            return this.getDefaultState().withProperty(TYPE, EnumType.BIG_OAK);
        }
        case 11: {
            return this.getDefaultState().withProperty(TYPE, EnumType.BIG_OAK_R);
        }
        default: {
            return this.getDefaultState().withProperty(TYPE, EnumType.OAK);
        }
        }
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {

        final EnumType type = state.getValue(TYPE);
        return type.getID();
    }

    public enum EnumType implements IStringSerializable
    {
        OAK(0, "oak"), OAK_R(1, "oak_r"), SPRUCE(2, "spruce"), SPRUCE_R(3, "spruce_r"), BIRCH(4, "birch"), BIRCH_R(5, "birch_r"), JUNGLE(6, "jungle"),
        JUNGLE_R(7, "jungle_r"), ACACIA(8, "acacia"), ACACIA_R(9, "acacia_r"), BIG_OAK(10, "big_oak"), BIG_OAK_R(11, "big_oak_r");

        private int ID;

        private String name;

        private EnumType(int ID, String name)
        {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String getName()
        {

            return this.name;
        }

        public int getID()
        {

            return this.ID;
        }

        @Override
        public String toString()
        {

            return this.getName();
        }
    }

}
