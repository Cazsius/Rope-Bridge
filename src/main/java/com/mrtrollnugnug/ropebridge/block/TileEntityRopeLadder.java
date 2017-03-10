package com.mrtrollnugnug.ropebridge.block;

import com.mrtrollnugnug.ropebridge.block.RopeLadder.EnumType;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRopeLadder extends TileEntity
{
    private EnumType type;

    public TileEntityRopeLadder(EnumType type)
    {
        this.type = type;
    }

    public TileEntityRopeLadder()
    {
        this(EnumType.OAK);
    }

    public EnumType getType()
    {
        return type;
    }

    public void setType(EnumType type)
    {
        this.type = type;
    }

    private static final String typeKey = "type";

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger(typeKey, type.meta);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        type = EnumType.fromMeta(compound.getInteger(typeKey));
    }
}
