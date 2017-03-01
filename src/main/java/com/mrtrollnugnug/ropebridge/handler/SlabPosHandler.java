package com.mrtrollnugnug.ropebridge.handler;

import net.minecraft.util.math.BlockPos;

//TODO Wrong Package
public class SlabPosHandler {

    public int x;

    public int y;

    public int z;

    public int level;

    public boolean rotate;

    public SlabPosHandler (int xCoordinate, int yCoordinate, int zCoordinate, int slabLevel, boolean isRotated) {
        this.x = xCoordinate;
        this.y = yCoordinate;
        this.z = zCoordinate;
        this.level = slabLevel;
        this.rotate = isRotated;
    }

    public SlabPosHandler (BlockPos position, int slabLevel, boolean isRotated) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
        this.level = slabLevel;
        this.rotate = isRotated;
    }

    public BlockPos getBlockPos () {

        return new BlockPos(this.x, this.y, this.z);
    }
}
