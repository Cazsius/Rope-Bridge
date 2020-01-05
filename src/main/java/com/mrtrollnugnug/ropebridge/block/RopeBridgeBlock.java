package com.mrtrollnugnug.ropebridge.block;

import com.mrtrollnugnug.ropebridge.handler.ConfigHandler;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.storage.loot.LootContext;

import java.util.ArrayList;
import java.util.List;

public class RopeBridgeBlock extends Block {

  public RopeBridgeBlock(Properties properties) {
    super(properties);
  }

  public static final IntegerProperty PROPERTY_HEIGHT = BlockStateProperties.LEVEL_0_3;
  public static final IntegerProperty PROPERTY_BACK = IntegerProperty.create("back",0,3);
  public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");

  public static final VoxelShape ZERO_AABB = Block.makeCuboidShape(0, 0, 0, 16, 4, 16);
  public static final VoxelShape ONE_AABB = Block.makeCuboidShape(0, 4, 0, 16, 8, 16);
  public static final VoxelShape TWO_AABB = Block.makeCuboidShape(0, 8, 0, 16, 12, 16);
  public static final VoxelShape THREE_AABB = Block.makeCuboidShape(0, 12, 0, 16, 16, 16);

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(PROPERTY_HEIGHT,PROPERTY_BACK,ROTATED);
  }

  private Block slab;

  public Block getSlab(){
    return slab;
  }

  public void setSlab(Block slab){
    this.slab = slab;
  }


  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    int level = state.get(PROPERTY_HEIGHT);
    switch (level){
      case 0:default:return ZERO_AABB;
      case 1:return ONE_AABB;
      case 2:return TWO_AABB;
      case 3:return THREE_AABB;
    }
  }

  @Override
  public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
    List<ItemStack> drops = new ArrayList<>();
    drops.add(new ItemStack(ContentHandler.rope, ConfigHandler.getRopePerBridge()));
    drops.add(new ItemStack(slab, ConfigHandler.getRopePerBridge()));
    return drops;
  }
}
