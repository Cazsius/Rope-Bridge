package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.block.RopeLadderBlock;
import com.mrtrollnugnug.ropebridge.datamap.SlabMap;
import com.mrtrollnugnug.ropebridge.item.ItemBridgeBuilder;
import com.mrtrollnugnug.ropebridge.item.ItemLadderBuilder;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;
import java.util.function.Function;

public final class ContentHandler {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

	// Blocks
	public static final DeferredBlock<RopeLadderBlock> oak_rope_ladder = registerBlockWithItem("oak_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.OAK_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> birch_rope_ladder = registerBlockWithItem("birch_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.BIRCH_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> jungle_rope_ladder = registerBlockWithItem("jungle_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.JUNGLE_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> spruce_rope_ladder = registerBlockWithItem("spruce_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.SPRUCE_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> acacia_rope_ladder = registerBlockWithItem("acacia_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.ACACIA_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> cherry_rope_ladder = registerBlockWithItem("cherry_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.CHERRY_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> dark_oak_rope_ladder = registerBlockWithItem("dark_oak_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.DARK_OAK_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> mangrove_rope_ladder = registerBlockWithItem("mangrove_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.MANGROVE_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> pale_oak_rope_ladder = registerBlockWithItem("pale_oak_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.PALE_OAK_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> bamboo_rope_ladder = registerBlockWithItem("bamboo_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.BAMBOO_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> crimson_rope_ladder = registerBlockWithItem("crimson_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.CRIMSON_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));
	public static final DeferredBlock<RopeLadderBlock> warped_rope_ladder = registerBlockWithItem("warped_rope_ladder", (properties) -> new RopeLadderBlock(properties, () -> Blocks.WARPED_SLAB), Block.Properties.ofFullCopy(Blocks.LADDER));

	public static final DeferredBlock<RopeBridgeBlock> oak_bridge = registerBlockWithItem("oak_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.OAK_SLAB), Block.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> birch_bridge = registerBlockWithItem("birch_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.BIRCH_SLAB), Block.Properties.ofFullCopy(Blocks.BIRCH_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> jungle_bridge = registerBlockWithItem("jungle_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.JUNGLE_SLAB), Block.Properties.ofFullCopy(Blocks.JUNGLE_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> spruce_bridge = registerBlockWithItem("spruce_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.SPRUCE_SLAB), Block.Properties.ofFullCopy(Blocks.SPRUCE_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> acacia_bridge = registerBlockWithItem("acacia_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.ACACIA_SLAB), Block.Properties.ofFullCopy(Blocks.ACACIA_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> cherry_bridge = registerBlockWithItem("cherry_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.CHERRY_SLAB), Block.Properties.ofFullCopy(Blocks.CHERRY_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> dark_oak_bridge = registerBlockWithItem("dark_oak_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.DARK_OAK_SLAB), Block.Properties.ofFullCopy(Blocks.DARK_OAK_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> mangrove_bridge = registerBlockWithItem("mangrove_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.MANGROVE_SLAB), Block.Properties.ofFullCopy(Blocks.MANGROVE_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> pale_oak_bridge = registerBlockWithItem("pale_oak_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.PALE_OAK_SLAB), Block.Properties.ofFullCopy(Blocks.PALE_OAK_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> bamboo_bridge = registerBlockWithItem("bamboo_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.BAMBOO_SLAB), Block.Properties.ofFullCopy(Blocks.BAMBOO_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> crimson_bridge = registerBlockWithItem("crimson_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.CRIMSON_SLAB), Block.Properties.ofFullCopy(Blocks.CRIMSON_SLAB).noOcclusion());
	public static final DeferredBlock<RopeBridgeBlock> warped_bridge = registerBlockWithItem("warped_bridge", (properties) -> new RopeBridgeBlock(properties, () -> Blocks.WARPED_SLAB), Block.Properties.ofFullCopy(Blocks.WARPED_SLAB).noOcclusion());

	// Items
	public static final DeferredItem<ItemBridgeBuilder> bridge_builder = ITEMS.registerItem("bridge_builder", ItemBridgeBuilder::new);
	public static final DeferredItem<ItemLadderBuilder> ladder_builder = ITEMS.registerItem("ladder_builder", ItemLadderBuilder::new);
	public static final DeferredItem<Item> rope = ITEMS.registerSimpleItem("rope");
	public static final DeferredItem<Item> bridge_builder_hook = ITEMS.registerSimpleItem("bridge_builder_hook");
	public static final DeferredItem<Item> bridge_builder_barrel = ITEMS.registerSimpleItem("bridge_builder_barrel");
	public static final DeferredItem<ItemBridgeBuilder> bridge_builder_handle = ITEMS.registerItem("bridge_builder_handle", ItemBridgeBuilder::new);
	public static final DeferredItem<Item> ladder_hook = ITEMS.registerSimpleItem("ladder_hook");

	public static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Function<Properties, ? extends T> func, BlockBehaviour.Properties properties) {
		DeferredBlock<T> reg = BLOCKS.registerBlock(name, func, properties);
		ITEMS.registerSimpleBlockItem(reg);
		return reg;
	}

	// Sounds
	public static final DeferredHolder<SoundEvent, SoundEvent> load = SOUND_EVENTS.register("load", () ->
		SoundEvent.createVariableRangeEvent(Constants.modLoc("load")));
	public static final DeferredHolder<SoundEvent, SoundEvent> swoosh = SOUND_EVENTS.register("swoosh", () ->
		SoundEvent.createVariableRangeEvent(Constants.modLoc("swoosh")));

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
		.icon(() -> new ItemStack(ContentHandler.bridge_builder.get()))
		.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
		.title(Component.translatable("itemGroup.ropebridge.tab"))
		.displayItems((displayParameters, output) -> {
			List<ItemStack> stacks = ContentHandler.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
			output.acceptAll(stacks);
		}).build());


	public static final DataMapType<Block, SlabMap> SLAB_MAP = DataMapType.builder(
		Constants.modLoc("slab_map"), Registries.BLOCK, SlabMap.CODEC)
		.synced(SlabMap.CODEC, false).build();

	public static void register(final RegisterDataMapTypesEvent event) {
		event.register(SLAB_MAP);
	}
}
