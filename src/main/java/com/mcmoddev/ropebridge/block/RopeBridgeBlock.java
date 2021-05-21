package com.mcmoddev.ropebridge.block;

import com.mcmoddev.ropebridge.handler.ConfigHandler;
import com.mcmoddev.ropebridge.handler.ContentHandler;
import com.mcmoddev.ropebridge.lib.Constants;
import com.mcmoddev.ropebridge.lib.Constants.Messages;
import com.mcmoddev.ropebridge.lib.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RopeBridgeBlock extends Block {

	public RopeBridgeBlock(Properties properties) {
		super(properties);
	}

	public static final IntegerProperty PROPERTY_HEIGHT = BlockStateProperties.LEVEL_0_3;
	public static final IntegerProperty PROPERTY_BACK = IntegerProperty.create("back", 0, 3);
	public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");

	public static final VoxelShape ZERO_AABB = Block.makeCuboidShape(0, 0, 0, 16, 4, 16);
	public static final VoxelShape ONE_AABB = Block.makeCuboidShape(0, 4, 0, 16, 8, 16);
	public static final VoxelShape TWO_AABB = Block.makeCuboidShape(0, 8, 0, 16, 12, 16);
	public static final VoxelShape THREE_AABB = Block.makeCuboidShape(0, 12, 0, 16, 16, 16);

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PROPERTY_HEIGHT, PROPERTY_BACK, ROTATED);
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
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int level = state.get(PROPERTY_HEIGHT);
		switch (level) {
			case 0:
			default:
				return ZERO_AABB;
			case 1:
				return ONE_AABB;
			case 2:
				return TWO_AABB;
			case 3:
				return THREE_AABB;
		}
	}

	@Override
	public void onBlockHarvested(World world, final BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBlockHarvested(world, pos, state, player);
		if (!world.isRemote) {
			if (player.getHeldItemMainhand().getItem() == ContentHandler.bridge_builder && player.isCrouching()) {
				ModUtils.tellPlayer(player, Messages.WARNING_BREAKING);
				boolean rotate = world.getBlockState(pos).get(RopeBridgeBlock.ROTATED);
				if (rotate) {
					breakNorth(pos, (ServerWorld) world);
					breakSouth(pos, (ServerWorld) world);
				} else {
					breakEast(pos, (ServerWorld) world);
					breakWest(pos, (ServerWorld) world);
				}
			}
		}
	}

	public void breakSouth(BlockPos posToBreak, ServerWorld world) {
		BlockPos south = posToBreak.south();
		BlockPos up = south.up();
		BlockPos down = south.down();
		BlockState stateDown = world.getBlockState(down);
		BlockState stateUp = world.getBlockState(up);
		BlockState state = world.getBlockState(south);
		if (state.getBlock() == this) {
			world.destroyBlock(south, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakSouth(south, world);
				}
			}, 100);
		}
		if (stateUp.getBlock() == this) {
			world.destroyBlock(up, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakSouth(up, world);
				}
			}, 100);
		}
		if (stateDown.getBlock() == this) {
			world.destroyBlock(down, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakSouth(down, world);
				}
			}, 100);
		}
	}

	public void breakNorth(BlockPos posToBreak, ServerWorld world) {
		BlockPos north = posToBreak.north();
		BlockPos up = north.up();
		BlockPos down = north.down();
		BlockState stateDown = world.getBlockState(down);
		BlockState stateUp = world.getBlockState(up);
		BlockState state = world.getBlockState(north);
		if (state.getBlock() == this) {
			world.destroyBlock(north, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakNorth(north, world);
				}
			}, 100);
		}
		if (stateUp.getBlock() == this) {
			world.destroyBlock(up, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakNorth(up, world);
				}
			}, 100);
		}
		if (stateDown.getBlock() == this) {
			world.destroyBlock(down, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakNorth(down, world);
				}
			}, 100);
		}
	}

	public void breakEast(BlockPos posToBreak, ServerWorld world) {
		BlockPos east = posToBreak.east();
		BlockPos up = east.up();
		BlockPos down = east.down();
		BlockState stateDown = world.getBlockState(down);
		BlockState stateUp = world.getBlockState(up);
		BlockState state = world.getBlockState(east);
		if (state.getBlock() == this) {
			world.destroyBlock(east, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakEast(east, world);
				}
			}, 100);
		}
		if (stateUp.getBlock() == this) {
			world.destroyBlock(up, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakEast(up, world);
				}
			}, 100);
		}
		if (stateDown.getBlock() == this) {
			world.destroyBlock(down, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakEast(down, world);
				}
			}, 100);
		}
	}

	public void breakWest(BlockPos posToBreak, ServerWorld world) {
		BlockPos west = posToBreak.west();
		BlockPos up = west.up();
		BlockPos down = west.down();
		BlockState stateDown = world.getBlockState(down);
		BlockState stateUp = world.getBlockState(up);
		BlockState state = world.getBlockState(west);
		if (state.getBlock() == this) {
			world.destroyBlock(west, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakWest(west, world);
				}
			}, 100);
		}
		if (stateUp.getBlock() == this) {
			world.destroyBlock(up, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakWest(up, world);
				}
			}, 100);
		}
		if (stateDown.getBlock() == this) {
			world.destroyBlock(down, true);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					breakWest(down, world);
				}
			}, 100);
		}
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(ContentHandler.rope, ConfigHandler.getRopePerBridge()));
		drops.add(new ItemStack(slab, ConfigHandler.getSlabsPerBridge()));
		return drops;
	}
}
