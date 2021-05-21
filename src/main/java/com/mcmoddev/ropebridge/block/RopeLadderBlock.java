package com.mcmoddev.ropebridge.block;

import com.mcmoddev.ropebridge.handler.ConfigHandler;
import com.mcmoddev.ropebridge.handler.ContentHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RopeLadderBlock extends LadderBlock {

	public RopeLadderBlock(Properties properties) {
		super(properties);
	}

	private Block slab;

	public Block getSlab() {
		return slab;
	}

	public void setSlab(Block slab) {
		this.slab = slab;
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(ContentHandler.rope, ConfigHandler.getRopePerLadder()));
		drops.add(new ItemStack(slab, ConfigHandler.getWoodPerLadder()));
		return drops;
	}
}
