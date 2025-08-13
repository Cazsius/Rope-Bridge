package com.mrtrollnugnug.ropebridge.block;

import com.mrtrollnugnug.ropebridge.handler.ConfigHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel,
	                              BlockPos pCurrentPos, BlockPos pFacingPos) {
		if (!canSurvive(pState, pLevel, pCurrentPos) && pLevel instanceof Level level && !pLevel.getBlockState(pCurrentPos.above()).is(this)) {
			dropResources(pState, level, pCurrentPos);
			pLevel.setBlock(pCurrentPos, Blocks.AIR.defaultBlockState(), 3);
		}
		return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
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
