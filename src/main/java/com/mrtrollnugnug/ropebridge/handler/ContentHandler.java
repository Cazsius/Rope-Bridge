package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.block.RopeLadderBlock;
import com.mrtrollnugnug.ropebridge.item.ItemBridgeBuilder;
import com.mrtrollnugnug.ropebridge.item.ItemLadderBuilder;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public final class ContentHandler {
	public static final DeferredRegister<net.minecraft.world.level.block.Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
	public static final DeferredRegister<net.minecraft.world.item.Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Constants.MOD_ID);

	// Blocks
	public static final RegistryObject<RopeLadderBlock> oak_rope_ladder = registerBlockWithItem("oak_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.OAK_SLAB));
	public static final RegistryObject<RopeLadderBlock> birch_rope_ladder = registerBlockWithItem("birch_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.BIRCH_SLAB));
	public static final RegistryObject<RopeLadderBlock> jungle_rope_ladder = registerBlockWithItem("jungle_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.JUNGLE_SLAB));
	public static final RegistryObject<RopeLadderBlock> spruce_rope_ladder = registerBlockWithItem("spruce_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.SPRUCE_SLAB));
	public static final RegistryObject<RopeLadderBlock> acacia_rope_ladder = registerBlockWithItem("acacia_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.ACACIA_SLAB));
	public static final RegistryObject<RopeLadderBlock> cherry_rope_ladder = registerBlockWithItem("cherry_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.CHERRY_SLAB));
	public static final RegistryObject<RopeLadderBlock> dark_oak_rope_ladder = registerBlockWithItem("dark_oak_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.DARK_OAK_SLAB));
	public static final RegistryObject<RopeLadderBlock> mangrove_rope_ladder = registerBlockWithItem("mangrove_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.MANGROVE_SLAB));
	public static final RegistryObject<RopeLadderBlock> bamboo_rope_ladder = registerBlockWithItem("bamboo_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.BAMBOO_SLAB));
	public static final RegistryObject<RopeLadderBlock> crimson_rope_ladder = registerBlockWithItem("crimson_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.CRIMSON_SLAB));
	public static final RegistryObject<RopeLadderBlock> warped_rope_ladder = registerBlockWithItem("warped_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.WARPED_SLAB));

	public static final RegistryObject<RopeBridgeBlock> oak_bridge = registerBlockWithItem("oak_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.OAK_SLAB).noOcclusion(), () -> Blocks.OAK_SLAB));
	public static final RegistryObject<RopeBridgeBlock> birch_bridge = registerBlockWithItem("birch_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.BIRCH_SLAB).noOcclusion(), () -> Blocks.BIRCH_SLAB));
	public static final RegistryObject<RopeBridgeBlock> jungle_bridge = registerBlockWithItem("jungle_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.JUNGLE_SLAB).noOcclusion(), () -> Blocks.JUNGLE_SLAB));
	public static final RegistryObject<RopeBridgeBlock> spruce_bridge = registerBlockWithItem("spruce_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.SPRUCE_SLAB).noOcclusion(), () -> Blocks.SPRUCE_SLAB));
	public static final RegistryObject<RopeBridgeBlock> acacia_bridge = registerBlockWithItem("acacia_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.ACACIA_SLAB).noOcclusion(), () -> Blocks.ACACIA_SLAB));
	public static final RegistryObject<RopeBridgeBlock> cherry_bridge = registerBlockWithItem("cherry_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.CHERRY_SLAB).noOcclusion(), () -> Blocks.CHERRY_SLAB));
	public static final RegistryObject<RopeBridgeBlock> dark_oak_bridge = registerBlockWithItem("dark_oak_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.DARK_OAK_SLAB).noOcclusion(), () -> Blocks.DARK_OAK_SLAB));
	public static final RegistryObject<RopeBridgeBlock> mangrove_bridge = registerBlockWithItem("mangrove_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.MANGROVE_SLAB).noOcclusion(), () -> Blocks.MANGROVE_SLAB));
	public static final RegistryObject<RopeBridgeBlock> bamboo_bridge = registerBlockWithItem("bamboo_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.BAMBOO_SLAB).noOcclusion(), () -> Blocks.BAMBOO_SLAB));
	public static final RegistryObject<RopeBridgeBlock> crimson_bridge = registerBlockWithItem("crimson_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.CRIMSON_SLAB).noOcclusion(), () -> Blocks.CRIMSON_SLAB));
	public static final RegistryObject<RopeBridgeBlock> warped_bridge = registerBlockWithItem("warped_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.WARPED_SLAB).noOcclusion(), () -> Blocks.WARPED_SLAB));

	// Items
	public static final RegistryObject<ItemBridgeBuilder> bridge_builder = ITEMS.register("bridge_builder", () -> new ItemBridgeBuilder(new Item.Properties()));
	public static final RegistryObject<ItemLadderBuilder> ladder_builder = ITEMS.register("ladder_builder", () -> new ItemLadderBuilder(new Item.Properties()));
	public static final RegistryObject<Item> rope = ITEMS.register("rope", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> bridge_builder_hook = ITEMS.register("bridge_builder_hook", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> bridge_builder_barrel = ITEMS.register("bridge_builder_barrel", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> bridge_builder_handle = ITEMS.register("bridge_builder_handle", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ladder_hook = ITEMS.register("ladder_hook", () -> new Item(new Item.Properties()));

	public static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
		RegistryObject<T> reg = BLOCKS.register(name, block);
		ITEMS.register(name, () -> new BlockItem(reg.get(), new Item.Properties()));
		return reg;
	}

	// Sounds
	public static final RegistryObject<SoundEvent> load = SOUND_EVENTS.register("load", () ->
		SoundEvent.createVariableRangeEvent(Constants.modLoc("load")));
	public static final RegistryObject<SoundEvent> swoosh = SOUND_EVENTS.register("swoosh", () ->
		SoundEvent.createVariableRangeEvent(Constants.modLoc("swoosh")));

	public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
		.icon(() -> new ItemStack(ContentHandler.bridge_builder.get()))
		.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
		.title(Component.translatable("itemGroup.ropebridge.tab"))
		.displayItems((displayParameters, output) -> {
			List<ItemStack> stacks = ContentHandler.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
			output.acceptAll(stacks);
		}).build());
}
