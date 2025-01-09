package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.block.RopeLadderBlock;
import com.mrtrollnugnug.ropebridge.item.ItemBridgeBuilder;
import com.mrtrollnugnug.ropebridge.item.ItemLadderBuilder;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
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

public final class ContentHandler {
	public static final DeferredRegister<net.minecraft.world.level.block.Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
	public static final DeferredRegister<net.minecraft.world.item.Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

	// Blocks
	public static final RegistryObject<Block> oak_rope_ladder = BLOCKS.register("oak_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.OAK_SLAB));
	public static final RegistryObject<Block> birch_rope_ladder = BLOCKS.register("birch_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.BIRCH_SLAB));
	public static final RegistryObject<Block> jungle_rope_ladder = BLOCKS.register("jungle_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.JUNGLE_SLAB));
	public static final RegistryObject<Block> spruce_rope_ladder = BLOCKS.register("spruce_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.SPRUCE_SLAB));
	public static final RegistryObject<Block> acacia_rope_ladder = BLOCKS.register("acacia_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.ACACIA_SLAB));
	public static final RegistryObject<Block> cherry_rope_ladder = BLOCKS.register("cherry_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.CHERRY_SLAB));
	public static final RegistryObject<Block> dark_oak_rope_ladder = BLOCKS.register("dark_oak_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.DARK_OAK_SLAB));
	public static final RegistryObject<Block> mangrove_rope_ladder = BLOCKS.register("mangrove_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.MANGROVE_SLAB));
	public static final RegistryObject<Block> bamboo_rope_ladder = BLOCKS.register("bamboo_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.BAMBOO_SLAB));
	public static final RegistryObject<Block> crimson_rope_ladder = BLOCKS.register("crimson_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.CRIMSON_SLAB));
	public static final RegistryObject<Block> warped_rope_ladder = BLOCKS.register("warped_rope_ladder", () -> new RopeLadderBlock(Block.Properties.copy(Blocks.LADDER), () -> Blocks.WARPED_SLAB));

	public static final RegistryObject<Block> oak_bridge = BLOCKS.register("oak_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.OAK_SLAB).noOcclusion(), () -> Blocks.OAK_SLAB));
	public static final RegistryObject<Block> birch_bridge = BLOCKS.register("birch_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.BIRCH_SLAB).noOcclusion(), () -> Blocks.BIRCH_SLAB));
	public static final RegistryObject<Block> jungle_bridge = BLOCKS.register("jungle_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.JUNGLE_SLAB).noOcclusion(), () -> Blocks.JUNGLE_SLAB));
	public static final RegistryObject<Block> spruce_bridge = BLOCKS.register("spruce_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.SPRUCE_SLAB).noOcclusion(), () -> Blocks.SPRUCE_SLAB));
	public static final RegistryObject<Block> acacia_bridge = BLOCKS.register("acacia_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.ACACIA_SLAB).noOcclusion(), () -> Blocks.ACACIA_SLAB));
	public static final RegistryObject<Block> cherry_bridge = BLOCKS.register("cherry_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.CHERRY_SLAB).noOcclusion(), () -> Blocks.CHERRY_SLAB));
	public static final RegistryObject<Block> dark_oak_bridge = BLOCKS.register("dark_oak_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.DARK_OAK_SLAB).noOcclusion(), () -> Blocks.DARK_OAK_SLAB));
	public static final RegistryObject<Block> mangrove_bridge = BLOCKS.register("mangrove_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.MANGROVE_SLAB).noOcclusion(), () -> Blocks.MANGROVE_SLAB));
	public static final RegistryObject<Block> bamboo_bridge = BLOCKS.register("bamboo_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.BAMBOO_SLAB).noOcclusion(), () -> Blocks.BAMBOO_SLAB));
	public static final RegistryObject<Block> crimson_bridge = BLOCKS.register("crimson_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.CRIMSON_SLAB).noOcclusion(), () -> Blocks.CRIMSON_SLAB));
	public static final RegistryObject<Block> warped_bridge = BLOCKS.register("warped_bridge", () -> new RopeBridgeBlock(Block.Properties.copy(Blocks.WARPED_SLAB).noOcclusion(), () -> Blocks.WARPED_SLAB));

	// Items
	public static final RegistryObject<Item> bridge_builder = ITEMS.register("bridge_builder", () -> new ItemBridgeBuilder(new Item.Properties()));
	public static final RegistryObject<Item> ladder_builder = ITEMS.register("ladder_builder", () -> new ItemLadderBuilder(new Item.Properties()));
	public static final RegistryObject<Item> rope = ITEMS.register("rope", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> bridge_builder_hook = ITEMS.register("bridge_builder_hook", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> bridge_builder_barrel = ITEMS.register("bridge_builder_barrel", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> bridge_builder_handle = ITEMS.register("bridge_builder_handle", () -> new ItemBridgeBuilder(new Item.Properties()));
	public static final RegistryObject<Item> ladder_hook = ITEMS.register("ladder_hook", () -> new Item(new Item.Properties()));

	public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
		.icon(() -> new ItemStack(ContentHandler.bridge_builder.get()))
		.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
		.title(Component.translatable("itemGroup.ropebridge.tab"))
		.displayItems((displayParameters, output) -> {
			List<ItemStack> stacks = ContentHandler.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
			output.acceptAll(stacks);
		}).build());
}
