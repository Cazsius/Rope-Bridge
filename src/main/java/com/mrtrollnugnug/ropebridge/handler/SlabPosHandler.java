package com.mrtrollnugnug.ropebridge.handler;

import net.minecraft.util.math.BlockPos;
//TODO Wrong Package
public class SlabPosHandler {
	public int x;
	public int y;
	public int z;
	public int level;
	public boolean rotate;
	
	public SlabPosHandler(int xCoordinate, int yCoordinate, int zCoordinate, int slabLevel, boolean isRotated) {
		x = xCoordinate;
		y = yCoordinate;
		z = zCoordinate;
		level = slabLevel;
		rotate = isRotated;
	}
	public SlabPosHandler(BlockPos position, int slabLevel, boolean isRotated) {
		x = position.getX();
		y = position.getY();
		z = position.getZ();
		level = slabLevel;
		rotate = isRotated;
	}
}
