package com.mrtrollnugnug.ropebridge.block;

import java.util.List;

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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BridgeSlab extends Block
{
    protected static float slabHeight = 4.0F / 16.0F;
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", BridgeSlab.EnumType.class);

    public BridgeSlab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
    {
        super(Material.WOOD);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.OAK));
        new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BridgeSlabTest.AABB_BLOCK_2;
    }

    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return true;
    }

    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }

    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return false;
    }

    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        int meta = getMetaFromState(state);
        int slabMeta = (meta - meta % 2) / 2;
        ret.add(new ItemStack(Blocks.WOODEN_SLAB, 1, slabMeta));
        ret.add(new ItemStack(Items.STRING, RANDOM.nextInt(2)));
        return ret;
    }

    public ItemStack getPickBlock(RayTraceResult target, World world, BlockPos pos)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean isFullCube()
    {
        return false;
    }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return 20;
    }

    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return true;
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return 5;
    }

    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }

    public int getMobilityFlag()
    {
        return 1;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { TYPE });
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        switch (meta) {
        case 0: {
            return getDefaultState().withProperty(TYPE, EnumType.OAK);
        }
        case 1: {
            return getDefaultState().withProperty(TYPE, EnumType.OAK_R);
        }
        case 2: {
            return getDefaultState().withProperty(TYPE, EnumType.SPRUCE);
        }
        case 3: {
            return getDefaultState().withProperty(TYPE, EnumType.SPRUCE_R);
        }
        case 4: {
            return getDefaultState().withProperty(TYPE, EnumType.BIRCH);
        }
        case 5: {
            return getDefaultState().withProperty(TYPE, EnumType.BIRCH_R);
        }
        case 6: {
            return getDefaultState().withProperty(TYPE, EnumType.JUNGLE);
        }
        case 7: {
            return getDefaultState().withProperty(TYPE, EnumType.JUNGLE_R);
        }
        case 8: {
            return getDefaultState().withProperty(TYPE, EnumType.ACACIA);
        }
        case 9: {
            return getDefaultState().withProperty(TYPE, EnumType.ACACIA_R);
        }
        case 10: {
            return getDefaultState().withProperty(TYPE, EnumType.BIG_OAK);
        }
        case 11: {
            return getDefaultState().withProperty(TYPE, EnumType.BIG_OAK_R);
        }
        default: {
            return getDefaultState().withProperty(TYPE, EnumType.OAK);
        }
        }
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumType type = (EnumType) state.getValue(TYPE);
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
            return name;
        }

        public int getID()
        {
            return ID;
        }

        @Override
        public String toString()
        {
            return getName();
        }
    }

}
