package com.mrtrollnugnug.ropebridge.handler;

import net.minecraft.util.math.BlockPos;

public class SlabPosHandler {

    private final int x;
    private final  int y;
    private final  int z;
    private final int level;
    private final boolean rotate;

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

	public boolean getRotate() {
		return rotate;
	}

	public int getLevel() {
		return level;
	}
}
