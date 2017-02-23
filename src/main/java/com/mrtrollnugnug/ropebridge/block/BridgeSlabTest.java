package com.mrtrollnugnug.ropebridge.block;

import java.util.List;

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

public class BridgeSlabTest extends BasicBlock
{
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static float slabHeight = 4.0F / 16.0F;
    public BridgeSlabTest(String unlocalizedName, float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
    {
        super(unlocalizedName, Material.WOOD, 1.0F, 5.0F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SIDE, Side.BOTTOM).withProperty(WOOD_TYPE, WoodType.OAK));
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
        return state.getValue(SIDE) == Side.BOTTOM ? AABB_BOTTOM_HALF : AABB_TOP_HALF;
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

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { SIDE, WOOD_TYPE });
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int side = meta >> 3;
        int type = meta & 0x7;
        return getDefaultState().withProperty(SIDE, side == 0 ? Side.TOP : Side.BOTTOM).withProperty(WOOD_TYPE, WoodType.get(type));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(SIDE).i << 3 + state.getValue(WOOD_TYPE).i;
    }

    public static final IProperty<Side> SIDE = PropertyEnum.create("side", Side.class);
    public static final IProperty<WoodType> WOOD_TYPE = PropertyEnum.create("type", WoodType.class);

    public enum Side implements IStringSerializable
    {
        TOP(0), BOTTOM(1);
        public final int i;

        private Side(int i)
        {
            this.i = i;
        }

        @Override
        public String getName()
        {
            return name().toLowerCase();
        }
    }

    public enum WoodType implements IStringSerializable
    {
        OAK(0, "oak"), BIRCH(1, "birch"), SPRUCE(2, "spruce"), JUNGLE(3, "jungle"), DARK_OAK(4, "dark_oak"), ACACIA(5, "acacia");
        public final int i;
        public final String name;

        WoodType(int i, String name)
        {
            this.i = i;
            this.name = name;
        }

        public static WoodType get(int type)
        {
            for (WoodType t : values())
                if (t.i == type)
                    return t;
            return OAK;
        }

        @Override
        public String getName()
        {
            return name;
        }

    }
}
