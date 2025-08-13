package com.mrtrollnugnug.ropebridge.block;

import com.mrtrollnugnug.ropebridge.handler.ConfigHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RopeLadderBlock extends LadderBlock {

	private final Supplier<Block> slabSupplier;

	public RopeLadderBlock(Properties properties, Supplier<Block> slabSupplier) {
		super(properties);
		this.slabSupplier = slabSupplier;
	}

	public Block getSlab() {
		return slabSupplier.get();
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess,
	                                 BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		if (!canSurvive(state, levelReader, pos) && levelReader instanceof Level level && !level.getBlockState(pos.above()).is(this)) {
			dropResources(state, level, pos);
			level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
		}
		return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
		return true;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(ContentHandler.rope.get(), ConfigHandler.getRopePerLadder()));
		drops.add(new ItemStack(getSlab(), ConfigHandler.getWoodPerLadder()));
		return drops;
	}
}
