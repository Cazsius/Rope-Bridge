package com.mrtrollnugnug.ropebridge.datagen.client;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockstateProvider extends BlockStateProvider {
	public ModBlockstateProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Constants.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		makeRopeLadder(ContentHandler.oak_rope_ladder, "oak_log_top");
		makeRopeLadder(ContentHandler.birch_rope_ladder, "birch_log_top");
		makeRopeLadder(ContentHandler.jungle_rope_ladder, "jungle_log_top");
		makeRopeLadder(ContentHandler.spruce_rope_ladder, "spruce_log_top");
		makeRopeLadder(ContentHandler.acacia_rope_ladder, "acacia_log_top");
		makeRopeLadder(ContentHandler.cherry_rope_ladder, "cherry_log_top");
		makeRopeLadder(ContentHandler.dark_oak_rope_ladder, "dark_oak_log_top");
		makeRopeLadder(ContentHandler.mangrove_rope_ladder, "mangrove_log_top");
		makeRopeLadder(ContentHandler.bamboo_rope_ladder, "bamboo_block_top");
		makeRopeLadder(ContentHandler.crimson_rope_ladder, "crimson_stem_top");
		makeRopeLadder(ContentHandler.warped_rope_ladder, "warped_stem_top");

		makeRopeBridge(ContentHandler.oak_bridge, "oak_log_top");
		makeRopeBridge(ContentHandler.birch_bridge, "birch_log_top");
		makeRopeBridge(ContentHandler.jungle_bridge, "jungle_log_top");
		makeRopeBridge(ContentHandler.spruce_bridge, "spruce_log_top");
		makeRopeBridge(ContentHandler.acacia_bridge, "acacia_log_top");
		makeRopeBridge(ContentHandler.cherry_bridge, "cherry_log_top");
		makeRopeBridge(ContentHandler.dark_oak_bridge, "dark_oak_log_top");
		makeRopeBridge(ContentHandler.mangrove_bridge, "mangrove_log_top");
		makeRopeBridge(ContentHandler.bamboo_bridge, "bamboo_block_top");
		makeRopeBridge(ContentHandler.crimson_bridge, "crimson_stem_top");
		makeRopeBridge(ContentHandler.warped_bridge, "warped_stem_top");
	}

	private void makeRopeBridge(RegistryObject<Block> registryObject, String logTexture) {
		String path = registryObject.getId().getPath();
		MultiPartBlockStateBuilder builder = getMultipartBuilder(registryObject.get());

		ModelFile model0 = models().getBuilder(path + "_block_0")
			.parent(models().getExistingFile(modLoc("block/bridge_block_0")))
			.texture("log", mcLoc("block/" + logTexture));
		ModelFile model1 = models().getBuilder(path + "_block_1")
			.parent(models().getExistingFile(modLoc("block/bridge_block_1")))
			.texture("log", mcLoc("block/" + logTexture));
		ModelFile model2 = models().getBuilder(path + "_block_2")
			.parent(models().getExistingFile(modLoc("block/bridge_block_2")))
			.texture("log", mcLoc("block/" + logTexture));
		ModelFile model3 = models().getBuilder(path + "_block_3")
			.parent(models().getExistingFile(modLoc("block/bridge_block_3")))
			.texture("log", mcLoc("block/" + logTexture));

		builder.part().modelFile(model0).addModel()
			.condition(RopeBridgeBlock.PROPERTY_HEIGHT, 0)
			.condition(RopeBridgeBlock.ROTATED, false)
			.end();

		builder.part().modelFile(model1).addModel()
			.condition(RopeBridgeBlock.PROPERTY_HEIGHT, 1)
			.condition(RopeBridgeBlock.ROTATED, false)
			.end();

		builder.part().modelFile(model2).addModel()
			.condition(RopeBridgeBlock.PROPERTY_HEIGHT, 2)
			.condition(RopeBridgeBlock.ROTATED, false)
			.end();

		builder.part().modelFile(model3).addModel()
			.condition(RopeBridgeBlock.PROPERTY_HEIGHT, 3)
			.condition(RopeBridgeBlock.ROTATED, false)
			.end();

		builder.part().modelFile(model0).rotationY(90).addModel()
			.condition(RopeBridgeBlock.PROPERTY_HEIGHT, 0)
			.condition(RopeBridgeBlock.ROTATED, true)
			.end();

		builder.part().modelFile(model1).rotationY(90).addModel()
			.condition(RopeBridgeBlock.PROPERTY_HEIGHT, 1)
			.condition(RopeBridgeBlock.ROTATED, true)
			.end();

		builder.part().modelFile(model2).rotationY(90).addModel()
			.condition(RopeBridgeBlock.PROPERTY_HEIGHT, 2)
			.condition(RopeBridgeBlock.ROTATED, true)
			.end();

		builder.part().modelFile(model3).rotationY(90).addModel()
			.condition(RopeBridgeBlock.PROPERTY_HEIGHT, 3)
			.condition(RopeBridgeBlock.ROTATED, true)
			.end();
	}

	private void makeRopeLadder(RegistryObject<Block> registryObject, String plankTexture) {
		String path = registryObject.getId().getPath();
		ModelFile model = models().getBuilder(path)
			.parent(models().getExistingFile(modLoc("block/rope_ladder")))
			.texture("plank", mcLoc("block/" + plankTexture));
		getVariantBuilder(registryObject.get())
			.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
			.modelForState().modelFile(model).rotationY(180).addModel()
			.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
			.modelForState().modelFile(model).rotationY(270).addModel()
			.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
			.modelForState().modelFile(model).addModel()
			.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)
			.modelForState().modelFile(model).rotationY(90).addModel();
	}
}
