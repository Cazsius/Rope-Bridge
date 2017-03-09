package com.mrtrollnugnug.ropebridge.block;

import com.mrtrollnugnug.ropebridge.block.RopeLadder.EnumType;

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
}
