package com.mrtrollnugnug.ropebridge.handler;

import com.mrtrollnugnug.ropebridge.RopeBridge;
import com.mrtrollnugnug.ropebridge.block.RopeBridgeBlock;
import com.mrtrollnugnug.ropebridge.block.RopeLadderBlock;
import com.mrtrollnugnug.ropebridge.item.ItemBridgeBuilder;
import com.mrtrollnugnug.ropebridge.item.ItemLadderBuilder;
import com.mrtrollnugnug.ropebridge.lib.Constants;
import com.mrtrollnugnug.ropebridge.lib.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ContentHandler {

	// Blocks
	public static final Block oak_rope_ladder = null;
	public static final Block birch_rope_ladder = null;
	public static final Block jungle_rope_ladder = null;
	public static final Block spruce_rope_ladder = null;
	public static final Block acacia_rope_ladder = null;
	public static final Block dark_oak_rope_ladder = null;

	public static final Block oak_bridge = null;
	public static final Block birch_bridge = null;
	public static final Block jungle_bridge = null;
	public static final Block spruce_bridge = null;
	public static final Block acacia_bridge = null;
	public static final Block dark_oak_bridge = null;

	// Items
	public static final Item bridge_builder = null;
	public static final Item ladder_builder = null;
	public static final Item rope = null;
	public static final Item bridge_builder_hook = null;
	public static final Item bridge_builder_barrel = null;
	public static final Item bridge_builder_handle = null;
	public static final Item ladder_hook = null;

	@SubscribeEvent
	public static void initBlocks(final RegistryEvent.Register<Block> event) {
		Block.Properties bridge = Block.Properties.from(Blocks.OAK_PLANKS).notSolid();
		Block.Properties ladder = Block.Properties.from(Blocks.LADDER);
		ModUtils.register(new RopeBridgeBlock(bridge), "oak_bridge", event.getRegistry());
		ModUtils.register(new RopeBridgeBlock(bridge), "birch_bridge", event.getRegistry());
		ModUtils.register(new RopeBridgeBlock(bridge), "jungle_bridge", event.getRegistry());
		ModUtils.register(new RopeBridgeBlock(bridge), "spruce_bridge", event.getRegistry());
		ModUtils.register(new RopeBridgeBlock(bridge), "acacia_bridge", event.getRegistry());
		ModUtils.register(new RopeBridgeBlock(bridge), "dark_oak_bridge", event.getRegistry());

		ModUtils.register(new RopeLadderBlock(ladder), "oak_rope_ladder", event.getRegistry());
		ModUtils.register(new RopeLadderBlock(ladder), "birch_rope_ladder", event.getRegistry());
		ModUtils.register(new RopeLadderBlock(ladder), "jungle_rope_ladder", event.getRegistry());
		ModUtils.register(new RopeLadderBlock(ladder), "spruce_rope_ladder", event.getRegistry());
		ModUtils.register(new RopeLadderBlock(ladder), "acacia_rope_ladder", event.getRegistry());
		ModUtils.register(new RopeLadderBlock(ladder), "dark_oak_rope_ladder", event.getRegistry());
	}

	@SubscribeEvent
	public static void initItems(final RegistryEvent.Register<Item> event) {
		Item.Properties properties = new Item.Properties().group(RopeBridge.RopeBridgeTab);
		ModUtils.register(new ItemLadderBuilder(properties), "ladder_builder", event.getRegistry());
		ModUtils.register(new Item(properties), "bridge_builder_hook", event.getRegistry());
		ModUtils.register(new Item(properties), "bridge_builder_barrel", event.getRegistry());
		ModUtils.register(new Item(properties), "bridge_builder_handle", event.getRegistry());
		ModUtils.register(new Item(properties), "ladder_hook", event.getRegistry());
		ModUtils.register(new ItemBridgeBuilder(properties), "bridge_builder", event.getRegistry());
		ModUtils.register(new Item(properties), "rope", event.getRegistry());
	}
}
