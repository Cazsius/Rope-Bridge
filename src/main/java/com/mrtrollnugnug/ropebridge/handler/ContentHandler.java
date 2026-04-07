package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.block.RopeLadderBlock;
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
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public final class ContentHandler {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

	// Blocks
	public static final DeferredBlock<RopeLadderBlock> oak_rope_ladder = registerBlockWithItem("oak_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.OAK_SLAB));
	public static final DeferredBlock<RopeLadderBlock> birch_rope_ladder = registerBlockWithItem("birch_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.BIRCH_SLAB));
	public static final DeferredBlock<RopeLadderBlock> jungle_rope_ladder = registerBlockWithItem("jungle_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.JUNGLE_SLAB));
	public static final DeferredBlock<RopeLadderBlock> spruce_rope_ladder = registerBlockWithItem("spruce_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.SPRUCE_SLAB));
	public static final DeferredBlock<RopeLadderBlock> acacia_rope_ladder = registerBlockWithItem("acacia_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.ACACIA_SLAB));
	public static final DeferredBlock<RopeLadderBlock> cherry_rope_ladder = registerBlockWithItem("cherry_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.CHERRY_SLAB));
	public static final DeferredBlock<RopeLadderBlock> dark_oak_rope_ladder = registerBlockWithItem("dark_oak_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.DARK_OAK_SLAB));
	public static final DeferredBlock<RopeLadderBlock> mangrove_rope_ladder = registerBlockWithItem("mangrove_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.MANGROVE_SLAB));
	public static final DeferredBlock<RopeLadderBlock> bamboo_rope_ladder = registerBlockWithItem("bamboo_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.BAMBOO_SLAB));
	public static final DeferredBlock<RopeLadderBlock> crimson_rope_ladder = registerBlockWithItem("crimson_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.CRIMSON_SLAB));
	public static final DeferredBlock<RopeLadderBlock> warped_rope_ladder = registerBlockWithItem("warped_rope_ladder", () -> new RopeLadderBlock(Block.Properties.ofFullCopy(Blocks.LADDER), () -> Blocks.WARPED_SLAB));

	public static final DeferredBlock<RopeBridgeBlock> oak_bridge = registerBlockWithItem("oak_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion(), () -> Blocks.OAK_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> birch_bridge = registerBlockWithItem("birch_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.BIRCH_SLAB).noOcclusion(), () -> Blocks.BIRCH_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> jungle_bridge = registerBlockWithItem("jungle_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.JUNGLE_SLAB).noOcclusion(), () -> Blocks.JUNGLE_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> spruce_bridge = registerBlockWithItem("spruce_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.SPRUCE_SLAB).noOcclusion(), () -> Blocks.SPRUCE_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> acacia_bridge = registerBlockWithItem("acacia_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.ACACIA_SLAB).noOcclusion(), () -> Blocks.ACACIA_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> cherry_bridge = registerBlockWithItem("cherry_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.CHERRY_SLAB).noOcclusion(), () -> Blocks.CHERRY_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> dark_oak_bridge = registerBlockWithItem("dark_oak_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.DARK_OAK_SLAB).noOcclusion(), () -> Blocks.DARK_OAK_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> mangrove_bridge = registerBlockWithItem("mangrove_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.MANGROVE_SLAB).noOcclusion(), () -> Blocks.MANGROVE_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> bamboo_bridge = registerBlockWithItem("bamboo_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_SLAB).noOcclusion(), () -> Blocks.BAMBOO_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> crimson_bridge = registerBlockWithItem("crimson_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.CRIMSON_SLAB).noOcclusion(), () -> Blocks.CRIMSON_SLAB));
	public static final DeferredBlock<RopeBridgeBlock> warped_bridge = registerBlockWithItem("warped_bridge", () -> new RopeBridgeBlock(Block.Properties.ofFullCopy(Blocks.WARPED_SLAB).noOcclusion(), () -> Blocks.WARPED_SLAB));

	// Items
	public static final DeferredItem<ItemBridgeBuilder> bridge_builder = ITEMS.register("bridge_builder", () -> new ItemBridgeBuilder(new Item.Properties()));
	public static final DeferredItem<ItemLadderBuilder> ladder_builder = ITEMS.register("ladder_builder", () -> new ItemLadderBuilder(new Item.Properties()));
	public static final DeferredItem<Item> rope = ITEMS.registerSimpleItem("rope");
	public static final DeferredItem<Item> bridge_builder_hook = ITEMS.registerSimpleItem("bridge_builder_hook");
	public static final DeferredItem<Item> bridge_builder_barrel = ITEMS.registerSimpleItem("bridge_builder_barrel");
	public static final DeferredItem<Item> bridge_builder_handle = ITEMS.registerSimpleItem("bridge_builder_handle");
	public static final DeferredItem<Item> ladder_hook = ITEMS.registerSimpleItem("ladder_hook");

	public static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> block) {
		DeferredBlock<T> reg = BLOCKS.register(name, block);
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
}
