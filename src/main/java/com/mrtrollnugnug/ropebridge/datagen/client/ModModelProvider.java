package com.mrtrollnugnug.ropebridge.datagen.client;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.handler.ContentHandler;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

public class ModModelProvider extends ModelProvider {
	public static final TextureSlot LOG = TextureSlot.create("log");
	public static final TextureSlot PLANK = TextureSlot.create("plank");

	public static final ModelTemplate BRIDGE_0 = ModelTemplates.create("ropebridge:bridge_block_0", LOG);
	public static final ModelTemplate BRIDGE_1 = ModelTemplates.create("ropebridge:bridge_block_1", LOG);
	public static final ModelTemplate BRIDGE_2 = ModelTemplates.create("ropebridge:bridge_block_2", LOG);
	public static final ModelTemplate BRIDGE_3 = ModelTemplates.create("ropebridge:bridge_block_3", LOG);
	public static final ModelTemplate ROPE_LADDER = ModelTemplates.create("ropebridge:rope_ladder", PLANK);

	public ModModelProvider(PackOutput output) {
		super(output, Constants.MOD_ID);
	}

	@Override
	protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
		useExistingItem(itemModels, ContentHandler.bridge_builder);
		useExistingItem(itemModels, ContentHandler.ladder_builder);
		useExistingItem(itemModels, ContentHandler.rope);
		useExistingItem(itemModels, ContentHandler.bridge_builder_hook);
		useExistingItem(itemModels, ContentHandler.bridge_builder_barrel);
		useExistingItem(itemModels, ContentHandler.bridge_builder_handle);
		useExistingItem(itemModels, ContentHandler.ladder_hook);

		makeRopeLadder(blockModels, ContentHandler.oak_rope_ladder, "oak_log_top");
		makeRopeLadder(blockModels, ContentHandler.birch_rope_ladder, "birch_log_top");
		makeRopeLadder(blockModels, ContentHandler.jungle_rope_ladder, "jungle_log_top");
		makeRopeLadder(blockModels, ContentHandler.spruce_rope_ladder, "spruce_log_top");
		makeRopeLadder(blockModels, ContentHandler.acacia_rope_ladder, "acacia_log_top");
		makeRopeLadder(blockModels, ContentHandler.cherry_rope_ladder, "cherry_log_top");
		makeRopeLadder(blockModels, ContentHandler.dark_oak_rope_ladder, "dark_oak_log_top");
		makeRopeLadder(blockModels, ContentHandler.mangrove_rope_ladder, "mangrove_log_top");
		makeRopeLadder(blockModels, ContentHandler.bamboo_rope_ladder, "bamboo_block_top");
		makeRopeLadder(blockModels, ContentHandler.crimson_rope_ladder, "crimson_stem_top");
		makeRopeLadder(blockModels, ContentHandler.warped_rope_ladder, "warped_stem_top");

		makeRopeBridge(blockModels, ContentHandler.oak_bridge, "oak_log_top");
		makeRopeBridge(blockModels, ContentHandler.birch_bridge, "birch_log_top");
		makeRopeBridge(blockModels, ContentHandler.jungle_bridge, "jungle_log_top");
		makeRopeBridge(blockModels, ContentHandler.spruce_bridge, "spruce_log_top");
		makeRopeBridge(blockModels, ContentHandler.acacia_bridge, "acacia_log_top");
		makeRopeBridge(blockModels, ContentHandler.cherry_bridge, "cherry_log_top");
		makeRopeBridge(blockModels, ContentHandler.dark_oak_bridge, "dark_oak_log_top");
		makeRopeBridge(blockModels, ContentHandler.mangrove_bridge, "mangrove_log_top");
		makeRopeBridge(blockModels, ContentHandler.bamboo_bridge, "bamboo_block_top");
		makeRopeBridge(blockModels, ContentHandler.crimson_bridge, "crimson_stem_top");
		makeRopeBridge(blockModels, ContentHandler.warped_bridge, "warped_stem_top");
	}

	private void useExistingItem(ItemModelGenerators itemModels, DeferredItem<? extends Item> deferredItem) {
		itemModels.itemModelOutput.accept(
			deferredItem.get(),
			ItemModelUtils.plainModel(Constants.modLoc("item/" + deferredItem.getId().getPath()))
		);
	}

	private void makeRopeBridge(BlockModelGenerators blockModels, DeferredBlock<? extends Block> registryObject, String logTexture) {
		ResourceLocation logTextureLocation = ResourceLocation.withDefaultNamespace("block/" + logTexture);
		ResourceLocation model0 = BRIDGE_0.createWithSuffix(registryObject.get(), "_block_0", log(logTextureLocation), blockModels.modelOutput);
		ResourceLocation model1 = BRIDGE_1.createWithSuffix(registryObject.get(), "_block_1", log(logTextureLocation), blockModels.modelOutput);
		ResourceLocation model2 = BRIDGE_2.createWithSuffix(registryObject.get(), "_block_2", log(logTextureLocation), blockModels.modelOutput);
		ResourceLocation model3 = BRIDGE_3.createWithSuffix(registryObject.get(), "_block_3", log(logTextureLocation), blockModels.modelOutput);

		blockModels.blockStateOutput.accept(
			MultiPartGenerator.multiPart(registryObject.get())
				.with(
					BlockModelGenerators.condition()
						.term(RopeBridgeBlock.PROPERTY_HEIGHT, 0)
						.term(RopeBridgeBlock.ROTATED, false),
					BlockModelGenerators.plainVariant(model0)
				).with(
					BlockModelGenerators.condition()
						.term(RopeBridgeBlock.PROPERTY_HEIGHT, 1)
						.term(RopeBridgeBlock.ROTATED, false),
					BlockModelGenerators.plainVariant(model1)
				).with(
					BlockModelGenerators.condition()
						.term(RopeBridgeBlock.PROPERTY_HEIGHT, 2)
						.term(RopeBridgeBlock.ROTATED, false),
					BlockModelGenerators.plainVariant(model2)
				).with(
					BlockModelGenerators.condition()
						.term(RopeBridgeBlock.PROPERTY_HEIGHT, 3)
						.term(RopeBridgeBlock.ROTATED, false),
					BlockModelGenerators.plainVariant(model3)
				).with(
					BlockModelGenerators.condition()
						.term(RopeBridgeBlock.PROPERTY_HEIGHT, 0)
						.term(RopeBridgeBlock.ROTATED, true),
					BlockModelGenerators.plainVariant(model0).with(BlockModelGenerators.Y_ROT_90)
				).with(
					BlockModelGenerators.condition()
						.term(RopeBridgeBlock.PROPERTY_HEIGHT, 1)
						.term(RopeBridgeBlock.ROTATED, true),
					BlockModelGenerators.plainVariant(model1).with(BlockModelGenerators.Y_ROT_90)
				).with(
					BlockModelGenerators.condition()
						.term(RopeBridgeBlock.PROPERTY_HEIGHT, 2)
						.term(RopeBridgeBlock.ROTATED, true),
					BlockModelGenerators.plainVariant(model2).with(BlockModelGenerators.Y_ROT_90)
				).with(
					BlockModelGenerators.condition()
						.term(RopeBridgeBlock.PROPERTY_HEIGHT, 3)
						.term(RopeBridgeBlock.ROTATED, true),
					BlockModelGenerators.plainVariant(model3).with(BlockModelGenerators.Y_ROT_90)
				)
		);

		blockModels.registerSimpleItemModel(registryObject.asItem(), model0);
	}

	private TextureMapping log(ResourceLocation texture) {
		return new TextureMapping().put(LOG, texture);
	}

	private void makeRopeLadder(BlockModelGenerators blockModels, DeferredBlock<? extends Block> registryObject, String plankTexture) {
		ResourceLocation plankTextureLocation = ResourceLocation.withDefaultNamespace("block/" + plankTexture);
		ResourceLocation model = ROPE_LADDER.create(registryObject.get(), plank(plankTextureLocation), blockModels.modelOutput);

		blockModels.blockStateOutput
			.accept(
				MultiVariantGenerator.dispatch(registryObject.get(), BlockModelGenerators.plainVariant(model)

				).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT)
			);
		blockModels.registerSimpleItemModel(registryObject.asItem(), model);
	}

	private TextureMapping plank(ResourceLocation texture) {
		return new TextureMapping().put(PLANK, texture);
	}
}
